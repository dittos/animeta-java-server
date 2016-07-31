package animeta.util;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.ReadablePartial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import animeta.model.Period;
import animeta.model.Schedule;
import animeta.model.SourceMaterialType;
import animeta.model.Work;
import animeta.model.WorkMetadata;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.net.UrlEscapers;

public class WorkMetadatas {
    private static final ObjectMapper MAPPER = new YAMLMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkMetadatas.class);

    public static WorkMetadata extract(Work work) {
        JsonNode root;
        try {
            root = MAPPER.readTree(work.getRawMetadata());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        WorkMetadata metadata = new WorkMetadata();
        metadata.periods = readStringList(root.get("periods")).stream()
                .map(Period::parse).collect(Collectors.toList());

        JsonNode titleNode = root.get("title");
        if (titleNode != null && !titleNode.isTextual()) {
            titleNode = titleNode.get("ko");
        }
        if (titleNode == null) {
            metadata.title = work.getTitle();
        } else {
            metadata.title = titleNode.asText();
        }

        if (root.has("website")) {
            metadata.links.website = root.get("website").asText();
        }
        if (root.has("namu_ref")) {
            metadata.links.namu = namuLink(root.get("namu_ref").asText());
        }
        if (root.has("ann_id")) {
            String prefix = "http://www.animenewsnetwork.com/encyclopedia/anime.php?id=";
            metadata.links.ann = prefix + root.get("ann_id").asText();
        }

        JsonNode studioNode = root.get("studio");
        if (studioNode != null) {
            metadata.studios = readStringList(studioNode);
        }

        metadata.source = SourceMaterialType.create(root.get("source").asText());
        metadata.schedule = readSchedule(root, metadata.periods.get(0));
        return metadata;
    }

    private static String namuLink(String ref) {
        String page;
        String anchor = null;
        int anchorIndex = ref.lastIndexOf("#");
        if (anchorIndex < 0) {
            page = ref;
        } else {
            page = ref.substring(0, anchorIndex);
            anchor = ref.substring(anchorIndex + 1);
        }

        String url = "https://namu.wiki/w/" + UrlEscapers.urlPathSegmentEscaper().escape(page);
        if (anchor != null) {
            url += "#" + UrlEscapers.urlFragmentEscaper().escape(anchor);
        }
        return url;
    }

    private static List<String> readStringList(JsonNode node) {
        if (node.isTextual()) {
            return Collections.singletonList(node.asText());
        }
        List<String> result = Lists.newArrayListWithExpectedSize(node.size());
        for (JsonNode element : node) {
            result.add(element.asText());
        }
        return result;
    }

    private static Map<String, Schedule> readSchedule(JsonNode root, Period period) {
        Map<String, Schedule> schedule = Maps.newHashMap();
        schedule.put("jp", readSingleSchedule(root.get("schedule"), period));
        Schedule koreaSchedule = readSingleSchedule(root.get("schedule_kr"), period);
        if (koreaSchedule != null) {
            schedule.put("kr", koreaSchedule);
        }
        return schedule;
    }

    @Nullable
    private static Schedule readSingleSchedule(JsonNode node, Period period) {
        if (node == null) {
            return null;
        }
        ReadablePartial date;
        List<String> broadcasts;
        if (node.isTextual()) {
            date = parseDateTime(node.asText(), period);
            broadcasts = null;
        } else if (node.size() == 1) {
            date = null;
            broadcasts = readStringList(node.get(0));
        } else {
            date = parseDateTime(node.get(0).asText(), period);
            broadcasts = readStringList(node.get(1));
        }
        Schedule schedule = new Schedule();
        if (date != null) {
            if (date instanceof LocalDate) {
                schedule.dateOnly = true;
                date = ((LocalDate) date).toLocalDateTime(LocalTime.MIDNIGHT);
            }
            schedule.date = ((LocalDateTime) date).toDateTime(DateTimeZone.forID("Asia/Seoul")).getMillis();
        }
        if (broadcasts != null && broadcasts.size() > 0) {
            schedule.broadcasts = broadcasts;
        }
        return schedule;
    }

    private static ReadablePartial parseDateTime(String s, Period period) {
        String[] parts = s.split(" ");
        if (parts.length == 1) {
            return parseDate(s, period);
        }
        LocalDate date = parseDate(parts[0], period);
        String[] timeParts = parts[1].split(":");
        LocalTime time = new LocalTime(Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1]));
        return date.toLocalDateTime(time);
    }

    private static LocalDate parseDate(String s, Period period) {
        String[] parts = s.split("-");
        if (parts.length == 3) {
            return new LocalDate(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        } else {
            return new LocalDate(period.getYear(), Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }
    }
}

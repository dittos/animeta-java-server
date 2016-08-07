package animeta.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RecordStatusTypeConverter implements AttributeConverter<RecordStatusType, Short> {
    private static final RecordStatusType[] ORDER = {
            RecordStatusType.FINISHED, // 0
            RecordStatusType.WATCHING, // 1
            RecordStatusType.SUSPENDED, // 2
            RecordStatusType.INTERESTED, // 3
    };

    @Override public RecordStatusType convertToEntityAttribute(Short dbData) {
        for (short i = 0; i < ORDER.length; i++) {
            if (i == dbData) {
                return ORDER[i];
            }
        }
        throw new IllegalArgumentException(dbData.toString());
    }

    @Override public Short convertToDatabaseColumn(RecordStatusType attribute) {
        for (short i = 0; i < ORDER.length; i++) {
            if (ORDER[i] == attribute) {
                return i;
            }
        }
        throw new IllegalArgumentException(attribute.toString());
    }
}

package animeta.model;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PeriodTest {
    @Test
    public void testParse() {
        Assert.assertEquals(Period.parse("2014Q1"), new Period(2014, 1));
    }

    @Test
    public void testJSONSerialize() throws Exception {
        Assert.assertEquals(new ObjectMapper().writeValueAsString(new Period(2014, 1)), "\"2014Q1\"");
    }
}

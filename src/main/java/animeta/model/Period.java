package animeta.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;

public class Period {
    private static Pattern PATTERN = Pattern.compile("([0-9]{4})Q([1-4])");

    @Nullable
    public static Period parse(String s) {
        Matcher m = PATTERN.matcher(s);
        if (!m.find()) {
            return null;
        }
        return new Period(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
    }

    private final int year;
    private final int quarter;

    public Period(int year, int quarter) {
        Preconditions.checkArgument(1 <= quarter && quarter <= 4);
        this.year = year;
        this.quarter = quarter;
    }

    public int getYear() {
        return year;
    }

    public int getQuarter() {
        return quarter;
    }

    public int getMonth() {
        switch (quarter) {
        case 1:
            return 1;
        case 2:
            return 4;
        case 3:
            return 7;
        case 4:
            return 10;
        }
        throw new IllegalStateException();
    }

    public Period previous() {
        if (quarter == 1) {
            return new Period(year - 1, 4);
        } else {
            return new Period(year, quarter - 1);
        }
    }

    @JsonValue
    @Override
    public String toString() {
        return year + "Q" + quarter;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Period period = (Period) o;

        if (year != period.year)
            return false;
        return quarter == period.quarter;

    }

    @Override public int hashCode() {
        int result = year;
        result = 31 * result + quarter;
        return result;
    }
}

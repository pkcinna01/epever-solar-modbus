package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.units.Unit;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
D7-0 Sec, D15-8 Min
D7-0 Hour, D15-8 Day
D7-0 Month, D15-8 Year
*/
public class TimeField extends RegisterBackedField<LocalTime> {


    public static class Parser {
        public Integer hours, minutes, seconds;
        public Parser(String strVal) throws ParseException {
            Pattern p = Pattern.compile("^\\s*([0-9]+)\\s*:\\s*([0-9]+)\\s*(:\\s*([0-9]+))?\\s*$");
            Matcher m = p.matcher(strVal);
            boolean isMatch = m.matches();
            if ( !isMatch ) {
                throw new ParseException("Invalid duration format.  Expected HH:mm but found: [" + strVal + "]", -1);
            }
            hours = Integer.parseInt(m.group(1));
            minutes = Integer.parseInt(m.group(2));
            String strSeconds = m.group(4);
            seconds = strSeconds != null ? Integer.parseInt(strSeconds) : new Integer(0);
        }
    }


    public TimeField(int addr, String name, String description) {
        super(addr, Unit.Time, name, description, 1, 3);
    }


    @Override
    public LocalTime fromRegisters(int offset, int[] registers) {

        return RegisterConversions.toTime(offset, registerCount, registers);
    }

    @Override
    public int[] toRegisters(LocalTime val) {

        return RegisterConversions.fromTime(val,registerCount);
    }


    @Override
    public double doubleValue() {
        return value == null ? Double.NaN : (double) value.toSecondOfDay();
    }


    public static LocalTime parse(String strVal) throws ParseException {

        Parser tp = new Parser(strVal);
        return LocalTime.of(tp.hours,tp.minutes,tp.seconds);
    }
}

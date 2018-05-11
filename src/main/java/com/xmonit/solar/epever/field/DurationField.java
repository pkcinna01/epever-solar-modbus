package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.units.Unit;

import java.text.ParseException;
import java.time.Duration;

/*
D7-0 Sec, D15-8 Min
D7-0 Hour, D15-8 Day
D7-0 Month, D15-8 Year
*/
public class DurationField extends RegisterBackedField<Duration> {


    public DurationField(int addr, String name, String description) {
        super(addr, Unit.Duration, name, description, 1, 1);
    }


    @Override
    public Duration fromRegisters(int offset, int[] registers) {

        return RegisterConversions.toDuration(offset, registerCount, registers);
    }

    @Override
    public int[] toRegisters(Duration val) {

        return RegisterConversions.fromDuration(val,registerCount);
    }

    @Override
    public double doubleValue() {
        return value == null ? Double.NaN : (double) value.getSeconds();
    }

    public static Duration parse(String strVal) throws ParseException {
        TimeField.Parser tp = new TimeField.Parser(strVal);
        Duration duration = Duration.ofHours(tp.hours).plusMinutes(tp.minutes).plusSeconds(tp.seconds);
        return duration;
    }


}

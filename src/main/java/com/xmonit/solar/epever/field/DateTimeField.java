package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.EpeverParseException;
import com.xmonit.solar.epever.units.Unit;

import java.time.LocalDateTime;
/*
D7-0 Sec, D15-8 Min
D7-0 Hour, D15-8 Day
D7-0 Month, D15-8 Year
*/
public class DateTimeField extends RegisterBackedField<LocalDateTime> {


    public static LocalDateTime parse(String strVal) {
        return LocalDateTime.parse(strVal);
    }


    public DateTimeField(int addr, String name, String description) {

        super(addr, Unit.DateTime, name, description, 1, 3);
    }


    @Override
    public LocalDateTime fromRegisters(int offset, int[] registers) {

        return RegisterConversions.toDateTime(offset, registerCount, registers);
    }


    @Override
    public LocalDateTime parseValue(String strVal) throws EpeverParseException {

        return DateTimeField.parse(strVal);
    }


    @Override
    public int[] toRegisters(LocalDateTime val) {

        return RegisterConversions.fromDateTime(val);
    }


    @Override
    public double doubleValue() {

        return value == null ? Double.NaN : (double) java.util.Date.from(value.atZone(java.time.ZoneId.systemDefault()).toInstant()).getTime();
    }

}

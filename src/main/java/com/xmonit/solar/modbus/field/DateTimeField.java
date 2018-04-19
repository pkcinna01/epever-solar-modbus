package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.RegisterConversions;
import com.xmonit.solar.modbus.units.Unit;

import java.time.LocalDateTime;
/*
D7-0 Sec, D15-8 Min
D7-0 Hour, D15-8 Day
D7-0 Month, D15-8 Year
*/
public class DateTimeField extends RegisterBackedField<LocalDateTime> {


    public DateTimeField(int addr, String name, String description) {
        super(addr, Unit.DateTime, name, description, 1, 3);
    }


    @Override
    public LocalDateTime fromRegisters(int[] registers) {
        return RegisterConversions.toDateTime(registers);
    }

    @Override
    public int[] toRegisters(LocalDateTime val) {
        return RegisterConversions.fromDateTime(val);
    }

}

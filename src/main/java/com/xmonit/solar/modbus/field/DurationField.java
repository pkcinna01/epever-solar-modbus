package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.RegisterConversions;
import com.xmonit.solar.modbus.units.Unit;

import java.time.LocalTime;

/*
D7-0 Sec, D15-8 Min
D7-0 Hour, D15-8 Day
D7-0 Month, D15-8 Year
*/
public class DurationField extends ProxyField<LocalTime> {


    public DurationField(int addr, String name, String description) {
        super(addr, Unit.Duration, name, description, 1, 1);
    }


    @Override
    public LocalTime fromRegisters(int[] registers) {

        return RegisterConversions.toTime(registers);
    }

    @Override
    public int[] toRegisters(LocalTime val) {

        return RegisterConversions.fromTime(val,registerCount);
    }

}

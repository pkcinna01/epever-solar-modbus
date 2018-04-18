package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.RegisterConversions;
import com.xmonit.solar.modbus.units.Unit;

import java.math.BigDecimal;

public class FloatField extends ProxyField<java.lang.Float> {


    public FloatField(int addr, Unit unit, String name, String description, int multiplier, int registerCount) {
        super(addr, unit, name, description, multiplier, registerCount);
    }

    @Override
    public Float fromRegisters(int[] registers) {
        return RegisterConversions.toBigDecimal(registers, denominator).floatValue();
    }

    @Override
    public int[] toRegisters(Float val) {
        return RegisterConversions.fromBigDecimal( new BigDecimal(val), denominator);
    }

}
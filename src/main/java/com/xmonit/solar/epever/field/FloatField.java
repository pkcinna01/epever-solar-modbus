package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.units.Unit;

import java.math.BigDecimal;

public class FloatField extends RegisterBackedField<Float> {


    public FloatField(int addr, Unit unit, String name, String description, int multiplier, int registerCount) {
        super(addr, unit, name, description, multiplier, registerCount);
    }

    @Override
    public Float fromRegisters(int offset, int[] registers) {
        return RegisterConversions.toBigDecimal(offset, registerCount, registers, denominator).floatValue();
    }

    @Override
    public int[] toRegisters(Float val) {
        return RegisterConversions.fromBigDecimal( new BigDecimal(val), denominator);
    }

    @Override
    public double doubleValue() {
        return value == null ? Double.NaN : value.doubleValue();
    }

}

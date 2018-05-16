package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.EpeverParseException;
import com.xmonit.solar.epever.units.Unit;

import java.math.BigDecimal;

public class FloatField extends RegisterBackedField<Float> {

    protected boolean bSigned = true;

    public FloatField(int addr, Unit unit, String name, String description, int multiplier, int registerCount) {
        super(addr, unit, name, description, multiplier, registerCount);
    }

    @Override
    public Float fromRegisters(int offset, int[] registers) {
        return RegisterConversions.toBigDecimal(offset, registerCount, registers, denominator,bSigned).floatValue();
    }

    @Override
    public int[] toRegisters(Float val) {
        return RegisterConversions.fromBigDecimal( new BigDecimal(val), denominator);
    }

    @Override
    public Float parseValue(String strVal) throws EpeverParseException {
        try {
            return Float.parseFloat(strVal);
        } catch ( NumberFormatException ex ) {
            throw new EpeverParseException("Could not convert text to float.", ex);
        }
    }

    @Override
    public double doubleValue() {
        return value == null ? Double.NaN : value.doubleValue();
    }

}

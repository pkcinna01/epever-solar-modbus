package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.EpeverParseException;
import com.xmonit.solar.epever.units.Unit;

import java.math.BigDecimal;
import java.text.ParseException;

public class DecimalField extends RegisterBackedField<BigDecimal> {


    public DecimalField(int addr, Unit unit, String name, String description, int multiplier, int registerCount) {
        super(addr, unit, name, description, multiplier, registerCount);
    }

    @Override
    public BigDecimal fromRegisters(int offset, int[] registers) {
        return RegisterConversions.toBigDecimal(offset, registerCount, registers, denominator);
    }

    @Override
    public int[] toRegisters(BigDecimal val) {
       return RegisterConversions.fromBigDecimal(val, denominator);
    }

    @Override
    public BigDecimal parseValue(String strVal) throws EpeverParseException {
        try {
            return new BigDecimal(strVal.replaceAll(",",""));
        } catch ( NumberFormatException ex ) {
            throw new EpeverParseException("Could not convert text to BigDecimal.", ex);
        }
    }

    @Override
    public double doubleValue() { return value == null ? Double.NaN : value.doubleValue(); }
}

package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.EpeverParseException;
import com.xmonit.solar.epever.units.Unit;

import java.math.BigInteger;

public class IntegerField extends RegisterBackedField<BigInteger> {


    public IntegerField(int addr, Unit unit, String name, String description, int multiplier, int registerCount) {
        super(addr, unit, name, description, multiplier, registerCount);
    }

    @Override
    public BigInteger fromRegisters(int offset, int[] registers) {
        return RegisterConversions.toBigInteger(offset, registerCount, registers, denominator);
    }

    @Override
    public int[] toRegisters(BigInteger val) {
        return RegisterConversions.fromBigInteger(val, denominator);
    }

    @Override
    public BigInteger parseValue(String strVal) throws EpeverParseException {
        try {
            return new BigInteger(strVal.replaceAll(",",""));
        } catch ( NumberFormatException ex ) {
            throw new EpeverParseException("Could not convert text to float.", ex);
        }
    }

    @Override
    public double doubleValue() {
        return value == null ? Double.NaN : value.doubleValue();
    }
}

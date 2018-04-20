package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.RegisterConversions;
import com.xmonit.solar.modbus.units.Unit;

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
}

package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.RegisterConversions;
import com.xmonit.solar.modbus.units.Unit;

import java.math.BigInteger;

public class BooleanField extends ProxyField<Boolean> {


    public BooleanField(int addr, String name, String description) {
        super(addr, Unit.Int, name, description, 1, 1);
    }

    @Override
    public Boolean fromRegisters(int[] registers) {
        return false; //RegisterConversions.toBigInteger(registers, denominator);
    }

    @Override
    public int[] toRegisters(Boolean val) {

        //return RegisterConversions.fromBigInteger(val, denominator);
        return null;
    }
}

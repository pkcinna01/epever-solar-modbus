package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.RegisterConversions;
import com.xmonit.solar.modbus.units.HexCodes;

import java.math.BigInteger;


public class CodesField extends RegisterBackedField<BigInteger> {

    public CodesField(int addr, int registerCount, HexCodes codes){

        super(addr,codes,codes.name,null,1,registerCount);
    }

    @Override
    public BigInteger fromRegisters(int[] registers) {

        return RegisterConversions.toBigInteger(registers, denominator);
    }

    @Override
    public int[] toRegisters(BigInteger val) {

        return RegisterConversions.fromBigInteger(val,denominator);
    }

}

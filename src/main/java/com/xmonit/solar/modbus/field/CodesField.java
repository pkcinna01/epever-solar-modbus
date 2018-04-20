package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.RegisterConversions;
import com.xmonit.solar.modbus.units.HexCodes;

import java.math.BigInteger;


public class CodesField extends RegisterBackedField<BigInteger> {

    public CodesField(int addr, int registerCount, HexCodes codes){

        super(addr,codes,codes.name,null,1,registerCount);
    }

    @Override
    public BigInteger fromRegisters(int offset, int[] registers) {

        return RegisterConversions.toBigInteger(offset, registerCount, registers, denominator);
    }

    @Override
    public int[] toRegisters(BigInteger val) {

        return RegisterConversions.fromBigInteger(val,denominator);
    }

    public void writeValue(String codeName) throws Exception {
        HexCodes codes = (HexCodes) unit;
        int code = codes.findByName(codeName);
        writeValue(BigInteger.valueOf(code));
    }

}

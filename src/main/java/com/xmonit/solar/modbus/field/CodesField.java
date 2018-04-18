package com.xmonit.solar.modbus.field;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.xmonit.solar.modbus.RegisterConversions;
import com.xmonit.solar.modbus.units.HexCodes;

import java.math.BigInteger;

import static com.intelligt.modbus.jlibmodbus.Modbus.MIN_SERVER_ADDRESS;


public class CodesField extends ProxyField<BigInteger> {

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

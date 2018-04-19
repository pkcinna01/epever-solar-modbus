package com.xmonit.solar.modbus;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;

import java.util.List;

public class MockChargeController extends ChargeController {

    @Override
    public void init(String deviceName) throws SerialPortException {

    }

    @Override
    public void connect() throws ModbusIOException {

    }

    @Override
    public void disconnect() throws ModbusIOException {

    }

    @Override
    public List<String> getDeviceInfo() throws ChargeControllerException {
        return null;
    }

    @Override
    public int[] readInputRegisters(int addr, int registerCount) throws ChargeControllerException {
        return new int[0];
    }

    @Override
    public int[] readHoldingRegisters(int addr, int registerCount) throws ChargeControllerException {
        return new int[0];
    }

    @Override
    public boolean[] readDiscreteInputs(int addr, int inputCount) throws ChargeControllerException {
        return new boolean[0];
    }

    @Override
    public boolean[] readCoils(int addr, int coilCount) throws ChargeControllerException {
        return new boolean[0];
    }

    @Override
    public void writeRegisters(int addr, int[] registers) throws ChargeControllerException {

    }

    @Override
    public void writeCoils(int addr, boolean[] coils) throws ChargeControllerException {

    }
}

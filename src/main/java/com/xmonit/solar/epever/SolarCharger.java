package com.xmonit.solar.epever;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.serial.*;

import java.util.List;

import static com.intelligt.modbus.jlibmodbus.Modbus.MIN_SERVER_ADDRESS;


abstract public class SolarCharger {

    protected static String hex(int i) {
        return String.format("0x%04X",i);
    }

    int serverAddressId = MIN_SERVER_ADDRESS;

    String serialName;

    abstract public void connect() throws ModbusIOException;

    abstract public void disconnect() throws ModbusIOException;

    abstract public List<String> getDeviceInfo() throws EpeverException;

    abstract public int[] readInputRegisters(int addr, int registerCount) throws EpeverException;

    abstract public int[] readHoldingRegisters(int addr, int registerCount) throws EpeverException;

    abstract public boolean[] readDiscreteInputs(int addr, int inputCount) throws EpeverException;

    abstract public boolean[] readCoils(int addr, int coilCount) throws EpeverException;

    abstract public void writeRegisters(int addr, int[] registers) throws EpeverException;

    abstract public void writeCoils(int addr, boolean[] coils) throws EpeverException;

    public void init(String deviceName, int serverAddressId) throws SerialPortException {
        this.serialName = deviceName;
        this.serverAddressId = serverAddressId;
    };

    public void init(String deviceName) throws SerialPortException {
        this.init(deviceName,MIN_SERVER_ADDRESS);
    };

    public String getSerialName() {
        return serialName;
    }
}

package com.xmonit.solar.epever;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.serial.*;

import java.util.List;

import static com.intelligt.modbus.jlibmodbus.Modbus.MIN_SERVER_ADDRESS;


abstract public class SolarCharger {

    public static String hex(int i) {
        return String.format("0x%04X",i);
    }

    static Integer idCounter = MIN_SERVER_ADDRESS; // increment each time a charge controller is created
    int id;

    String serialName;

    public SolarCharger() {
        synchronized(idCounter) {
            id = idCounter++;
        }
    }

    abstract public void connect() throws ModbusIOException;

    abstract public void disconnect() throws ModbusIOException;

    abstract public List<String> getDeviceInfo() throws EpeverException;

    abstract public int[] readInputRegisters(int addr, int registerCount) throws EpeverException;

    abstract public int[] readHoldingRegisters(int addr, int registerCount) throws EpeverException;

    abstract public boolean[] readDiscreteInputs(int addr, int inputCount) throws EpeverException;

    abstract public boolean[] readCoils(int addr, int coilCount) throws EpeverException;

    abstract public void writeRegisters(int addr, int[] registers) throws EpeverException;

    abstract public void writeCoils(int addr, boolean[] coils) throws EpeverException;

    public void init(String deviceName) throws SerialPortException {
        this.serialName = deviceName;
    };

    public String getSerialName() {
        return serialName;
    }
}

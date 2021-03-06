package com.xmonit.solar.epever;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;

public class MockSolarCharger extends SolarCharger {


    boolean connected = false;
    protected long connectTimeMs = 0;

    @Override
    public void connect() throws ModbusIOException {
        connected = true;
        connectTimeMs = System.currentTimeMillis();
    }

    @Override
    public void disconnect() throws ModbusIOException {
        connected = false;
        connectTimeMs = 0;
    }

    @Override
    public boolean isConnected() throws ModbusIOException {
        return connected;
    }

    @Override
    public DeviceInfo readDeviceInfo() throws EpeverException {
        deviceInfo = new DeviceInfo();
        deviceInfo.company = "ACME";
        deviceInfo.model = "Mock Solar Charger";
        deviceInfo.version = "1.0";
        return deviceInfo;
    }

    @Override
    public int[] readInputRegisters(int addr, int registerCount) throws EpeverException {
        return new int[0];
    }

    @Override
    public int[] readHoldingRegisters(int addr, int registerCount) throws EpeverException {
        return new int[0];
    }

    @Override
    public boolean[] readDiscreteInputs(int addr, int inputCount) throws EpeverException {
        return new boolean[0];
    }

    @Override
    public boolean[] readCoils(int addr, int coilCount) throws EpeverException {
        return new boolean[0];
    }

    @Override
    public void writeRegisters(int addr, int[] registers) throws EpeverException {

    }

    @Override
    public void writeCoils(int addr, boolean[] coils) throws EpeverException {

    }

    @Override
    public long getConnectTimeMs() {
        return 0;
    }
}

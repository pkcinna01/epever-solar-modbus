package com.xmonit.solar.epever;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.serial.*;
import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.intelligt.modbus.jlibmodbus.Modbus.MIN_SERVER_ADDRESS;


abstract public class SolarCharger {

    @Data
    @ToString
    public class DeviceInfo {
        public String company;
        public String model;
        public String version;
        public String commPort;
        public String getId() {
            return getModel() + "|" + getCommPort();
        }
    }

    public static String hex(int i) {
        return String.format("0x%08X",i);
    }

    public static String hex(int[] ia) { return Arrays.stream(ia).mapToObj(i ->String.format("%08X",i)).collect(Collectors.joining(" "));}

    int serverAddressId = MIN_SERVER_ADDRESS;

    String serialName;

    DeviceInfo deviceInfo;

    abstract public void connect() throws ModbusIOException;

    abstract public void disconnect() throws ModbusIOException;

    abstract public boolean isConnected() throws ModbusIOException;

    abstract public DeviceInfo readDeviceInfo() throws EpeverException;

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

    public synchronized DeviceInfo getDeviceInfo() {
        if ( deviceInfo == null ) try {
            deviceInfo = readDeviceInfo();
        } catch (EpeverException ex) {
            throw new RuntimeException(ex);
        }
        return deviceInfo;
    }

    public static interface ConnectionOp {
        public void run() throws Exception;
    }

    public synchronized SolarCharger withConnection( ConnectionOp op ) throws Exception {
        boolean priorConnection = this.isConnected();
        try {
            if ( !priorConnection ) {
                this.connect();
            }
            try {
                op.run();
            } catch (Exception ex) {
                throw ex;
            }
        } finally {
            if ( !priorConnection )
            this.disconnect();
        }
        return this;
    }

    public String getSerialName() {
        return serialName;
    }

    public String getId() {
        DeviceInfo di = getDeviceInfo();

        return di.getId();
    }
}

package com.xmonit.solar.epever;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.data.mei.ReadDeviceIdentificationInterface;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.MEIReadDeviceIdentification;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.ReadDeviceIdentificationCode;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;
import com.intelligt.modbus.jlibmodbus.serial.SerialUtils;

import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class EpeverSolarCharger extends SolarCharger {


    static {
        //SerialUtils.setSerialPortFactory( new SerialPortFactoryJSerialComm());
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryPJC());
    }

    SerialParameters serialParameters;

    ModbusMaster modbusMaster;


    public static List<String> findSerialPortNames(String strRegEx) throws SerialPortException {

        return findSerialPortNames(Pattern.compile(strRegEx));
    }


    public static List<String> findSerialPortNames(Pattern pattern) throws SerialPortException {
        return SerialUtils.getPortIdentifiers().stream().filter(n->pattern.matcher(n).matches()).collect(Collectors.toList());
    }


    @Override
    public void init(String serialName) throws SerialPortException {

        super.init(serialName);
        serialParameters = new SerialParameters();
        serialParameters.setDevice(serialName); //  ex: /dev/ttyXRUSB1
        serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_115200);
        serialParameters.setDataBits(8);
        serialParameters.setParity(SerialPort.Parity.NONE);
        serialParameters.setStopBits(1);
        modbusMaster = ModbusMasterFactory.createModbusMasterRTU(serialParameters);
        modbusMaster.setResponseTimeout(Modbus.MAX_RESPONSE_TIMEOUT);
    }


    protected long connectTimeMs = 0;

    @Override
    public synchronized void connect() throws ModbusIOException {
        modbusMaster.connect();
        connectTimeMs = System.currentTimeMillis();
        //modbusMaster.setResponseTimeout(15000); //TBD - this is redundant but sometimes reads don't timeout
    }

    @Override
    public synchronized void disconnect() throws ModbusIOException {
        connectTimeMs = 0;
        disconnectNow();

    }

    public void disconnectNow() throws ModbusIOException {
        modbusMaster.disconnect();

    }

    @Override
    public boolean isConnected() throws ModbusIOException {
        return modbusMaster.isConnected();
    }

    @Override
    public long getConnectTimeMs() {
        return connectTimeMs;
    }



    @Override
    public synchronized DeviceInfo readDeviceInfo() throws EpeverException {
        try {
            DeviceInfo deviceInfo = new DeviceInfo();
            MEIReadDeviceIdentification rdi = modbusMaster.readDeviceIdentification(1, 0, ReadDeviceIdentificationCode.BASIC_STREAM_ACCESS);
            ReadDeviceIdentificationInterface.DataObject[] objects = rdi.getObjects();
            deviceInfo.company = new String(objects[0].getValue(), Charset.defaultCharset());
            deviceInfo.model = new String(objects[1].getValue(), Charset.defaultCharset());
            deviceInfo.version = new String(objects[2].getValue(), Charset.defaultCharset());
            deviceInfo.commPort = this.getSerialName();
            return deviceInfo;
        } catch (Exception ex) {
            throw new EpeverException("Failed reading MEI Device Information", ex);
        }
    }


    @Override
    public synchronized int[] readInputRegisters(int addr, int registerCount) throws EpeverException {
        try {
            return modbusMaster.readInputRegisters(serverAddressId, addr, registerCount);
        } catch (Exception ex) {
            throw new EpeverException("Failed reading input register(s).  Address=" + hex(addr) + " Count=" + registerCount, ex);
        }
    }


    @Override
    public synchronized int[] readHoldingRegisters(int addr, int registerCount) throws EpeverException {
        try {
            return modbusMaster.readHoldingRegisters(serverAddressId, addr, registerCount);
        } catch (Exception ex) {
            throw new EpeverException("Failed reading holding register(s).  Address=" + hex(addr) + " Count=" + registerCount, ex);
        }
    }


    @Override
    public synchronized boolean[] readDiscreteInputs(int addr, int inputCount) throws EpeverException {
        try {
            return modbusMaster.readDiscreteInputs(serverAddressId, addr, inputCount);
        } catch (Exception ex) {
            throw new EpeverException("Failed reading discrete input(s).  Address=" + hex(addr) + " Count=" + inputCount, ex);
        }
    }


    @Override
    public synchronized boolean[] readCoils(int addr, int coilCount) throws EpeverException {
        try {
            return modbusMaster.readCoils(serverAddressId, addr, coilCount);
        } catch (Exception ex) {
            throw new EpeverException("Failed reading coil(s).  Address=" + hex(addr) + " Count=" + coilCount, ex);
        }
    }


    @Override
    public synchronized void writeRegisters(int addr, int[] registers) throws EpeverException {
        try {
            modbusMaster.writeMultipleRegisters(serverAddressId, addr, registers);
        } catch (Exception ex) {
            throw new EpeverException("Failed writing holding registers.  Address=" + hex(addr) + " Count=" + registers.length, ex);
        }
    }


    @Override
    public synchronized void writeCoils(int addr, boolean[] coils) throws EpeverException {
        try {
            modbusMaster.writeMultipleCoils(serverAddressId, addr, coils);
        } catch (Exception ex) {
            throw new EpeverException("Failed writing coils.  Address=" + hex(addr), ex);
        }
    }

}

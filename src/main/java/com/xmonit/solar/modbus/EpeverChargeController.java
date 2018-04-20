package com.xmonit.solar.modbus;

import com.intelligt.modbus.jlibmodbus.data.mei.ReadDeviceIdentificationInterface;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.MEIReadDeviceIdentification;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.ReadDeviceIdentificationCode;
import com.intelligt.modbus.jlibmodbus.serial.*;
import com.xmonit.solar.modbus.field.ModbusField;
import purejavacomm.CommPortIdentifier;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


public class EpeverChargeController extends ChargeController {

    static {
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryRXTX());
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryJavaComm());
        SerialUtils.setSerialPortFactory(new SerialPortFactoryPJC());
    }

    SerialParameters serialParameters;
    ModbusMaster modbusMaster;

    public static List<String> findDeviceNames(Pattern pattern) {
        List<String> deviceNames = new LinkedList<>();
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            CommPortIdentifier id = (CommPortIdentifier) portList.nextElement();
            if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                String portName = id.getName();
                if (pattern.matcher(portName).matches()) {
                    deviceNames.add(portName);
                }
            }
        }
        return deviceNames;
    }

    @Override
    public void init(String deviceName) throws SerialPortException {

        serialParameters = new SerialParameters();
        serialParameters.setDevice(deviceName); //  ex: /dev/ttyXRUSB1
        serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_115200);
        serialParameters.setDataBits(8);
        serialParameters.setParity(SerialPort.Parity.NONE);
        serialParameters.setStopBits(1);
        modbusMaster = ModbusMasterFactory.createModbusMasterRTU(serialParameters);
        modbusMaster.setResponseTimeout(10000);

    }

    @Override
    public void connect() throws ModbusIOException {
        modbusMaster.connect();

    }

    @Override
    public void disconnect() throws ModbusIOException {
        modbusMaster.disconnect();

    }

    @Override
    public List<String> getDeviceInfo() throws ChargeControllerException {
        try {
            List<String> deviceInfo = new ArrayList<>();
            MEIReadDeviceIdentification rdi = modbusMaster.readDeviceIdentification(1, 0, ReadDeviceIdentificationCode.BASIC_STREAM_ACCESS);
            ReadDeviceIdentificationInterface.DataObject[] objects = rdi.getObjects();
            for (ReadDeviceIdentificationInterface.DataObject o : objects) {
                deviceInfo.add(new String(o.getValue(), Charset.defaultCharset()));
            }
            return deviceInfo;
        } catch (Exception ex) {
            throw new ChargeControllerException("Failed reading MEI Device Information", ex);
        }
    }

    @Override
    public int[] readInputRegisters(int addr, int registerCount) throws ChargeControllerException {
        try {
            return modbusMaster.readInputRegisters(id, addr, registerCount);
        } catch (Exception ex) {
            throw new ChargeControllerException("Failed reading input register(s).  Address="+ hex(addr) + " Count="+registerCount,ex);
        }
    }

    @Override
    public int[] readHoldingRegisters(int addr, int registerCount) throws ChargeControllerException {
        try {
            return modbusMaster.readHoldingRegisters(id, addr, registerCount);
        } catch (Exception ex) {
            throw new ChargeControllerException("Failed reading holding register(s).  Address="+hex(addr)+ " Count="+registerCount,ex);
        }
    }

    @Override
    public boolean[] readDiscreteInputs(int addr, int inputCount) throws ChargeControllerException {
        try {
            return modbusMaster.readDiscreteInputs(id, addr, inputCount);
        } catch (Exception ex) {
            throw new ChargeControllerException("Failed reading discrete input(s).  Address="+hex(addr)+ " Count="+inputCount,ex);
        }
    }

    @Override
    public boolean[] readCoils(int addr, int coilCount) throws ChargeControllerException {
        try {
            return modbusMaster.readCoils(id, addr, coilCount);
        } catch (Exception ex) {
            throw new ChargeControllerException("Failed reading coil(s).  Address="+hex(addr)+ " Count="+coilCount,ex);
        }
    }

    @Override
    public void writeRegisters(int addr, int[] registers) throws ChargeControllerException {
        try {
            modbusMaster.writeMultipleRegisters(id, addr, registers);
        } catch (Exception ex) {
            throw new ChargeControllerException("Failed writing holding registers.  Address="+hex(addr)+ " Count="+registers.length,ex);
        }
    }

    @Override
    public void writeCoils(int addr, boolean[] coils) throws ChargeControllerException {
        try {
            modbusMaster.writeMultipleCoils(id, addr, coils);
        } catch (Exception ex) {
            throw new ChargeControllerException("Failed writing coils.  Address="+hex(addr),ex);
        }
    }

}

package com.xmonit.solar.epever;

import com.intelligt.modbus.jlibmodbus.data.mei.ReadDeviceIdentificationInterface;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.MEIReadDeviceIdentification;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.ReadDeviceIdentificationCode;
import com.intelligt.modbus.jlibmodbus.serial.*;

//import purejavacomm.CommPortIdentifier;
//import jssc.SerialPortList;
//import com.fazecast.jSerialComm.SerialPort;

import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class EpeverSolarCharger extends SolarCharger {

    static Function<Pattern,List<String>> findSerialNamesImpl;

    static {
        SerialUtils.setSerialPortFactory( new SerialPortFactoryJSerialComm());
        findSerialNamesImpl = (pattern) -> Arrays.stream(com.fazecast.jSerialComm.SerialPort.getCommPorts())
                .map(port->port.getSystemPortName()).filter(n->pattern.matcher(n).matches()).collect(Collectors.toList());

        //SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
        //findSerialNamesImpl = (pattern) -> Stream.of(jssc.SerialPortList.getPortNames())
        //      .filter(name->pattern.matcher(name).matches()).collect(Collectors.toList());

        //SerialUtils.setSerialPortFactory(new SerialPortFactoryPJC());
        //findSerialNamesImpl = (pattern) -> Collections.list(purejavacomm.CommPortIdentifier.getPortIdentifiers()).stream()
        //      .filter(id->id.getPortType() == purejavacomm.CommPortIdentifier.PORT_SERIAL).map(id->id.getName())
        //      .filter(n->pattern.matcher(n).matches()).collect(Collectors.toList());

        //SerialUtils.setSerialPortFactory(new SerialPortFactoryRXTX());
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryJavaComm());
    }

    SerialParameters serialParameters;

    ModbusMaster modbusMaster;

    public static List<String> findSerialNames(String strRegEx) {
        return findSerialNames(Pattern.compile(strRegEx));
    }

    public static List<String> findSerialNames(Pattern pattern) {
        return findSerialNamesImpl.apply(pattern);
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
    public List<String> getDeviceInfo() throws EpeverException {
        try {
            List<String> deviceInfo = new ArrayList<>();
            MEIReadDeviceIdentification rdi = modbusMaster.readDeviceIdentification(1, 0, ReadDeviceIdentificationCode.BASIC_STREAM_ACCESS);
            ReadDeviceIdentificationInterface.DataObject[] objects = rdi.getObjects();
            for (ReadDeviceIdentificationInterface.DataObject o : objects) {
                deviceInfo.add(new String(o.getValue(), Charset.defaultCharset()));
            }
            return deviceInfo;
        } catch (Exception ex) {
            throw new EpeverException("Failed reading MEI Device Information", ex);
        }
    }

    @Override
    public int[] readInputRegisters(int addr, int registerCount) throws EpeverException {
        try {
            return modbusMaster.readInputRegisters(serverAddressId, addr, registerCount);
        } catch (Exception ex) {
            throw new EpeverException("Failed reading input register(s).  Address="+ hex(addr) + " Count="+registerCount,ex);
        }
    }

    @Override
    public int[] readHoldingRegisters(int addr, int registerCount) throws EpeverException {
        try {
            return modbusMaster.readHoldingRegisters(serverAddressId, addr, registerCount);
        } catch (Exception ex) {
            throw new EpeverException("Failed reading holding register(s).  Address="+hex(addr)+ " Count="+registerCount,ex);
        }
    }

    @Override
    public boolean[] readDiscreteInputs(int addr, int inputCount) throws EpeverException {
        try {
            return modbusMaster.readDiscreteInputs(serverAddressId, addr, inputCount);
        } catch (Exception ex) {
            throw new EpeverException("Failed reading discrete input(s).  Address="+hex(addr)+ " Count="+inputCount,ex);
        }
    }

    @Override
    public boolean[] readCoils(int addr, int coilCount) throws EpeverException {
        try {
            return modbusMaster.readCoils(serverAddressId, addr, coilCount);
        } catch (Exception ex) {
            throw new EpeverException("Failed reading coil(s).  Address="+hex(addr)+ " Count="+coilCount,ex);
        }
    }

    @Override
    public void writeRegisters(int addr, int[] registers) throws EpeverException {
        try {
            modbusMaster.writeMultipleRegisters(serverAddressId, addr, registers);
        } catch (Exception ex) {
            throw new EpeverException("Failed writing holding registers.  Address="+hex(addr)+ " Count="+registers.length,ex);
        }
    }

    @Override
    public void writeCoils(int addr, boolean[] coils) throws EpeverException {
        try {
            modbusMaster.writeMultipleCoils(serverAddressId, addr, coils);
        } catch (Exception ex) {
            throw new EpeverException("Failed writing coils.  Address="+hex(addr),ex);
        }
    }

}

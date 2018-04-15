package com.xmonit.solar.modbus;

import com.intelligt.modbus.jlibmodbus.data.mei.ReadDeviceIdentificationInterface;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.MEIReadDeviceIdentification;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.ReadDeviceIdentificationCode;
import com.intelligt.modbus.jlibmodbus.serial.*;
import com.intelligt.modbus.jlibmodbus.slave.ModbusSlave;
import com.intelligt.modbus.jlibmodbus.slave.ModbusSlaveFactory;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import purejavacomm.CommPortIdentifier;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.*;


public class ChargeController {

    static {
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryRXTX());
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryJavaComm());
        SerialUtils.setSerialPortFactory(new SerialPortFactoryPJC());
    }

    SerialParameters serialParameters;
    ModbusSlave modbusSlave;
    ModbusMaster modbusMaster;

    public static List<String> findDeviceNames(RegularExpression re) {
        List<String> deviceNames = new LinkedList<>();
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            CommPortIdentifier id = (CommPortIdentifier) portList.nextElement();
            if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                String portName = id.getName();
                if ( re.matches(portName) ) {
                    deviceNames.add(portName);
                }
            }
        }
        return deviceNames;
    }

    public void init(String deviceName) throws SerialPortException {


        serialParameters = new SerialParameters();
        serialParameters.setDevice(deviceName); //  ex: /dev/ttyXRUSB1
        serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_115200);
        serialParameters.setDataBits(8);
        serialParameters.setParity(SerialPort.Parity.NONE);
        serialParameters.setStopBits(1);
        //modbusSlave = ModbusSlaveFactory.createModbusSlaveRTU(serialParameters);
        modbusMaster = ModbusMasterFactory.createModbusMasterRTU(serialParameters);

    }

    public void connect() throws ModbusIOException {
        modbusMaster.connect();

    }

    public List<String> getDeviceInfo() throws Exception {
        List<String> deviceInfo = new ArrayList<>();
        MEIReadDeviceIdentification rdi = modbusMaster.readDeviceIdentification(1, 0, ReadDeviceIdentificationCode.BASIC_STREAM_ACCESS);
        ReadDeviceIdentificationInterface.DataObject[] objects = rdi.getObjects();
        for (ReadDeviceIdentificationInterface.DataObject o : objects) {
            deviceInfo.add( new String(o.getValue(),Charset.defaultCharset()));
            //System.out.format("\t%s\n", new String(o.getValue(), Charset.defaultCharset()));
        }
        return deviceInfo;
    }

    public void dumpDeviceInputRegisters(PrintWriter pw) {
        pw.println("Device Input Registers (" + serialParameters.getDevice() + ")");

    }


}

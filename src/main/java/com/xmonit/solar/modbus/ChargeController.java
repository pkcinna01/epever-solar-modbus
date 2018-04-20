package com.xmonit.solar.modbus;

import com.intelligt.modbus.jlibmodbus.data.mei.ReadDeviceIdentificationInterface;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.MEIReadDeviceIdentification;
import com.intelligt.modbus.jlibmodbus.msg.base.mei.ReadDeviceIdentificationCode;
import com.intelligt.modbus.jlibmodbus.serial.*;
import purejavacomm.CommPortIdentifier;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static com.intelligt.modbus.jlibmodbus.Modbus.MIN_SERVER_ADDRESS;


abstract public class ChargeController {

    public static String hex(int i) {
        return String.format("0x%04X",i);
    }

    static Integer idCounter = MIN_SERVER_ADDRESS; // increment each time a charge controller is created
    int id;

    public ChargeController() {
        synchronized(idCounter) {
            id = idCounter++;
        }
    }

    abstract public void init(String deviceName) throws SerialPortException;

    abstract public void connect() throws ModbusIOException;

    abstract public void disconnect() throws ModbusIOException;

    abstract public List<String> getDeviceInfo() throws ChargeControllerException;

    abstract public int[] readInputRegisters(int addr, int registerCount) throws ChargeControllerException;

    abstract public int[] readHoldingRegisters(int addr, int registerCount) throws ChargeControllerException;

    abstract public boolean[] readDiscreteInputs(int addr, int inputCount) throws ChargeControllerException;

    abstract public boolean[] readCoils(int addr, int coilCount) throws ChargeControllerException;

    abstract public void writeRegisters(int addr, int[] registers) throws ChargeControllerException;

    abstract public void writeCoils(int addr, boolean[] coils) throws ChargeControllerException;

}

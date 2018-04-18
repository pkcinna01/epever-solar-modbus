package com.xmonit.solar.modbus;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;
import com.xmonit.solar.modbus.field.CodesField;
import com.xmonit.solar.modbus.field.ProxyField;
import com.xmonit.solar.modbus.field.ProxyFieldList;
import com.xmonit.solar.modbus.units.HexCodes;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class HexCodesFieldTest extends ChargeControllerDependentTest {

    @Test
    public void printCodeValue() {
        for ( int i = 0; i < 4; i++ ) {
            System.out.println(HexCodes.chargingMode.asString(i));
        }
        for ( int i = 0; i < 4; i++ ) {
            System.out.println(HexCodes.batteryStatus.asString(i));
        }

    }

    @Test
    public void readValue() throws ChargeControllerException {
        List<ProxyField> proxyFieldList = new ProxyFieldList(chargeController).stream().filter((f)-> f.unit instanceof HexCodes).collect(Collectors.toList());

        for (ProxyField proxyField : proxyFieldList) {
            BigInteger val = (BigInteger) proxyField.readValue();
            System.out.println(proxyField.name + " " + val.toString(16));
            System.out.println("\t" + proxyField);
        }
    }

    @Test
    public void printDiscription() {
        System.out.println( HexCodes.batteryStatus.getDescription());
        System.out.println( HexCodes.chargingMode.getDescription());
    }
}
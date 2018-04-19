package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.ChargeControllerDependentTest;
import com.xmonit.solar.modbus.ChargeControllerException;
import com.xmonit.solar.modbus.field.ModbusField;
import com.xmonit.solar.modbus.field.RegisterBackedField;
import com.xmonit.solar.modbus.field.ModbusFieldList;
import com.xmonit.solar.modbus.units.HexCodes;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class CodesFieldTest extends ChargeControllerDependentTest {

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
        List<ModbusField> registerBackedFieldList = new ModbusFieldList(chargeController).stream().filter((f)-> f.unit instanceof HexCodes).collect(Collectors.toList());

        for (ModbusField registerBackedField : registerBackedFieldList) {
            BigInteger val = (BigInteger) registerBackedField.readValue();
            System.out.println(registerBackedField.name + " " + val.toString(16));
            System.out.println("\t" + registerBackedField);
        }
    }

    @Test
    public void printDiscription() {
        System.out.println( HexCodes.batteryStatus.getDescription());
        System.out.println( HexCodes.chargingMode.getDescription());
    }
}
package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.ChargeControllerDependentTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanFieldTest extends ChargeControllerDependentTest {

    @Test
    public void readHoldingRegistersBackedFields() throws Exception {
        ModbusFieldList modbusFieldList = ModbusFieldList.createBooleanBackedFields(chargeController);
        for (ModbusField field : modbusFieldList) {
           BooleanField booleanField = (BooleanField)field;
            System.out.println(booleanField.name);
            Boolean val = booleanField.readValue();
            //Object convertedVal = registerBackedField.fromRegisters(registerBackedField.toRegisters(val));
            //assertFieldValuesEqual(val,convertedVal);
            System.out.println("\t" + booleanField);
        }
    }
}
package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.ChargeControllerDependentTest;
import com.xmonit.solar.modbus.field.ModbusField;
import com.xmonit.solar.modbus.field.RegisterBackedField;
import com.xmonit.solar.modbus.field.ModbusFieldList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterBackedFieldTest extends ChargeControllerDependentTest {


    protected void assertFieldValuesEqual(Object expected, Object actual) {
        if ( expected instanceof Number) {
            Number nVal = (Number) expected;
            Number nConvertedVal = (Number) actual;
            assertEquals(nVal.doubleValue(),nConvertedVal.doubleValue(),0.011);
        } else {
            assertEquals(expected, actual );
        }
    }

    @Test
    public void readInputRegistersBackedFields() throws Exception {
        ModbusFieldList modbusFieldList = ModbusFieldList.createInputRegisterBackedFields(chargeController);
        for (ModbusField field : modbusFieldList) {
            RegisterBackedField registerBackedField = (RegisterBackedField)field;
            System.out.println(registerBackedField.name);
            Object val = registerBackedField.readValue();
            Object convertedVal = registerBackedField.fromRegisters(registerBackedField.toRegisters(val));
            assertFieldValuesEqual(val,convertedVal);
            System.out.println("\t" + registerBackedField);
        }
    }

    @Test
    public void readHoldingRegistersBackedFields() throws Exception {
        ModbusFieldList modbusFieldList = ModbusFieldList.createHoldingRegisterBackedFields(chargeController);
        for (ModbusField field : modbusFieldList) {
            RegisterBackedField registerBackedField = (RegisterBackedField)field;
            System.out.println(registerBackedField.name);
            Object val = registerBackedField.readValue();
            Object convertedVal = registerBackedField.fromRegisters(registerBackedField.toRegisters(val));
            assertFieldValuesEqual(val,convertedVal);
            System.out.println("\t" + registerBackedField);
        }
    }
}
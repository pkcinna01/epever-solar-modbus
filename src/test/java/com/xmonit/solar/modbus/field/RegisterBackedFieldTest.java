package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.ChargeControllerDependentTest;
import com.xmonit.solar.modbus.EpeverFieldDefinitions;
import com.xmonit.solar.modbus.field.ModbusField;
import com.xmonit.solar.modbus.field.RegisterBackedField;
import com.xmonit.solar.modbus.field.ModbusFieldList;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
            Object convertedVal = registerBackedField.fromRegisters(0,registerBackedField.toRegisters(val));
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
            Object convertedVal = registerBackedField.fromRegisters(0,registerBackedField.toRegisters(val));
            assertFieldValuesEqual(val,convertedVal);
            System.out.println("\t" + registerBackedField);
        }
    }

    @Test
    public void writeHoldingRegistersBackedFields() throws Exception {
        CodesField manualModeField = (CodesField) EpeverFieldDefinitions.DEFAULT_LOAD_ON_OFF_IN_MANUAL_MODE.create(chargeController);
        int manualModeCode = manualModeField.readValue().intValue();
        System.out.println("Manual mode as integer: " + manualModeCode);
        System.out.println(manualModeField.name + ": " + manualModeField);
        manualModeField.writeValue(BigInteger.ZERO);
        manualModeCode = manualModeField.readValue().intValue();
        assertEquals(0,manualModeCode);
        System.out.println("Manual mode as integer (set to zero): " + manualModeCode);
        System.out.println(manualModeField.name + ": " + manualModeField);

        manualModeField.writeValue("ON");
        manualModeCode = manualModeField.readValue().intValue();
        assertEquals(1,manualModeCode);
        System.out.println("Manual mode as integer (set to 'ON'): " + manualModeCode);
        System.out.println(manualModeField.name + ": " + manualModeField);

        try {
            manualModeField.writeValue("Maybe");
            assertTrue(false); // should never get here
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
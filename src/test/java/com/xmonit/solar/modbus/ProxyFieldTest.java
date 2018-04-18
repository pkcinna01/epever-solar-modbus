package com.xmonit.solar.modbus;

import com.xmonit.solar.modbus.field.ProxyField;
import com.xmonit.solar.modbus.field.ProxyFieldList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProxyFieldTest extends ChargeControllerDependentTest {


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
        ProxyFieldList proxyFieldList = ProxyFieldList.createInputRegisterBackedFields(chargeController);
        for (ProxyField proxyField : proxyFieldList) {
            System.out.println(proxyField.name);
            Object val = proxyField.readValue();
            Object convertedVal = proxyField.fromRegisters(proxyField.toRegisters(val));
            assertFieldValuesEqual(val,convertedVal);
            System.out.println("\t" + proxyField);
        }
    }

    @Test
    public void readHoldingRegistersBackedFields() throws Exception {
        ProxyFieldList proxyFieldList = ProxyFieldList.createHoldingRegisterBackedFields(chargeController);
        for (ProxyField proxyField : proxyFieldList) {
            System.out.println(proxyField.name);
            Object val = proxyField.readValue();
            Object convertedVal = proxyField.fromRegisters(proxyField.toRegisters(val));
            assertFieldValuesEqual(val,convertedVal);
            System.out.println("\t" + proxyField);
        }
    }
}
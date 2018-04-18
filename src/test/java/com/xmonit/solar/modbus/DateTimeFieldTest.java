package com.xmonit.solar.modbus;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;
import com.xmonit.solar.modbus.field.DateTimeField;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class DateTimeFieldTest extends ChargeControllerDependentTest {

    @Test
    public void readValue() throws ChargeControllerException {
        DateTimeField dateTimeField = (DateTimeField) EpeverFieldDefinitions.REAL_TIME_CLOCK.create(chargeController);
        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        System.out.println(localDateTime);
        int[] registers = RegisterConversions.fromDateTime(localDateTime);
        LocalDateTime localDateTime2 = RegisterConversions.toDateTime(registers);
        System.out.println(localDateTime2);
        assertEquals(dateTimeField,localDateTime2);

        System.out.println(dateTimeField.name);
        LocalDateTime localDateTime3 = dateTimeField.readValue();
        System.out.println(localDateTime3);
    }

    @Test
    public void writeValue() {
    }
}
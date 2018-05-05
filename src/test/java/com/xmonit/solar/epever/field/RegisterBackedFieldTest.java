package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.SolarChargerDependentTest;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterBackedFieldTest extends SolarChargerDependentTest {


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
        EpeverFieldList epeverFieldList = EpeverFieldList.createInputRegisterBackedFields(solarCharger);
        for (EpeverField field : epeverFieldList) {
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
        EpeverFieldList epeverFieldList = EpeverFieldList.createHoldingRegisterBackedFields(solarCharger);
        for (EpeverField field : epeverFieldList) {
            RegisterBackedField registerBackedField = (RegisterBackedField)field;
            System.out.println(registerBackedField.name);
            Object val = registerBackedField.readValue();
            Object convertedVal = registerBackedField.fromRegisters(0,registerBackedField.toRegisters(val));
            assertFieldValuesEqual(val,convertedVal);
            System.out.println("\t" + registerBackedField);
        }
    }




    @Ignore
    @Test
    public void writeLengthOfNightDuration() throws Exception {
        DurationField lengthOfNight = (DurationField) EpeverFieldDefinitions.LENGTH_OF_NIGHT.create(solarCharger);
        Duration duration = lengthOfNight.readValue();
        System.out.println(lengthOfNight.name + ": " + lengthOfNight);
        Duration expectedDuration = duration.plusHours(1).plusMinutes(5);
        lengthOfNight.writeValue(expectedDuration);
        lengthOfNight.readValue();
        System.out.println(lengthOfNight.name + ": " + lengthOfNight);
        assertEquals(expectedDuration,lengthOfNight.getValue());

        expectedDuration = Duration.ofHours(10).plusMinutes(20);
        lengthOfNight.writeValue(expectedDuration);
        System.out.println(lengthOfNight.name + ": " + lengthOfNight);
        lengthOfNight.readValue();
        System.out.println(lengthOfNight.name + ": " + lengthOfNight);
        assertEquals(expectedDuration,lengthOfNight.getValue());
    }


    @Ignore
    @Test
    public void writeLoadStartAndStopTimes() throws Exception {

        TimeField turnOnTiming1 = (TimeField) EpeverFieldDefinitions.TURN_ON_TIMING_1.create(solarCharger);
        TimeField turnOffTiming1 = (TimeField) EpeverFieldDefinitions.TURN_OFF_TIMING_1.create(solarCharger);
        turnOnTiming1.readValue();
        turnOffTiming1.readValue();
        System.out.println(turnOnTiming1.name + ": " + turnOnTiming1.toString());
        System.out.println(turnOffTiming1.name + ": " + turnOffTiming1.toString());

        {
            LocalTime onTime = LocalTime.of(8, 30, 0, 0);
            turnOnTiming1.writeValue(onTime);
            turnOnTiming1.readValue();
            System.out.println(turnOnTiming1.name + ": " + turnOnTiming1.toString());
        }
        {
            LocalTime offTime = LocalTime.of(18, 30, 0, 0);
            turnOffTiming1.writeValue(offTime);
            turnOffTiming1.readValue();
            System.out.println(turnOffTiming1.name + ": " + turnOffTiming1.toString());
        }
    }

}
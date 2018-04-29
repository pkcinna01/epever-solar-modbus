package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.EpeverFieldDefinitions;
import com.xmonit.solar.epever.SolarChargerDependentTest;
import com.xmonit.solar.epever.EpeverException;
import com.xmonit.solar.epever.units.HexCodes;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CodesFieldTest extends SolarChargerDependentTest {

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
    public void readValue() throws EpeverException {
        List<EpeverField> registerBackedFieldList = new EpeverFieldList(solarCharger).stream().filter((f)-> f.unit instanceof HexCodes).collect(Collectors.toList());

        for (EpeverField registerBackedField : registerBackedFieldList) {
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


    @Ignore
    @Test
    public void writeManualModeByValueOrCodeName() throws Exception {
        CodesField manualMode = (CodesField) EpeverFieldDefinitions.DEFAULT_LOAD_ON_OFF_IN_MANUAL_MODE.create(solarCharger);
        int manualModeCode = manualMode.readValue().intValue();
        System.out.println("Manual mode as integer: " + manualModeCode);
        System.out.println(manualMode.name + ": " + manualMode);
        manualMode.writeValue(BigInteger.ZERO);
        manualModeCode = manualMode.readValue().intValue();
        assertEquals(0,manualModeCode);
        System.out.println("Manual mode as integer (set to zero): " + manualModeCode);
        System.out.println(manualMode.name + ": " + manualMode);

        manualMode.writeValue("ON");
        manualModeCode = manualMode.readValue().intValue();
        assertEquals(1,manualModeCode);
        System.out.println("Manual mode as integer (set to 'ON'): " + manualModeCode);
        System.out.println(manualMode.name + ": " + manualMode);

        try {
            manualMode.writeValue("Maybe");
            assertTrue(false); // should never get here
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Ignore
    @Test
    public void writeLoadControlMode() throws Exception {
        CodesField loadControlMode = (CodesField) EpeverFieldDefinitions.LOAD_CONTROLING_MODES.create(solarCharger);
        loadControlMode.readValue();
        System.out.println(loadControlMode.name + ": " + loadControlMode.toString());

        loadControlMode.writeValue("Time Control");
        loadControlMode.readValue();
        System.out.println(loadControlMode.name + ": " + loadControlMode.toString());

    }
}
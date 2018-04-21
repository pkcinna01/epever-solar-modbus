package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.SolarChargerDependentTest;
import com.xmonit.solar.epever.EpeverException;
import com.xmonit.solar.epever.units.HexCodes;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

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
}
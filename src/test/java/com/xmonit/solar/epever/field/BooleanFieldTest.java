package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.SolarChargerDependentTest;
import com.xmonit.solar.epever.EpeverFieldDefinitions;
import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanFieldTest extends SolarChargerDependentTest {

    @Test
    public void readCoilsAndDiscreteInputBackedFields() throws Exception {
        EpeverFieldList epeverFieldList = EpeverFieldList.createBooleanBackedFields(solarCharger);
        for (EpeverField field : epeverFieldList) {
           BooleanField booleanField = (BooleanField)field;
            System.out.println(booleanField.name);
            Boolean val = booleanField.readValue();
            System.out.println("\t" + booleanField);
        }
    }

    @Test
    public void writeCoil() throws Exception {
        BooleanField loadTestModeField =  (BooleanField) EpeverFieldDefinitions.LOAD_TEST_MODE.create(solarCharger);
        BooleanField loadTestModeField2 = new BooleanField(0x0005, "Load Test Mode On").setSolarCharger(solarCharger);

        assertEquals(loadTestModeField.name,loadTestModeField2.name);
        assertEquals(loadTestModeField.readValue(),loadTestModeField2.readValue());

        System.out.println(loadTestModeField.name);
        System.out.println("\t" + loadTestModeField);
        loadTestModeField.writeValue(true);
        assertEquals(true,loadTestModeField.readValue());
        System.out.println(loadTestModeField.name);
        System.out.println("\t" + loadTestModeField);

        loadTestModeField2.writeValue(false);
        assertEquals(loadTestModeField.readValue(),loadTestModeField2.readValue());
        assertEquals(false,loadTestModeField2.getValue());
        System.out.println(loadTestModeField.name);
        System.out.println("\t" + loadTestModeField);

    }
}
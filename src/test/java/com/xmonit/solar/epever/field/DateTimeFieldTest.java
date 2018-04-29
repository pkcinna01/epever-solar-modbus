package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.SolarChargerDependentTest;
import com.xmonit.solar.epever.EpeverException;
import com.xmonit.solar.epever.EpeverFieldDefinitions;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class DateTimeFieldTest extends SolarChargerDependentTest {

    @Test
    public void readValue() throws EpeverException {
        DateTimeField dateTimeField = (DateTimeField) EpeverFieldDefinitions.REAL_TIME_CLOCK.create(solarCharger);
        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        System.out.println(localDateTime);
        int[] registers = RegisterConversions.fromDateTime(localDateTime);
        LocalDateTime localDateTime2 = RegisterConversions.toDateTime(0,dateTimeField.registerCount,registers);
        System.out.println(localDateTime2);

        assertEquals(localDateTime,localDateTime2);

        System.out.println(dateTimeField.name);
        LocalDateTime localDateTime3 = dateTimeField.readValue();
        System.out.println(localDateTime3);
    }

    @Ignore
    @Test
    public void writeValue() throws EpeverException {
        DateTimeField dateTimeField = (DateTimeField) EpeverFieldDefinitions.REAL_TIME_CLOCK.create(solarCharger);
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        System.out.println("Now: " + now);

        System.out.println(dateTimeField.name);
        dateTimeField.writeValue(now);
        LocalDateTime receivedDateTime = dateTimeField.readValue();
        System.out.println(dateTimeField);
        assertEquals(now,receivedDateTime);
    }
}
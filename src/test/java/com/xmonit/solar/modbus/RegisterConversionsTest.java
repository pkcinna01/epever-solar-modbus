package com.xmonit.solar.modbus;

import static org.junit.Assert.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.Collectors;
import static com.xmonit.solar.modbus.RegisterConversions.*;

public class RegisterConversionsTest {

    @Test
    public void doubleConversions() {

        Double expectedDouble = 65792.0;
        int[] expectedRegs = new int[]{ 0x0100, 0x0001 }; //65792

        double actualDouble = toDouble(expectedRegs,1);

        assertEquals(expectedDouble,actualDouble, 0);

        expectedDouble = expectedDouble/100.0;
        assertEquals(expectedDouble,toDouble(expectedRegs,100), 0);

        expectedRegs = new int[] { 0x0291 }; // 657

        int[] actualRegs = fromInteger(expectedDouble.intValue(),1);

        assertArrayEquals(expectedRegs, actualRegs);
    }

    @Test
    public void dateConversions() {

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        int[] registers = RegisterConversions.fromDateTime(now);
        LocalDateTime actual = RegisterConversions.toDateTime(registers);

        assertEquals(now,actual);
    }

}
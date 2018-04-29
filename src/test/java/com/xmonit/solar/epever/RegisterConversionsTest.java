package com.xmonit.solar.epever;

import static org.junit.Assert.*;

import com.xmonit.solar.epever.field.RegisterConversions;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.IntStream;

import static com.xmonit.solar.epever.field.RegisterConversions.*;

public class RegisterConversionsTest {

    @Test
    public void doubleConversions() {

        Double expectedDouble = 65792.0;
        int[] expectedRegs = new int[]{ 0x0100, 0x0001 }; //65792
        int[] expectedRegs2 = new int[]{ 0, 0, 0x0100, 0x0001 }; //65792
        int[] expectedRegs3 = new int[]{ 0, 0x0100, 0x0001, 0 }; //65792

        double actualDouble = toDouble(0,expectedRegs.length,expectedRegs,1);

        assertEquals(expectedDouble,actualDouble, 0);

        expectedDouble = expectedDouble/100.0;

        assertEquals(expectedDouble,toDouble(0,2,expectedRegs,100), 0);
        assertEquals(expectedDouble,toDouble(2,2,expectedRegs2,100), 0);
        assertEquals(expectedDouble,toDouble(1,2,expectedRegs3,100), 0);

        expectedRegs = new int[] { 0x0291 }; // 657

        int[] actualRegs = fromInteger(expectedDouble.intValue(),1);

        assertArrayEquals(expectedRegs, actualRegs);

        int[] data = new int[] { 0x1939,0x0000,0x05A4,0x0496,0x0002,0x0000}; //0x1939H = 6457
        assertEquals(6457, toBigDecimal(0,1,data,1).doubleValue(), 0 );
        assertEquals(64.57, toBigDecimal(0,1,data,100).doubleValue(), 0 );

        data = new int[] { 0x0000, 0x0000, 0x1939, 0x0001, 0x0002, 3 };
        assertEquals( 6457, toBigDecimal(2,1,data,1).doubleValue(), 0 );
        assertEquals( 6457+Math.pow(2,16), toBigDecimal(2,2,data,1).doubleValue(), 0 );
    }

    @Test
    public void dateConversions() {

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        int[] registers = RegisterConversions.fromDateTime(now);
        LocalDateTime actual = RegisterConversions.toDateTime(0,3,registers);
        assertEquals(now,actual);

        registers = IntStream.concat(Arrays.stream(new int[]{0,0,0}),Arrays.stream(registers)).toArray();
        actual = RegisterConversions.toDateTime(3,3,registers);
        assertEquals(now,actual);
    }

    @Test
    public void negativeAndPositiveValues() {
        double[] testVals = {294.94, -294.94, 326.64, -326.64, 900.1, -900.1};

        for(double testVal:testVals) {
            BigDecimal bigDecimalVal = BigDecimal.valueOf(testVal);

            int[] registers = RegisterConversions.fromBigDecimal(bigDecimalVal,100);
            byte[] registersAsBytes = RegisterConversions.toBytes(0,registers.length,registers);
            System.out.println( "testVal: " + testVal);
            System.out.println( "\tregisters  (as ints): " + RegisterConversions.registersToString(0,registers.length,registers));
            System.out.println( "\tregisters (as bytes): " + RegisterConversions.bytesToString(registersAsBytes));
            System.out.println( "new testVal: " + RegisterConversions.toBigDecimal(0,registers.length,registers,100).doubleValue());
        }

        int lastVal = 0;
        for ( int val = 0x0000; val <= 0xFFFF; val++ ) {
            int[] registers = RegisterConversions.fromBigDecimal(BigDecimal.valueOf(val),100);
            double newVal = RegisterConversions.toBigDecimal(0,registers.length,registers,100).doubleValue();
            if ( newVal * val< 0 ) {
                System.out.println("Sign changed for " + val + " (newVal=" +newVal + ") " + String.format(" 0x%02X", newVal));
            }
            lastVal = val;
        }
    }

}
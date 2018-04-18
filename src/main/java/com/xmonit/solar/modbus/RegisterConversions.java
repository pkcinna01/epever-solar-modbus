package com.xmonit.solar.modbus;

import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RegisterConversions {

    public static int[] fromBytes(byte[] bytes) {
        int[] ints = new int[(bytes.length+bytes.length%2)/2];
        for( int i = 0; i < ints.length; i++ ) {
            ints[i] = bytes[2*i] & 0xFF;
            if ( 2*i < bytes.length-1 ) {
                ints[i] |= bytes[2*i+1] << 8;
            }
        }
        return ints;
    }

    public static int[] fromBigDecimal(BigDecimal val, int multiplier) {
        byte[] bytes = val.multiply(new BigDecimal(multiplier)).toBigInteger().toByteArray();
        ArrayUtils.reverse(bytes);
        return fromBytes(bytes);
    }

    public static int[] fromBigInteger(BigInteger val, int multiplier) {
        byte[] bytes = val.multiply(BigInteger.valueOf(multiplier)).toByteArray();
        ArrayUtils.reverse(bytes);
        return fromBytes(bytes);
    }

    public static int[] fromInteger(int val, int multiplier) {
        return fromBigInteger(BigInteger.valueOf(val), multiplier);
    }

    public static int[] fromDateTime(LocalDateTime dateTime) {
        byte[] bytes = new byte[6];
        bytes[0] = (byte) dateTime.getSecond();
        bytes[1] = (byte) dateTime.getMinute();
        bytes[2] = (byte) dateTime.getHour();
        bytes[3] = (byte) dateTime.getDayOfMonth();
        bytes[4] = (byte) dateTime.getMonthValue();
        bytes[5] = (byte) (dateTime.getYear() - 2000);
        return fromBytes(bytes);
    }

    public static int[] fromTime(LocalTime time, int registerCount) {
        if ( registerCount == 1 ) {
            // just have room for hour and minute
            byte[] bytes = new byte[2];
            bytes[0] = (byte) time.getMinute();
            bytes[1] = (byte) time.getHour();
            return fromBytes(bytes);
        } else if ( registerCount == 3 ) {
            int[] registers = new int[3];
            registers[0] = time.getSecond();
            registers[1] = time.getMinute();
            registers[2] = time.getHour();
            return registers;
        } else {
            throw new RuntimeException("Unsupported register count during LocalTime to registers conversion");
        }
    }


    public static byte[] toBytes(int[] registers) {
        byte[] bytes = new byte[registers.length*2];
        for( int i = 0; i < registers.length; i++ ) {
            bytes[2*i] = (byte)(registers[i] & 0xFF);
            bytes[2*i+1] = (byte)(registers[i] >> 8);
        }
        return bytes;
    }

    public static BigDecimal toBigDecimal(int[] registers, int denominator) {
        byte[] bytes = toBytes(registers);
        ArrayUtils.reverse(bytes);
        BigInteger bigInt = new BigInteger(bytes);
        return new BigDecimal(bigInt).divide(new BigDecimal(denominator));
    }

    public static BigInteger toBigInteger(int[] registers, int denominator) {
        byte[] bytes = toBytes(registers);
        ArrayUtils.reverse(bytes);
        return new BigInteger(bytes).divide( BigInteger.valueOf(denominator));
    }

    public static double toDouble(int[] registers, int denominator) {
        return toBigDecimal(registers, denominator).doubleValue();
    }

    public static LocalDateTime toDateTime(int[] registers) {
        byte[] bytes = RegisterConversions.toBytes(registers);

        byte hours = bytes[2], minutes = bytes[1], seconds = bytes[0];
        LocalTime time = LocalTime.of(hours,minutes,seconds);

        byte years = bytes[5], months = bytes[4], days = bytes[3];
        LocalDate date = LocalDate.of( 2000 + years, months, days);

        return LocalDateTime.of(date,time);
    }

    public static LocalTime toTime(int[] registers) {

        LocalTime time = null;

        if ( registers.length == 1 ) {
            // just have hours and minutes from single register
            byte[] bytes = RegisterConversions.toBytes(registers);
            time = LocalTime.of(bytes[1],bytes[0],0);
        } else if ( registers.length == 3 ) {
            int hours = registers[2], minutes = registers[1], seconds = registers[0];
            time = LocalTime.of(hours,minutes,seconds);
        } else {
            throw new RuntimeException("Unsupported register count during registers to LocalTime conversion");
        }

        return time;
    }

}
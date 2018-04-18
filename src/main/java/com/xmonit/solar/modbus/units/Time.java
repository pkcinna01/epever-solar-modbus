package com.xmonit.solar.modbus.units;

public abstract class Time extends Unit {


    public Time(String name,int registerCnt) {
        super(name, name);
        this.registerCnt = registerCnt;
    }

    public int registerCnt;

    public static class HoursMinutes extends Time {

        public HoursMinutes(String name) {
            super(name,2);
        }
    }

    public static class SecondsMinutesHours extends Time {
        public SecondsMinutesHours(String name) {
            super(name,3);
        }

        @Override
        public String asString(Object val) {
            return val.toString();
        }
    }

    public static class SecondsMinutesHoursDayMonthYear extends Time {
        public SecondsMinutesHoursDayMonthYear(String name) {
            super(name,3);
        }

        @Override
        public String asString(Object val) {
            return val.toString();
        }
    }

}

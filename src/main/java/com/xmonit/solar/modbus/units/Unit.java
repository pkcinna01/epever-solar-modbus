package com.xmonit.solar.modbus.units;

import java.time.LocalTime;

public class Unit {

    static class EmptyUnit extends Unit {
        EmptyUnit(String name, String abbr) {
            super(name,abbr);
        }

        @Override
        public String asString(Object val) {
            return val != null ? val.toString() : null;
        }
    }

    public static final Unit
            Volts = new Unit("VoltageField","V"),
            Amps = new Unit("Ampere", "A"),
            AmpHours = new Unit("Ampere hours", "Ah"),
            Watts = new Unit("Watt", "W"),
            Celcius = new Unit("Degrees Celsius", "\u2103" ), //"\u00b0"),
            Fahrenheit = new Unit("Degrees Fahrenheit", "\u2109" ), //"\u00b0"),
            Percent = new Unit("%, PercentageField", "%"),
            KWH = new Unit("KWH, Kilowatt/Hour", "KWH"),
            Ton = new Unit("1000kg", "t"),
            mOHM = new Unit("milliohm", "mOHM"),
            Int = new EmptyUnit("integer", ""),
            Bool = new EmptyUnit("boolean", "true or false"),
            Minutes = new Unit("minutes", "min"),
            Time = new Time.SecondsMinutesHours("time"),
            DateTime = new Time.SecondsMinutesHoursDayMonthYear("date/time"),
            Duration = new Unit("duration (hh:mm)", "hh:mm") {
                @Override
                public String asString(Object val) {
                    LocalTime t = (LocalTime) val;
                    return val != null ? String.format("%02d:%02d (%s)",t.getHour(),t.getMinute(),abbr) : null;
                }
            };

    public String name, abbr;
    String strFormat;

    public Unit(String name, String abbr, String strFormat) {
        this.name = name;
        this.abbr = abbr;
        this.strFormat = strFormat;
    }

    public Unit(String name, String abbr) {
        this(name,abbr,"%04.2f" );
    }

    public String asString( Object val ) {
        return String.format(strFormat,val) + " " + abbr;
    }

    public String getDescription() {
        return name + " (" + abbr + ")";
    }
}

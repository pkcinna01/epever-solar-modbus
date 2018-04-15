package com.xmonit.solar.modbus;

public class RegisterUnit {

    static final RegisterUnit
            Volts = new RegisterUnit("Voltage","V"),
            Amps = new RegisterUnit("Ampere", "A"),
            AmpHours = new RegisterUnit("Ampere hours", "Ah"),
            Watts = new RegisterUnit("Watt", "W"),
            Celcius = new RegisterUnit("Degrees Celsius", "\0xb0C"),
            Percent = new RegisterUnit("%, Percentage", "%"),
            KWH = new RegisterUnit("KWH, Kilowatt/Hour", "KWH"),
            Ton = new RegisterUnit("1000kg", "t"),
            mOHM = new RegisterUnit("milliohm", "mOHM"),
            Int = new RegisterUnit("integer", ""),
            Bool = new RegisterUnit("boolean", ""),
            Seconds = new RegisterUnit("seconds", "s"),
            Minutes = new RegisterUnit("minutes", "min"),
            Hours = new RegisterUnit("hours", "h"),
            DateTime = new RegisterUnit("date and time", "date/time");//YYMMDDhhmmss

    String name, abbr;

    public RegisterUnit(String name, String abbr) {
        this.name = name;
        this.abbr = abbr;
    }
}

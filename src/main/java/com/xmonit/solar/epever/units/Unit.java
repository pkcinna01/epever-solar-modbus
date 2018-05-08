package com.xmonit.solar.epever.units;

import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import java.time.Duration;

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
            Volts = new Unit("Volt(s)","V"),
            Amps = new Unit("Amp(s)", "A"),
            AmpHours = new Unit("Amp hour(s)", "Ah"),
            Watts = new Unit("Watt(s)", "W"),
            Celcius = new Unit("Degrees Celsius", "\u2103" ), //"\u00b0"),
            Fahrenheit = new Unit("Degrees Fahrenheit", "\u2109" ), //"\u00b0"),
            Percent = new Unit("Percent", "%"),
            KWH = new Unit("Kilowatt(s)/Hour", "KWH"),
            Ton = new Unit("Ton(s)", "t"),
            mOHM = new Unit("milliohm", "mOHM"),
            Int = new EmptyUnit("integer", ""),
            Bool = new EmptyUnit("boolean", "true or false"),
            Minutes = new Unit("minute(s)", "min"),
            Time = new Time.SecondsMinutesHours("time"),
            DateTime = new Time.SecondsMinutesHoursDayMonthYear("date/time"),
            Duration = new Unit("duration (hh:mm)", "hh:mm") {
                @Override
                public String asString(Object val) {
                    Duration d = (java.time.Duration) val;
                    long hours = d.toHours();
                    long minutes = d.minusHours(hours).toMinutes();
                    return val != null ? String.format("%02d:%02d (%s)",hours,minutes,abbr) : null;
                }
            };

    public String name, abbr;
    String strFormat;

    public Unit(String name, String abbr, String strFormat) {
        this.name = name;
        this.abbr = abbr;
        this.strFormat = strFormat;
    }


    public ObjectNode asJson(){
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode n = factory.objectNode();
        n.put("name",factory.textNode(name));
        n.put("abbr",factory.textNode(abbr));
        n.put("description", factory.textNode(getDescription()));
        return n;
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

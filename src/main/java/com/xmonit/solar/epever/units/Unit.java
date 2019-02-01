package com.xmonit.solar.epever.units;


import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
            Volts = new Unit("Volts","V"),
            Amps = new Unit("Amps", "A"),
            AmpHours = new Unit("Amp Hours", "Ah"),
            Watts = new Unit("Watts", "W"),
            Celcius = new Unit("Degrees Celsius", "\u2103" ), //"\u00b0"),
            Fahrenheit = new Unit("Degrees Fahrenheit", "\u2109" ), //"\u00b0"),
            Percent = new Unit("Percent", "%"),
            KWH = new Unit("Kilowatts/Hour", "KWH"),
            Ton = new Unit("Tons", "t"),
            mOHM = new Unit("milliohm", "mOHM"),
            Int = new EmptyUnit("integer", ""),
            Bool = new EmptyUnit("boolean", "true or false"),
            Minutes = new Unit("minutes", "min"),
            Time = new Time.SecondsMinutesHours("time"),
            DateTime = new Time.SecondsMinutesHoursDayMonthYear("datetime"),
            Duration = new Unit("duration", "hh:mm") {
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
        n.set("name",factory.textNode(name));
        n.set("abbr",factory.textNode(abbr));
        n.set("description", factory.textNode(getDescription()));
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

package com.xmonit.solar.modbus;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;


public class Registers extends ArrayList<Register> {

    public static Registers createInputRegisters() {
        return new Registers( (def) -> {
            return def.ordinal() >= 0x3000 && def.ordinal() < 0x9000;
        });
    }

    public Registers() {
        this( (def) -> {return true;} );
    }

    public Registers(Predicate<RegisterDefinition> filter) {
        for ( RegisterDefinition def : RegisterDefinition.values() ) {
            if ( filter.test(def) ) {
                add(def.create());
            }
        }
    }
}

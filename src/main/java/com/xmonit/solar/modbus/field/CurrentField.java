package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.units.Unit;


public class CurrentField extends FloatField {

    public CurrentField(int addr, String name, String description){

        this(addr,name,description,100,1);
    }

    public CurrentField(int addr, String name, String description, int multiplier, int registerCount){

        super(addr,Unit.Amps,name,description,multiplier,registerCount);
    }

}

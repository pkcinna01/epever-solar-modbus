package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.units.Unit;

public class VoltageField extends FloatField {

    public VoltageField(int addr, String name, String description){

        super(addr,Unit.Volts,name,description,100,1);
    }

}

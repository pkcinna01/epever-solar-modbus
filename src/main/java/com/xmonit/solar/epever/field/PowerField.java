package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.units.Unit;

//NOTE: some power fields are 2 registers so use setCount on those since the default is 1 regesters here

public class PowerField extends FloatField {

    public PowerField(int addr, String name, String description){

        super(addr,Unit.Watts,name,description,100,1);
        bSigned = false;
    }

}

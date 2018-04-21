package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.units.Unit;

public class PercentageField extends FloatField {

    public PercentageField(int addr, String name, String description){

        super(addr,Unit.Percent,name,description,1,1);
    }

}

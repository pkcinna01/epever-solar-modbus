package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.units.Unit;

public class PercentageField extends FloatField {

    public PercentageField(int addr, String name, String description){

        super(addr,Unit.Percent,name,description,1,1);
    }

}

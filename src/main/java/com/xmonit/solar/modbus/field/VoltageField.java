package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.units.Unit;

public class VoltageField extends FloatField {

    public VoltageField(int addr, String name, String description){

        super(addr,Unit.Volts,name,description,100,1);
    }

}

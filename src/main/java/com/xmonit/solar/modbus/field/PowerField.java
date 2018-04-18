package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.units.Unit;

//NOTE: some power fields are only one register so use setRegCnt on those since the default is 2 regesters here

public class PowerField extends FloatField {

    public PowerField(int addr, String name, String description){

        super(addr,Unit.Watts,name,description,100,2);
    }

}

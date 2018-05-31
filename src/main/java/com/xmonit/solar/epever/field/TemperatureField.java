package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.units.Unit;


public class TemperatureField extends FloatField {


    public TemperatureField(int addr, String name, String description) {
        this(addr, Unit.Fahrenheit, name, description);
    }


    public TemperatureField(int addr, Unit unit, String name, String description) {
        super(addr, unit, name, description, 100, 1);
    }

    //
    // charge controller is always in celcius so adjust before getting/setting when units is Fahrenheit
    //

    @Override
    public java.lang.Float fromRegisters(int offset, int[] registers) {
        java.lang.Float celciusVal = super.fromRegisters(offset, registers);
        if (unit != Unit.Fahrenheit) {
            return celciusVal;
        } else {
            return celciusVal * 9.0f / 5.0f + 32;
        }
    }

    @Override
    public int[] toRegisters(java.lang.Float val) {
        if ( unit == Unit.Fahrenheit ) {
            val = (val - 32) * 5.0f / 9.0f;
        }
        return super.toRegisters(val);
    }

}

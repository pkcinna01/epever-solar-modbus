package com.xmonit.solar.modbus.field;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.xmonit.solar.modbus.units.Unit;


public class TemperatureField extends FloatField {


    public TemperatureField(int addr, String name, String description) {
        this(addr, Unit.Celcius, name, description);
    }
    public TemperatureField(int addr, Unit unit, String name, String description) {
        super(addr, unit, name, description, 100, 1);
    }

    //
    // charge controller is always in celcius so adjust before getting/setting when units is Fahrenheit
    //

    @Override
    public java.lang.Float fromRegisters(int[] registers) {
        java.lang.Float celciusVal = super.fromRegisters(registers);
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

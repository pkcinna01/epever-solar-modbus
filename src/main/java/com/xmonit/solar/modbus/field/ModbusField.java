package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.ChargeController;
import com.xmonit.solar.modbus.ChargeControllerException;
import com.xmonit.solar.modbus.units.Unit;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

abstract public class ModbusField<T> {

    public String name;
    public int addr;
    public Unit unit;

    protected String description;

    protected T value;
    protected LocalDateTime commitTime;
    protected ChargeController chargeController;

    public ModbusField(int addr, Unit unit, String name, String description) {
        this.addr = addr;
        this.unit = unit;
        this.name = name;
        this.description = description;
    }

    abstract public T readValue() throws ChargeControllerException;

    abstract public void writeValue(T val) throws ChargeControllerException;

    public Map<String,Object> getMetaData() {
        return new HashMap<String,Object>(){{
            put("addr",addr);
            put("unit", unit.abbr);
            put("name",name);
            put("description",getDescription());
        }};
    }

    public boolean isCoilBacked() {
        return addr < 0x1000;
    }

    public boolean isDiscreteInputBacked() {
        return addr >= 0x1000 && addr < 0x3000;
    }

    public boolean isInputRegisterBacked() {
        return addr >= 0x3000 && addr < 0x9000;
    }

    public boolean isHoldingRegisterBacked() {
        return addr >= 0x9000;
    }

    public T getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public ModbusField setChargeController(ChargeController cc) {
        this.chargeController = cc;
        return this;
    }

    @Override
    public String toString() {
        return unit.asString(value);
    }




}

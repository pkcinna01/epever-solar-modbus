package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.SolarCharger;
import com.xmonit.solar.epever.EpeverException;
import com.xmonit.solar.epever.units.Unit;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

abstract public class EpeverField<T> {

    public String name;
    public int addr;
    public Unit unit;

    protected String description;

    protected T value;
    protected LocalDateTime commitTime;
    protected SolarCharger solarCharger;

    public EpeverField(int addr, Unit unit, String name, String description) {
        this.addr = addr;
        this.unit = unit;
        this.name = name;
        this.description = description;
    }

    abstract public T readValue() throws EpeverException;

    abstract public void writeValue(T val) throws EpeverException;

    abstract int getCount();

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

    public boolean isBitBacked() { return isCoilBacked() || isDiscreteInputBacked(); }

    public boolean isInputRegisterBacked() {
        return addr >= 0x3000 && addr < 0x9000;
    }

    public boolean isHoldingRegisterBacked() {
        return addr >= 0x9000;
    }

    public boolean isRegisterBacked() { return isInputRegisterBacked() || isHoldingRegisterBacked(); }

    public T getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public EpeverField setSolarCharger(SolarCharger cc) {
        this.solarCharger = cc;
        return this;
    }

    @Override
    public String toString() {
        return unit.asString(value);
    }




}

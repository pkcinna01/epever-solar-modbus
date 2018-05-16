package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.EpeverException;
import com.xmonit.solar.epever.units.Unit;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;


public abstract class RegisterBackedField<T> extends EpeverField<T> {


    public int denominator;
    public int registerCount;

    abstract public T fromRegisters(int offset, int[] registers);

    abstract public int[] toRegisters(T val);


    public RegisterBackedField(int addr, Unit unit, String name, String description, int denominator, int registerCount) {
        super(addr,unit,name,description);
        this.registerCount = registerCount;
        this.denominator = denominator;
    }


    public RegisterBackedField(int addr, Unit unit, String name, String description) {
        this(addr, unit, name, description, 100, 1);
    }


    public RegisterBackedField(int addr, Unit unit, String name, String description, int denominator) {
        this(addr, unit, name, description, denominator, 1);
    }


    @Override
    public int getCount() {
        return registerCount;
    }


    public int[] readRegisters() throws EpeverException {
        int[] rtnRegisters;
        if ( isInputRegisterBacked() ) {
            rtnRegisters = solarCharger.readInputRegisters(addr, registerCount);
        } else if ( isHoldingRegisterBacked() ) {
            rtnRegisters = solarCharger.readHoldingRegisters(addr, registerCount);
        } else {
            throw new EpeverException("Invalid address when resolving register type: " + addr);
        }
        return rtnRegisters;
    }


    @Override
    public T readValue() throws EpeverException {
        int registers[] = readRegisters();
        return readValue(0, registers);
    }


    public T readValue(int offset, int[] registers) {
        value = fromRegisters(offset, registers);
        commitTime = LocalDateTime.now();
        return value;
    }


    public RegisterBackedField<T> setCount(int cnt) {
        this.registerCount = cnt;
        return this;
    }



    public RegisterBackedField<T> setDenominator(int d) {
        this.denominator = d;
        return this;
    }


    public int[] toRegisters() {
        return toRegisters(getValue());
    }


    @Override
    public void writeValue(T val) throws EpeverException {
        int[] registers = toRegisters(val);
        if ( registers.length > getCount() ) {
            throw new EpeverException("Conversion to modbus register(s) failed. Field has " + getCount()
            + " registers assigned but value encoding used " + registers.length + ". Value: " + val);
        } else if ( registers.length != getCount() ) {
            registers = Arrays.copyOf(registers,getCount());
        }

        solarCharger.writeRegisters(addr,registers);
        this.value = val;
        this.commitTime = LocalDateTime.now();
    }

}

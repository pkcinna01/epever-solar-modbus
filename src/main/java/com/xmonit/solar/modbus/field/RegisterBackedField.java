package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.ChargeControllerException;
import com.xmonit.solar.modbus.units.Unit;

import java.time.LocalDateTime;
import java.util.Map;


public abstract class RegisterBackedField<T> extends ModbusField<T> {


    public int denominator;
    public int registerCount;

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

    abstract public T fromRegisters(int offset, int[] registers);

    abstract public int[] toRegisters(T val);

    @Override
    public int getCount() {
        return registerCount;
    }

    @Override
    public Map<String,Object> getMetaData() {
        Map m = super.getMetaData();
        m.put("registerCount",registerCount);
        m.put("denominator", denominator);
        return m;
    }

    @Override
    public T readValue() throws ChargeControllerException {
        int registers[] = readRegisters();
        return readValue(0, registers);
    }

    public T readValue(int offset, int[] registers) {
        value = fromRegisters(offset, registers);
        commitTime = LocalDateTime.now();
        return value;
    }

    @Override
    public void writeValue(T val) throws ChargeControllerException {
        chargeController.writeRegisters(addr,toRegisters(val));
        this.value = val;
        this.commitTime = LocalDateTime.now();
    }

    public RegisterBackedField<T> setCount(int cnt) {
        this.registerCount = cnt;
        return this;
    }

    public RegisterBackedField<T> setDenominator(int d) {
        this.denominator = d;
        return this;
    }

    public int[] readRegisters() throws ChargeControllerException {
        int[] rtnRegisters;
        if ( isInputRegisterBacked() ) {
            rtnRegisters = chargeController.readInputRegisters(addr, registerCount);
        } else if ( isHoldingRegisterBacked() ) {
            rtnRegisters = chargeController.readHoldingRegisters(addr, registerCount);
        } else {
            throw new ChargeControllerException("Invalid address when resolving register type: " + addr);
        }
        return rtnRegisters;
    }

}

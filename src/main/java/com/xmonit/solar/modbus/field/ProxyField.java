package com.xmonit.solar.modbus.field;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.xmonit.solar.modbus.ChargeController;
import com.xmonit.solar.modbus.ChargeControllerException;
import com.xmonit.solar.modbus.EpeverChargeController;
import com.xmonit.solar.modbus.units.Unit;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.intelligt.modbus.jlibmodbus.Modbus.MIN_SERVER_ADDRESS;


@Data
public abstract class ProxyField<T> {


    public Unit unit;
    public int denominator;
    public int registerCount;
    public String name;
    public int addr;

    protected String description;

    protected T value;
    protected LocalDateTime commitTime;
    protected ChargeController chargeController;

    public ProxyField(int addr, Unit unit, String name, String description, int denominator, int registerCount) {
        this.addr = addr;
        this.unit = unit;
        this.name = name;
        this.description = description;
        this.registerCount = registerCount;
        this.denominator = denominator;
    }

    public ProxyField(int addr, Unit unit, String name, String description) {
        this(addr, unit, name, description, 100, 1);
    }

    public ProxyField(int addr, Unit unit, String name, String description, int denominator) {
        this(addr, unit, name, description, denominator, 1);
    }

    abstract public T fromRegisters(int[] registers);

    abstract public int[] toRegisters(T val);

    public boolean isCoilBacked() {
        return addr < 0x1000;
    }

    public boolean isDescreteInputRegisterBacked() {
        return addr >= 0x1000 && addr < 0x3000;
    }

    public boolean isInputRegisterBacked() {
        return addr >= 0x3000 && addr < 0x9000;
    }

    public boolean isHoldingRegisterBacked() {
        return addr >= 0x9000;
    }

    public String getDescription() {
        return description;
    }

    public Map<String,Object> getMetaData() {
        return new HashMap<String,Object>(){{
            put("addr",addr);
            put("unit", unit.abbr);
            put("name",name);
            put("description",getDescription());
            put("registerCount",registerCount);
            put("denominator", denominator);

        }};
    }

    public ProxyField setChargeController(ChargeController cc) {
        this.chargeController = cc;
        return this;
    }

    public T getValue() {
        return value;
    }

    public T readValue() throws ChargeControllerException {
        int registers[] = readRegisters();
        value = fromRegisters(registers);
        commitTime = LocalDateTime.now();
        return value;
    }

    public void writeRegisters(T val) throws ChargeControllerException {
        chargeController.writeRegisters(addr,toRegisters(val));
        this.value = val;
        this.commitTime = LocalDateTime.now();
    }

    public ProxyField<T> setRegCnt(int cnt) {
        this.registerCount = cnt;
        return this;
    }

    public ProxyField<T> setDenominator(int d) {
        this.denominator = d;
        return this;
    }

    @Override
    public String toString() {
        return unit.asString(value);
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

package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.RegisterConversions;
import com.xmonit.solar.modbus.units.Unit;

import java.math.BigDecimal;

public class DecimalField extends RegisterBackedField<BigDecimal> {


    public DecimalField(int addr, Unit unit, String name, String description, int multiplier, int registerCount) {
        super(addr, unit, name, description, multiplier, registerCount);
    }

    @Override
    public BigDecimal fromRegisters(int[] registers) {
        return RegisterConversions.toBigDecimal(registers, denominator);
    }

    @Override
    public int[] toRegisters(BigDecimal val) {
       return RegisterConversions.fromBigDecimal(val, denominator);
    }
}

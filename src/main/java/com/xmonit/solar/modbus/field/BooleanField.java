package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.ChargeController;
import com.xmonit.solar.modbus.ChargeControllerException;
import com.xmonit.solar.modbus.units.Unit;

import java.time.LocalDateTime;
import java.util.Arrays;

public class BooleanField extends ModbusField<Boolean> {

    BooleanList impl;

    public BooleanField(int addr, String name) {
        this(addr,name,"");
    }

    public BooleanField(int addr, String name, String description) {
        super(addr, Unit.Bool, name, description);
        impl = new BooleanList(addr,name,description);
    }

    public BooleanField setBitCount(int cnt) {
        if ( cnt != 1 ) {
            throw new IndexOutOfBoundsException("BooleanField type requires bit count to be 1");
        }
        return this;
    }

    @Override
    public Boolean readValue() throws ChargeControllerException {
        this.value = impl.readValue().get(0);
        this.commitTime = LocalDateTime.now();
        return this.value;
    }

    @Override
    public void writeValue(Boolean val) throws ChargeControllerException {
        impl.writeValue( Arrays.asList(val) );
        this.value = val;
        this.commitTime = LocalDateTime.now();
    }

    @Override
    public BooleanField setChargeController(ChargeController cc) {
        super.setChargeController(cc);
        impl.setChargeController(cc);
        return this;
    }

}

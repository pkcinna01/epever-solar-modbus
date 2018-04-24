package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.SolarCharger;
import com.xmonit.solar.epever.EpeverException;
import com.xmonit.solar.epever.units.Unit;

import java.time.LocalDateTime;
import java.util.Arrays;

public class BooleanField extends EpeverField<Boolean> {

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
    public int getCount() {
        return impl.getCount();
    }

    @Override
    public Boolean readValue() throws EpeverException {
        this.value = impl.readValue().get(0);
        this.commitTime = LocalDateTime.now();
        return this.value;
    }

    public Boolean readValue(int offset, boolean[] values) throws EpeverException {
        this.value = impl.readValue(offset, values).get(0);
        this.commitTime = LocalDateTime.now();
        return this.value;
    }

    @Override
    public void writeValue(Boolean val) throws EpeverException {
        impl.writeValue( Arrays.asList(val) );
        this.value = val;
        this.commitTime = LocalDateTime.now();
    }

    @Override
    public BooleanField setSolarCharger(SolarCharger cc) {
        super.setSolarCharger(cc);
        impl.setSolarCharger(cc);
        return this;
    }

    @Override
    public double doubleValue() {
        return value == null ? Double.NaN : (value ? 1.0 : 0.0);
    }

}

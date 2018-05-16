package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.EpeverException;
import com.xmonit.solar.epever.EpeverParseException;
import com.xmonit.solar.epever.units.Unit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BooleanList extends EpeverField<List<Boolean>> {

    protected int bitCount = 1;

    public BooleanList(int addr, String name) {
        super(addr, Unit.Bool, name, "");
    }

    public BooleanList(int addr, String name, String description) {
        super(addr, Unit.Bool, name, description);
    }

    public BooleanList setCount(int cnt) {
        this.bitCount = cnt;
        return this;
    }

    @Override
    public int getCount() {
        return bitCount;
    }

    @Override
    public List<Boolean> parseValue(String strVal) throws EpeverParseException {
        throw new EpeverParseException("Boolean list parsing not yet supported.  Could not parse: " + strVal);
    }

    @Override
    public List<Boolean> readValue() throws EpeverException {
        boolean[] rtnVals;
        if ( isCoilBacked()) {
            rtnVals = solarCharger.readCoils(addr, bitCount);
        } else if ( isDiscreteInputBacked() ) {
            rtnVals = solarCharger.readDiscreteInputs(addr, bitCount);
        } else {
            throw new EpeverException("Invalid address when resolving register type: " + addr);
        }

        this.commitTime = LocalDateTime.now();
        this.value = IntStream.range(0,rtnVals.length).mapToObj(i->rtnVals[i]).collect(Collectors.toList());
        return this.value;
    }

    public List<Boolean> readValue(int offset, boolean[] values) throws EpeverException {
        this.value = IntStream.range(0,bitCount).mapToObj(i->values[offset+i]).collect(Collectors.toList());
        this.commitTime = LocalDateTime.now();
        return this.value;
    }

    @Override
    public void writeValue(List<Boolean> valList) throws EpeverException {
        boolean[] valArray = new boolean[valList.size()];
        for(int i=0; i< valArray.length; i++ ) {
            valArray[i] = valList.get(i);
        }
        solarCharger.writeCoils(addr,valArray);
        this.value = valList;
        this.commitTime = LocalDateTime.now();
    }

    @Override
    public double doubleValue() {
        return value == null ? Double.NaN : (value.stream().allMatch(v->true) ? 1.0 : 0.0);
    }
}

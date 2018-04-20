package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.ChargeControllerException;
import com.xmonit.solar.modbus.units.Unit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BooleanList extends ModbusField<List<Boolean>> {

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
    public List<Boolean> readValue() throws ChargeControllerException {
        boolean[] rtnVals;
        if ( isCoilBacked()) {
            rtnVals = chargeController.readCoils(addr, bitCount);
        } else if ( isDiscreteInputBacked() ) {
            rtnVals = chargeController.readDiscreteInputs(addr, bitCount);
        } else {
            throw new ChargeControllerException("Invalid address when resolving register type: " + addr);
        }

        this.commitTime = LocalDateTime.now();
        this.value = IntStream.range(0,rtnVals.length).mapToObj(i->rtnVals[i]).collect(Collectors.toList());
        return this.value;
    }

    public List<Boolean> readValue(int offset, boolean[] values) throws ChargeControllerException {
        this.value = IntStream.range(0,bitCount).mapToObj(i->values[offset+i]).collect(Collectors.toList());
        this.commitTime = LocalDateTime.now();
        return this.value;
    }

    @Override
    public void writeValue(List<Boolean> valList) throws ChargeControllerException {
        boolean[] valArray = new boolean[valList.size()];
        for(int i=0; i< valArray.length; i++ ) {
            valArray[i] = valList.get(i);
        }
        chargeController.writeCoils(addr,valArray);
        this.value = valList;
        this.commitTime = LocalDateTime.now();
    }
}

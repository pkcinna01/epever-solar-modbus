package com.xmonit.solar.modbus.field;

import com.xmonit.solar.modbus.ChargeControllerDependentTest;
import com.xmonit.solar.modbus.ChargeControllerException;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ModbusFieldListTest extends ChargeControllerDependentTest {

    @Test
    public void createInputRegisterBackedFields() {
    }

    @Test
    public void createHoldingRegisterBackedFields() {
    }

    @Test
    public void createBooleanBackedFields() {
    }

    /**
     *
     * 20 is optimum case for maxGap (anything higher will result in same batch groupings)
     *
     * The performance gain is about 45%.  The gains after maxGap=3 are not as pronounced.
     *
     * @throws ChargeControllerException
     */
    @Test
    public void readValues() throws ChargeControllerException {
        ModbusFieldList fieldList = new ModbusFieldList(chargeController);
        List<String> msgs = new LinkedList();
        int[] maxGaps = { 1, 1, 1, 3, 3, 3, 5, 5, 5, 20, 20, 20 };
        for ( int maxGap : maxGaps ) {
            long start = System.nanoTime();
            ModbusFieldList.readValues(chargeController,fieldList, maxGap);
            String msg = "Max gap: " + maxGap + " us: " + (System.nanoTime()-start)/1000;
            msgs.add(msg);
        }
        msgs.forEach(m->System.out.println(m));
    }
}
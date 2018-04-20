package com.xmonit.solar.modbus.field;


import com.xmonit.solar.modbus.ChargeController;
import com.xmonit.solar.modbus.ChargeControllerException;
import com.xmonit.solar.modbus.EpeverFieldDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class ModbusFieldList extends ArrayList<ModbusField> {

    public static ModbusFieldList createInputRegisterBackedFields(ChargeController cc) {
        return new ModbusFieldList(cc, (fieldDef) -> {
            return fieldDef.registerAddress >= 0x3000 && fieldDef.registerAddress < 0x9000;
        });
    }

    public static ModbusFieldList createHoldingRegisterBackedFields(ChargeController cc) {
        return new ModbusFieldList(cc, (fieldDef) -> {
            return fieldDef.registerAddress >= 0x9000;
        });
    }

    public static ModbusFieldList createBooleanBackedFields(ChargeController cc) {
        return new ModbusFieldList(cc, (fieldDef) -> {
            return fieldDef.registerAddress < 0x3000; // coils and discrete inputs
        });
    }

    /**
     * Group field addresses to minimize requests to charge controller.  Initial testing
     * suggests 45% faster if using max address gap of 20.  There is not a
     * big difference between 5 and 20 since there are lots of mandatory areas
     * that have to be skipped (see if statement below with ranges).
     *
     * @param chargeController
     * @param fields
     * @param maxAddressGap
     */
    public static void readValues(ChargeController chargeController, List<ModbusField> fields, int maxAddressGap)
            throws ChargeControllerException {
        List<ModbusField> sortedFields = new ArrayList(fields);
        sortedFields.sort((l, r) -> Integer.compare(l.addr, r.addr));
        List<List<ModbusField>> groupedFields = new ArrayList();
        int lastAddr = -1;
        for (ModbusField f : sortedFields) {
            if (lastAddr == -1
                    || f.addr - lastAddr > maxAddressGap
                    || lastAddr < 0x1000 && f.addr >= 0x1000
                    || lastAddr < 0x2000 && f.addr >= 0x2000
                    || lastAddr < 0x3000 && f.addr >= 0x3000
                    || lastAddr < 0x9000 && f.addr >= 0x9000
                    || lastAddr <= 0x2000 && f.addr > 0x2000
                    || lastAddr <= 0x300D && f.addr > 0x300D
                    || lastAddr <= 0x3106 && f.addr > 0x3106
                    || lastAddr <= 0x3112 && f.addr > 0x3112
                    || lastAddr <= 0x311B && f.addr > 0x311B
                    || lastAddr <= 0x3201 && f.addr > 0x3201
                    || lastAddr <= 0x331E && f.addr > 0x331E
                    || lastAddr <= 0x900E && f.addr > 0x900E
                    || lastAddr <= 0x9021 && f.addr > 0x9021
                    || lastAddr <= 0x903F && f.addr > 0x903F
                    || lastAddr <= 0x9042 && f.addr > 0x9042
                    || lastAddr <= 0x904B && f.addr > 0x904B
                    || lastAddr <= 0x9065 && f.addr > 0x9065
                    || lastAddr <= 0x9067 && f.addr > 0x9067
                    || lastAddr <= 0x9069 && f.addr > 0x9069) {
                groupedFields.add(new ArrayList());
            }
            groupedFields.get(groupedFields.size() - 1).add(f);
            lastAddr = f.addr;
        }

        for (List<ModbusField> group : groupedFields) {
            ModbusField first = group.get(0), last = group.get(group.size() - 1);

            if (first.isRegisterBacked()) {
                RegisterBackedField lastRegField = (RegisterBackedField) last;

                int registerCount = last.addr - first.addr + last.getCount();
                int[] registers = first.addr < 0x9000 ?
                        chargeController.readInputRegisters(first.addr, registerCount) :
                        chargeController.readHoldingRegisters(first.addr, registerCount);
                for (ModbusField field : group) {
                    RegisterBackedField inputField = (RegisterBackedField) field;
                    int offset = inputField.addr - first.addr;
                    inputField.readValue(offset, registers);
                }
            } else if (first.isBitBacked()) {
                int bitCount = last.addr - first.addr + last.getCount();
                boolean[] bools = first.addr < 0x2000 ?
                        chargeController.readCoils(first.addr, bitCount) :
                        chargeController.readDiscreteInputs(first.addr, bitCount);
                for (ModbusField field : group) {
                    BooleanField inputField = (BooleanField) field;
                    int offset = inputField.addr - first.addr;
                    inputField.readValue(offset, bools);
                }
            }
        }
        //System.out.print(""+maxAddressGap+": ");
        //groupedFields.forEach( g -> System.out.print("" + g.size() + ", " ));
        //System.out.println();
    }

    ChargeController chargeController;

    public ModbusFieldList(ChargeController cc) {
        this(cc, (fieldDef) -> {
            return true;
        });
    }

    public ModbusFieldList(ChargeController cc, Predicate<EpeverFieldDefinitions> filter) {
        chargeController = cc;
        for (EpeverFieldDefinitions def : EpeverFieldDefinitions.values()) {
            if (filter.test(def)) {
                add(def.create(chargeController));
            }
        }
    }

    public void readValues() throws ChargeControllerException {
        ModbusFieldList.readValues(chargeController, this, 20);
    }

}

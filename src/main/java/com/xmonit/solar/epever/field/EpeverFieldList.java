package com.xmonit.solar.epever.field;


import com.xmonit.solar.epever.SolarCharger;
import com.xmonit.solar.epever.EpeverException;
import com.xmonit.solar.epever.EpeverFieldDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class EpeverFieldList extends ArrayList<EpeverField> {

    public static EpeverFieldList masterFieldList = new EpeverFieldList(null);

    public static EpeverFieldList createInputRegisterBackedFields(SolarCharger cc) {
        return new EpeverFieldList(cc, (fieldDef) -> {
            return fieldDef.isInputRegisterBacked();
        });
    }

    public static EpeverFieldList createHoldingRegisterBackedFields(SolarCharger cc) {
        return new EpeverFieldList(cc, (fieldDef) -> {
            return fieldDef.isHoldingRegisterBacked();
        });
    }

    public static EpeverFieldList createBooleanBackedFields(SolarCharger cc) {
        return new EpeverFieldList(cc, (fieldDef) -> {
            return fieldDef.isBooleanBacked(); // coils and discrete inputs
        });
    }

    public static void readValues(SolarCharger solarCharger, List<EpeverField> fields)
            throws EpeverException {
        readValues(solarCharger,fields,20);
    }

    /**
     * Group field addresses to minimize requests to charge controller.  Initial testing
     * suggests 45% faster if using max address gap of 20.  There is not a
     * big difference between 5 and 20 since there are lots of mandatory areas
     * that have to be skipped (see if statement below with ranges).
     *
     * @param solarCharger
     * @param fields
     * @param maxAddressGap
     */
    public static void readValues(SolarCharger solarCharger, List<EpeverField> fields, int maxAddressGap)
            throws EpeverException {
        List<EpeverField> sortedFields = new ArrayList(fields);
        sortedFields.sort((l, r) -> Integer.compare(l.addr, r.addr));
        List<List<EpeverField>> groupedFields = new ArrayList();
        int lastAddr = -1;
        for (EpeverField f : sortedFields) {
            if (lastAddr == -1
                    || f.addr - lastAddr > maxAddressGap
                    || lastAddr < 0x1000 && f.addr >= 0x1000
                    || f.addr == 0x2000
                    || f.addr == 0x200C
                    || lastAddr < 0x3000 && f.addr >= 0x3000
                    || lastAddr < 0x9000 && f.addr >= 0x9000
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

        for (List<EpeverField> group : groupedFields) {
            EpeverField first = group.get(0), last = group.get(group.size() - 1);

            if (first.isRegisterBacked()) {
                RegisterBackedField lastRegField = (RegisterBackedField) last;

                int registerCount = last.addr - first.addr + last.getCount();
                int[] registers = first.addr < 0x9000 ?
                                  solarCharger.readInputRegisters(first.addr, registerCount) :
                                  solarCharger.readHoldingRegisters(first.addr, registerCount);
                for (EpeverField field : group) {
                    RegisterBackedField inputField = (RegisterBackedField) field;
                    int offset = inputField.addr - first.addr;
                    inputField.readValue(offset, registers);
                }
            } else if (first.isBitBacked()) {
                int bitCount = last.addr - first.addr + last.getCount();
                boolean[] bools = first.addr < 0x2000 ?
                                  solarCharger.readCoils(first.addr, bitCount) :
                                  solarCharger.readDiscreteInputs(first.addr, bitCount);
                for (EpeverField field : group) {
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

    SolarCharger solarCharger;

    public EpeverFieldList(SolarCharger cc) {
        this(cc, (fieldDef) -> {
            return true;
        });
    }

    public EpeverFieldList(SolarCharger cc, Predicate<EpeverFieldDefinitions> filter) {
        solarCharger = cc;
        for (EpeverFieldDefinitions def : EpeverFieldDefinitions.values()) {
            if (filter.test(def)) {
                add(def.create(solarCharger));
            }
        }
    }

    public void readValues() throws EpeverException {
        EpeverFieldList.readValues(solarCharger, this, 20);
    }

    public EpeverFieldList setSolarCharger(SolarCharger cc) {
        for(EpeverField field: this) {
            field.setSolarCharger(cc);
        }
        return this;
    }

}

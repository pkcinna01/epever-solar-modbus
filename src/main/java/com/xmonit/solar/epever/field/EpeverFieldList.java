package com.xmonit.solar.epever.field;


import com.xmonit.solar.epever.EpeverSolarCharger;
import com.xmonit.solar.epever.SolarCharger;
import com.xmonit.solar.epever.EpeverException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class EpeverFieldList implements Iterable<EpeverField> {

    public static EpeverFieldList masterFieldList = new EpeverFieldList(null).addFromDefs(f->true);


    public static EpeverFieldList createInputRegisterBackedFields(EpeverSolarCharger cc) {
        return new EpeverFieldList(cc).addFromDefs( fieldDef -> EpeverField.isInputRegisterBacked(fieldDef.registerAddress));
    }


    public static EpeverFieldList createHoldingRegisterBackedFields(EpeverSolarCharger cc) {
        return new EpeverFieldList(cc).addFromDefs( fieldDef -> EpeverField.isHoldingRegisterBacked(fieldDef.registerAddress));
    }


    public static EpeverFieldList createBooleanBackedFields(EpeverSolarCharger cc) {
        return new EpeverFieldList(cc).addFromDefs( fieldDef -> EpeverField.isBooleanBacked(fieldDef.registerAddress));
    }


    /**
     * Group field addresses to minimize requests to charge controller.  Initial testing
     * suggests 45% faster if using max address gap of 20.  There is not a
     * big difference between 5 and 20 since there are lots of mandatory areas
     * that have to be skipped (see if statement below with ranges).
     *
     * TBD - Issue with some fields getting wrong register.  Appears to be hardware specific
     *
     * @param solarCharger
     * @param fields
     * @param maxAddressGap
     */
    public synchronized static void readValues(SolarCharger solarCharger, List<EpeverField> fields, int maxAddressGap)
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
            } else if (first.isBooleanBacked()) {
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
    }


    EpeverSolarCharger solarCharger;
    List<EpeverField> listImpl;


    public EpeverFieldList(EpeverSolarCharger cc) {
        this( cc, new ArrayList() );
    }


    public EpeverFieldList(EpeverSolarCharger cc, List<EpeverField> list) {
        solarCharger = cc;
        listImpl = list;
    }


    public EpeverFieldList addFromDefs(Predicate<EpeverFieldDefinitions> filter) {
        for (EpeverFieldDefinitions def : EpeverFieldDefinitions.values()) {
            if (filter.test(def)) {
                listImpl.add(def.create(solarCharger));
            }
        }
        return this;
    }


    public EpeverFieldList addFromMaster(Predicate<EpeverField> filter) {
        masterFieldList.stream().filter(filter)
                .map( f-> EpeverField.createByAddr(solarCharger,f.addr) ).forEach(listImpl::add);
        return this;
    }


    public List<EpeverField> getListImpl() {
        return listImpl;
    }


    public synchronized void connectAndReadValues() throws Exception {
        solarCharger.withConnection( () -> readValues() );
    }


    public synchronized void readValues() throws EpeverException {
        for( EpeverField field: listImpl) {
            field.readValue();
        }
    }


    public synchronized void readValues(int maxGap) throws EpeverException {
        EpeverFieldList.readValues(solarCharger,getListImpl(),maxGap);
    }


    public EpeverFieldList setSolarCharger(final SolarCharger cc) {
        listImpl.forEach( field ->  field.setSolarCharger(cc) );
        return this;
    }

    public EpeverSolarCharger getSolarCharger() {
        return solarCharger;
    }


    public synchronized EpeverFieldList reset() {
        listImpl.forEach( field ->  field.reset() );
        return this;
    }


    public void copyValuesFrom(EpeverFieldList srcFields) {
        copyValuesFrom(srcFields.getListImpl());
    }

    public synchronized void copyValuesFrom(List<EpeverField> srcFields) {
        for( EpeverField field: listImpl ) {
            srcFields.stream().filter(srcField->srcField.addr == field.addr).findFirst().ifPresent(srcField->
               field.value = srcField.value
            );
        }
    }

    public EpeverField get(int index) { return listImpl.get(index); }

    public boolean isEmpty() {
        return listImpl.isEmpty();
    }

    public int size() {
        return listImpl.size();
    }

    public Stream<EpeverField> stream() {
        return listImpl.stream();
    }

    public Stream<EpeverField> filter(Predicate<EpeverField> fieldFilter) {
        return stream().filter(fieldFilter);
    }

    @Override
    public Iterator<EpeverField> iterator() {
        return listImpl.iterator();
    }
}

package com.xmonit.solar.modbus.field;


import com.xmonit.solar.modbus.ChargeController;
import com.xmonit.solar.modbus.EpeverChargeController;
import com.xmonit.solar.modbus.EpeverFieldDefinitions;

import java.util.ArrayList;
import java.util.function.Predicate;


public class ProxyFieldList extends ArrayList<ProxyField> {

    public static ProxyFieldList createInputRegisterBackedFields(ChargeController cc) {
        return new ProxyFieldList( cc, (fieldDef) -> {
            return fieldDef.registerAddress >= 0x3000 && fieldDef.registerAddress < 0x9000;
        });
    }

    public static ProxyFieldList createHoldingRegisterBackedFields(ChargeController cc) {
        return new ProxyFieldList( cc, (fieldDef) -> {
            return fieldDef.registerAddress >= 0x9000;
        });
    }

    public ProxyFieldList(ChargeController cc) {
        this( cc, (fieldDef) -> {return true;} );
    }

    public ProxyFieldList(ChargeController cc, Predicate<EpeverFieldDefinitions> filter) {
        for ( EpeverFieldDefinitions def : EpeverFieldDefinitions.values() ) {
            if ( filter.test(def) ) {
                add(def.create(cc));
            }
        }
    }
}

package com.xmonit.solar.modbus.field;


import com.xmonit.solar.modbus.ChargeController;
import com.xmonit.solar.modbus.EpeverFieldDefinitions;

import java.util.ArrayList;
import java.util.function.Predicate;


public class ModbusFieldList extends ArrayList<ModbusField> {

    public static ModbusFieldList createInputRegisterBackedFields(ChargeController cc) {
        return new ModbusFieldList( cc, (fieldDef) -> {
            return fieldDef.registerAddress >= 0x3000 && fieldDef.registerAddress < 0x9000;
        });
    }

    public static ModbusFieldList createHoldingRegisterBackedFields(ChargeController cc) {
        return new ModbusFieldList( cc, (fieldDef) -> {
            return fieldDef.registerAddress >= 0x9000;
        });
    }

    public static ModbusFieldList createBooleanBackedFields(ChargeController cc) {
        return new ModbusFieldList( cc, (fieldDef) -> {
            return fieldDef.registerAddress < 0x3000; // coils and discrete inputs
        });
    }

    public ModbusFieldList(ChargeController cc) {
        this( cc, (fieldDef) -> {return true;} );
    }

    public ModbusFieldList(ChargeController cc, Predicate<EpeverFieldDefinitions> filter) {
        for ( EpeverFieldDefinitions def : EpeverFieldDefinitions.values() ) {
            if ( filter.test(def) ) {
                add(def.create(cc));
            }
        }
    }
}

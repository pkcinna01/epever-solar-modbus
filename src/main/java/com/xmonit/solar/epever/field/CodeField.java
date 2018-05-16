package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.EpeverException;
import com.xmonit.solar.epever.EpeverParseException;
import com.xmonit.solar.epever.units.HexCodes;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import java.math.BigInteger;

/**
 * Also defines a text equivalent for each numeric value.
 * Used for representing multiple flags or values within a register.
 */
public class CodeField extends RegisterBackedField<BigInteger> {

    public CodeField(int addr, int registerCount, String description, HexCodes codes){

        super(addr,codes,codes.name,description,1,registerCount);
    }


    @Override
    public ObjectNode asJson(){
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode n = super.asJson();
        HexCodes hexCodes = (HexCodes) unit;
        n.put("values",hexCodes.asJson(getValue()));
        return n;
    }


    @Override
    public double doubleValue() {
        return value == null ? Double.NaN : value.doubleValue();
    }


    @Override
    public BigInteger fromRegisters(int offset, int[] registers) {

        return RegisterConversions.toBigInteger(offset, registerCount, registers, denominator);
    }


    @Override
    public int[] toRegisters(BigInteger val) {

        return RegisterConversions.fromBigInteger(val,denominator);
    }


    public ObjectNode valueAsJson(){
        ObjectNode n = super.valueAsJson();
        n.put("values",((HexCodes)unit).asJson(getValue()));
        return n;
    }

    // this only makes sense if field has only one bit range
    @Override
    public BigInteger parseValue(String codeName) throws EpeverParseException {
        HexCodes codes = (HexCodes) unit;
        try {
            int code = codeName.matches("^\\d+$") ? codes.findById(codeName) : codes.findByName(codeName);
            return BigInteger.valueOf(code);
        } catch ( Exception ex ) {
            throw new EpeverParseException("Could not convert text value into numeric code.", ex);
        }
    }
}

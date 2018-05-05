package com.xmonit.solar.epever.field;

import com.xmonit.solar.epever.units.HexCodes;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import java.math.BigInteger;


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
    public void writeValue(String codeName) throws Exception {
        HexCodes codes = (HexCodes) unit;
        int code = codes.findByName(codeName);
        writeValue(BigInteger.valueOf(code));
    }
}

package com.xmonit.solar.epever.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xmonit.solar.epever.EpeverException;
import com.xmonit.solar.epever.EpeverParseException;
import com.xmonit.solar.epever.SolarCharger;
import com.xmonit.solar.epever.units.Unit;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;

abstract public class EpeverField<T> {

    JsonNodeFactory factory = JsonNodeFactory.instance;

    public static EpeverField createByAddr(SolarCharger cc, int addr) {

        return Arrays.stream(EpeverFieldDefinitions.values()).filter(d->d.registerAddress == addr).findFirst().map(d->d.create(cc)).orElse(null);
    }

    public static boolean isBooleanBacked(int addr) { return isCoilBacked(addr) || isDiscreteInputBacked(addr); }
    public static boolean isCoilBacked(int addr) { return addr < 0x1000; }
    public static boolean isDiscreteInputBacked(int addr) { return addr >= 0x1000 && addr < 0x3000; }
    public static boolean isHoldingRegisterBacked(int addr) { return addr >= 0x9000; }
    public static boolean isInputRegisterBacked(int addr) { return addr >= 0x3000 && addr < 0x9000; }
    public static boolean isMetric(int addr) { return isInputRegisterBacked(addr)&&!isRating(addr); }
    public static boolean isStatistic(int addr) { return addr >= 0x3300 && addr <= 0x3314; }
    public static boolean isRating(int addr) { return addr >= 0x3000 && addr <= 0x300E; }

    public String name;
    public int addr;
    public Unit unit;

    protected String description;

    protected T value;
    protected LocalDateTime commitTime;
    protected SolarCharger solarCharger;

    public EpeverField(int addr, Unit unit, String name, String description) {
        this.addr = addr;
        this.unit = unit;
        this.name = name;
        this.description = description;
    }

    abstract public T readValue() throws EpeverException;

    abstract public void writeValue(T val) throws EpeverException;

    abstract int getCount();

    abstract public T parseValue(String strVal) throws EpeverParseException;

    public void writeValue(String value) throws EpeverException {
        T val = parseValue(value);
        writeValue(val);
    }
    /**
     * @return Double representation of getValue() used by monitoring systems
     */
    public abstract double doubleValue();


    public ObjectNode asJson(){
        ObjectNode n = factory.objectNode();
        n.set("name",factory.textNode(name));
        n.set("addr",factory.numberNode(addr));
        n.set("textValue", factory.textNode(toString()));
        n.set("value", factory.numberNode(doubleValue()));
        n.set("description", factory.textNode(getDescription()));
        n.set("unit", unit.asJson());
        return n;
    }


    public boolean isBooleanBacked() { return EpeverField.isBooleanBacked(addr); }
    public boolean isCoilBacked() { return EpeverField.isCoilBacked(addr); }
    public boolean isDiscreteInputBacked() { return EpeverField.isDiscreteInputBacked(addr); }
    public boolean isInputRegisterBacked() { return EpeverField.isInputRegisterBacked(addr); }
    public boolean isHoldingRegisterBacked() { return EpeverField.isHoldingRegisterBacked(addr); }
    public boolean isRating() { return EpeverField.isRating(addr); }
    public boolean isRegisterBacked() { return isInputRegisterBacked() || isHoldingRegisterBacked(); }


    public T getValue() {
        return value;
    }


    public String getCamelCaseName() {
        return StringUtils.uncapitalize(this.name.replaceAll("[ -]", ""));
    }


    public String getDescription() {
        return description;
    }


    @JsonProperty()
    public String getTextValue() {
        return toString();
    }


    public EpeverField setSolarCharger(SolarCharger cc) {
        this.solarCharger = cc;
        return this;
    }


    public void reset() {
        value = null;
        commitTime = null;
    }

    @Override
    public String toString() {
        return unit.asString(value);
    }


    public ObjectNode valueAsJson(){
        ObjectNode n = factory.objectNode();
        n.set("name",factory.textNode(name));
        n.set("addr",factory.numberNode(addr));
        n.set("textValue", factory.textNode(toString()));
        n.set("value", factory.numberNode(doubleValue()));
        return n;
    }

    public EpeverField<T> withChargerConnection( SolarCharger.ConnectionOp op ) throws Exception {
        this.solarCharger.withConnection(op);
        return this;
    }

}

package com.xmonit.solar.modbus;

public class HoldingRegister extends Register {

    public HoldingRegister(int addr, String name, String description, int size, int multiplier, RegisterUnit unit) {
        super(addr, name, description, size, multiplier, unit);
    }
}

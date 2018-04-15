package com.xmonit.solar.modbus;

public class CoilRegister extends Register {

    public CoilRegister(int addr, String name, String description, int size, int multiplier, RegisterUnit unit) {
        super(addr, name, description, size, multiplier, unit);
    }
}

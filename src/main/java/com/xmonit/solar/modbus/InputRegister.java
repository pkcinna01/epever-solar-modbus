package com.xmonit.solar.modbus;

public class InputRegister extends Register {

    public InputRegister(int addr, String name, String description, int size, int multiplier, RegisterUnit unit) {
        super(addr, name, description, size, multiplier, unit);
    }
}
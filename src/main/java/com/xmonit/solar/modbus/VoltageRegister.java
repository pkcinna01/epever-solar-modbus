package com.xmonit.solar.modbus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.xmonit.solar.modbus.RegisterUnit.Volts;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class VoltageRegister extends Register {

    public VoltageRegister(int addr, String name, String description, int size, int multiplier){

        super(addr, name, description,size,multiplier, Volts);
    }
    public VoltageRegister(int addr, String name, String description){

        this(addr, name, description,1,100);
    }
}

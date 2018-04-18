package com.xmonit.solar.modbus;

public class ChargeControllerException extends Exception {

    public ChargeControllerException(String msg, Exception ex) {
        super(msg,ex);
    }

    public ChargeControllerException(String msg) {
        super(msg);
    }
}

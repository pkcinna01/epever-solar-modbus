package com.xmonit.solar.epever;

public class EpeverException extends Exception {

    public EpeverException(String msg, Exception ex) {
        super(msg,ex);
    }

    public EpeverException(String msg) {
        super(msg);
    }
}

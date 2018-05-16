package com.xmonit.solar.epever;

public class EpeverParseException extends EpeverException {

    public EpeverParseException(String msg, Exception ex) {
        super(msg,ex);
    }

    public EpeverParseException(String msg) {
        super(msg);
    }
}

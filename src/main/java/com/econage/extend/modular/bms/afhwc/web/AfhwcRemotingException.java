package com.econage.extend.modular.bms.afhwc.web;

import com.econage.core.basic.exception.EconageWebException;
import org.springframework.http.HttpStatus;

public class AfhwcRemotingException extends EconageWebException {
    public AfhwcRemotingException(String err){
        super(err);
    }

    public AfhwcRemotingException(Throwable cause, String errorCode, Object... arguments) {
        super(cause, errorCode, arguments);
    }

    public AfhwcRemotingException httpStatus(HttpStatus httpStatus) {
        setHttpStatus(httpStatus);
        return this;
    }
}

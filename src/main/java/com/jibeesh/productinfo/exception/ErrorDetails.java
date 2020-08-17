package com.jibeesh.productinfo.exception;

import java.util.Date;

public class ErrorDetails {
    private String message;
    private Date timeStamp;
    private String errorDetails;

    public ErrorDetails(String message, Date timeStamp, String errorDetails) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.errorDetails = errorDetails;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getErrorDetails() {
        return errorDetails;
    }
}

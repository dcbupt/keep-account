package com.bupt.dc.object.processor;

import java.io.Serializable;

public class ProcessorResponse<Resp> implements Serializable {
    private static final long serialVersionUID = 4801091740659910528L;
    private boolean success = true;
    private Resp response;
    private String errCode;
    private String errMessage;

    public ProcessorResponse() {
    }

    public ProcessorResponse(Resp response) {
        this.response = response;
    }

    public ProcessorResponse(boolean success, String errCode, String errMessage) {
        this.success = success;
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public static <Resp> ProcessorResponse<Resp> successResult(Resp t) {
        return new ProcessorResponse(t);
    }

    public static <Resp> ProcessorResponse<Resp> failedResult(String errCode, String errMessage) {
        return new ProcessorResponse(false, errCode, errMessage);
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Resp getResponse() {
        return this.response;
    }

    public void setResponse(Resp response) {
        this.response = response;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return this.errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}

package com.bupt.dc.object.processor;

import java.io.Serializable;

public class ProcessorRequest<Req> implements Serializable {
    private static final long serialVersionUID = 3492845440671857171L;
    private String bizCode;
    private Req request;

    public ProcessorRequest() {
    }

    public ProcessorRequest(String bizCode, Req request) {
        this.bizCode = bizCode;
        this.request = request;
    }

    public static <Req> ProcessorRequest<Req> instance(String bizCode, Req request) {
        return new ProcessorRequest(bizCode, request);
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Req getRequest() {
        return this.request;
    }

    public void setRequest(Req request) {
        this.request = request;
    }
}

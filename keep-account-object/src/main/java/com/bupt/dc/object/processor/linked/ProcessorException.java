package com.bupt.dc.object.processor.linked;

public class ProcessorException extends RuntimeException {
    private static final long serialVersionUID = -1566769656450941962L;

    public ProcessorException() {
    }

    public ProcessorException(String message) {
        super(message);
    }

    public ProcessorException(Throwable cause) {
        super(cause);
    }

    public ProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}


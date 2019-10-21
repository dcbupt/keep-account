package com.bupt.dc.object.processor.linked;

import com.bupt.dc.object.processor.ProcessorRequest;

import java.util.Map;

public interface Processor<Req> {
    void invoke(ProcessorRequest<Req> var1, Map<String, Object> var2);
}

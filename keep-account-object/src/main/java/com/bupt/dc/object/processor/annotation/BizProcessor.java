package com.bupt.dc.object.processor.annotation;

import com.bupt.dc.object.processor.ProcessorRequest;
import com.bupt.dc.object.processor.ProcessorResponse;

public interface BizProcessor {

    ProcessorResponse process(ProcessorRequest request);

}

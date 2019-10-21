package com.bupt.dc.object.processor.linked;

import com.bupt.dc.object.processor.ProcessorRequest;
import com.bupt.dc.object.processor.ProcessorResponse;

public interface AbstractProcessorManager {

    <Resp, Req> ProcessorResponse<Resp> processor(ProcessorRequest<Req> var1);

}

package com.bupt.dc.object.processor.linked;

import com.bupt.dc.object.processor.ProcessorRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class BasicProcessor2 implements Processor<Void>{

    @Override
    public void invoke(ProcessorRequest<Void> var1, Map<String, Object> var2) {
        log.info("enter basicProcessor2");
    }
}

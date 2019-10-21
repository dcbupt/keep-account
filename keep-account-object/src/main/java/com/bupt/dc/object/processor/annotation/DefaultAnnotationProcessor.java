package com.bupt.dc.object.processor.annotation;

import com.bupt.dc.object.processor.ProcessorRequest;
import com.bupt.dc.object.processor.ProcessorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@BizProcessConfig(bizType = {BizTypeEnum.DEFAULT})
@Slf4j
public class DefaultAnnotationProcessor implements BizProcessor {
    @Override
    public ProcessorResponse process(ProcessorRequest request) {
        log.info("DefaultAnnotationProcessor|process");
        return null;
    }
}

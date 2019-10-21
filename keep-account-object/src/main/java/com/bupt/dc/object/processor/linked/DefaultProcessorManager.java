package com.bupt.dc.object.processor.linked;

import com.bupt.dc.object.processor.ProcessorRequest;
import com.bupt.dc.object.processor.ProcessorResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class DefaultProcessorManager implements AbstractProcessorManager {

    @Resource
    private Processor basicProcessor1;

    @Resource
    private Processor basicProcessor2;

    private Map<String, List<Processor>> processors = new TreeMap();

    @PostConstruct
    private void init() {
        this.buildProcessor("test", basicProcessor1).
             buildProcessor("test", basicProcessor2);
    }

    private DefaultProcessorManager buildProcessor(String bizCode, Processor processor) {
        if (CollectionUtils.isEmpty(processors.get(bizCode))) {
            List<Processor> processorList = new ArrayList<>();
            processorList.add(processor);
            processors.put(bizCode, processorList);
            return this;
        }
        processors.get(bizCode).add(processor);
        return this;
    }

    public DefaultProcessorManager() {
    }

    /**
     * 链式处理
     */
    @Override
    public <Resp, Req> ProcessorResponse<Resp> processor(ProcessorRequest<Req> processorRequest) {
        this.checkParamters(processorRequest);
        ProcessorResponse<Resp> processorResponse = new ProcessorResponse();
        List<Processor> processors = (List)this.processors.get(processorRequest.getBizCode());
        Map<String, Object> context = new HashMap();
        context.put(ProcessorResponse.class.getName(), processorResponse);
        Iterator var5 = processors.iterator();

        while(var5.hasNext()) {
            Processor processor = (Processor)var5.next();
            processor.invoke(processorRequest, context);
        }

        return processorResponse;
    }

    public void setProcessors(Map<String, List<Processor>> processors) {
        this.processors = processors;
    }

    public void addProcessors(String name, List<Processor> processorList) {
        if (name != null && processorList != null && !processorList.isEmpty()) {
            this.processors.put(name, processorList);
        }
    }

    private <Req> void checkParamters(ProcessorRequest<Req> processorRequest) {
        if (this.processors != null && !this.processors.isEmpty()) {
            if (processorRequest == null || processorRequest.getBizCode() == null || processorRequest.getRequest() == null) {
                throw new ProcessorException("业务逻辑配置错误");
            }
        } else {
            throw new ProcessorException("processor配置错误");
        }
    }
}

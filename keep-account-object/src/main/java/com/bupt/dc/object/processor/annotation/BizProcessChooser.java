package com.bupt.dc.object.processor.annotation;

import com.bupt.dc.object.util.SpringContextUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
public class BizProcessChooser {

    private static Map<String, List<BizProcessor>> bizProcessorMap = new HashMap<>();

    public static Map<String, List<BizProcessor>> getBizProcessorMap() {
        return bizProcessorMap;
    }

    static {
        /**
         * 实现了BizProcessor接口且加了BizProcessConfig注解的实现类，加入map
         */
        Optional.ofNullable(SpringContextUtil.getApplicationContext().getBeansOfType(BizProcessor.class).values()).
            ifPresent(bizProcessors -> {
                bizProcessors.stream().forEach(bizProcessor -> {
                    for (Annotation annotation : bizProcessor.getClass().getAnnotations()) {
                        if (annotation instanceof BizProcessConfig) {
                            BizProcessConfig bizProcessConfig = (BizProcessConfig) annotation;
                            for (BizTypeEnum bizTypeEnum : bizProcessConfig.bizType()) {
                                Optional.ofNullable(bizProcessorMap.get(bizTypeEnum.name())).orElse(
                                    bizProcessorMap.put(bizTypeEnum.name(), new ArrayList<>()));
                                bizProcessorMap.get(bizTypeEnum.name()).add(bizProcessor);
                            }
                        }
                    }
                });
            });
    }
}

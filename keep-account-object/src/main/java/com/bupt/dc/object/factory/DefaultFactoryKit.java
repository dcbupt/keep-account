package com.bupt.dc.object.factory;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultFactoryKit {

    @Resource
    private AbstractFactory defaultFactoryImplA;

    @Resource
    private AbstractFactory defaultFactoryImplB;

    private Map<String, AbstractFactory> factoryMap = new HashMap<>();

    private DefaultFactoryKit buildFactory(String s, AbstractFactory abstractFactory) {
        this.factoryMap.put(s, abstractFactory);
        return this;
    }

    @PostConstruct
    private void init() {
        this.buildFactory("implA", defaultFactoryImplA).
             buildFactory("implB", defaultFactoryImplB);
    }

    public AbstractFactory getFactory(String s) {
        return factoryMap.get(s);
    }

}

package com.bupt.dc.object.factory;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FactoryMaker {

    @Resource
    private AbstractFactory defaultFactoryBase;

    /**
     * 按业务类型返回对应的工厂类
     * @return
     */
    public AbstractFactory makeFactory() {
        return defaultFactoryBase;
    }
}

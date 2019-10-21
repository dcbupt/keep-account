package com.bupt.dc.object.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class DefaultFactoryBase implements AbstractFactory {

    @Resource
    private DefaultFactoryKit defaultFactoryKit;

    @Override
    public void createDefaultComponent() {
        log.info("DefaultFactoryBase|createDefaultComponent");
        AbstractFactory factoryA = defaultFactoryKit.getFactory("implA");
        factoryA.createDefaultComponent();
        AbstractFactory factoryB = defaultFactoryKit.getFactory("implB");
        factoryB.createDefaultComponent();
    }
}

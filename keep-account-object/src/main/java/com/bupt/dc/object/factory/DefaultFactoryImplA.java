package com.bupt.dc.object.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultFactoryImplA implements AbstractFactory {

    @Override
    public void createDefaultComponent() {
        log.info("DefaultFactoryImplA|createDefaultComponent");
    }
}

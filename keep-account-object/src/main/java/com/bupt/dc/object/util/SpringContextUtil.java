package com.bupt.dc.object.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang.Validate.notEmpty;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    private static Map<String, Object> cachedMap = new ConcurrentHashMap<String, Object>(700);

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(String beanName) {
        notEmpty(beanName, beanName + " bean name is required");

        checkState(applicationContext != null, "spring application context is not injected");

        if(cachedMap.containsKey(beanName)) {
            return (T) cachedMap.get(beanName);
        }
        assert applicationContext != null;
        T ret = (T) applicationContext.getBean(beanName);
        if(ret != null) {
            cachedMap.put(beanName, ret);
        }
        return ret;
    }

}

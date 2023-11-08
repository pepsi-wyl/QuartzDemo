package com.ylan.quartzdemo1.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author by pepsi-wyl
 * @date 2023-11-06 14:07
 * @Description Spring工具类
 */

@Component
public class SpringContextJobUtil implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SpringContextJobUtil.class);

    private static ApplicationContext applicationContext;

    @SuppressWarnings("static-access")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    public static ApplicationContext getContext() {
        if (applicationContext == null) {
            throw new RuntimeException("There has no Spring ApplicationContext!");
        }
        return applicationContext;
    }

    /**
     * 发布事件
     *
     * @param event 事件
     */
    public static void publishEvent(ApplicationEvent event) {
        if (applicationContext == null) {
            return;
        }
        try {
            applicationContext.publishEvent(event);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * 获取 Spring Bean
     *
     * @param clazz 类
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T getBean(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取 Spring Bean
     *
     * @param bean 名称
     * @param <T>  泛型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String bean) {
        if (bean == null) {
            return null;
        }
        return (T) applicationContext.getBean(bean);
    }

    /**
     * 获取 Spring Bean
     *
     * @param beanName 名称
     * @param clazz    类
     * @param <T>      泛型
     * @return 对象
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null == beanName || "".equals(beanName.trim())) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        return (T) applicationContext.getBean(beanName, clazz);
    }
}
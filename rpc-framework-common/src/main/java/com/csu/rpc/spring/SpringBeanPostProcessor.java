package com.csu.rpc.spring;

import com.csu.rpc.annotation.RpcReference;
import com.csu.rpc.annotation.RpcService;
import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.proxy.RpcClientProxy;
import com.csu.rpc.server.process.ServerProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Field;

/**
 * @Author Chen Yu
 * @Date 2021/4/13 21:26
 */
@Component
public class SpringBeanPostProcessor implements BeanPostProcessor {

    ServerProvider serverProvider = ServerProvider.INSTANCE;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {

            RpcService serviceAnnotation = bean.getClass().getAnnotation(RpcService.class);
            String group = serviceAnnotation.group();
            String version = serviceAnnotation.version();
            String serviceName = serviceAnnotation.serviceName();

            RpcServiceInfo serviceInfo = new RpcServiceInfo(serviceName, group, version);

            serverProvider.publishServer(bean, serviceInfo);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            RpcReference annotation = field.getAnnotation(RpcReference.class);

            if (annotation == null) {
                continue;
            }

            RpcServiceInfo serviceInfo = new RpcServiceInfo(field.getType().getSimpleName(),
                                                annotation.group(),
                                                annotation.version());

            RpcClientProxy clientProxy = new RpcClientProxy(serviceInfo);
            Object proxyObject = clientProxy.getProxy(field.getType());
            field.setAccessible(true);

            try {
                field.set(bean, proxyObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }
}

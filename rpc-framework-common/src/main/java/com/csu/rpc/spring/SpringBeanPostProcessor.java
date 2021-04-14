package com.csu.rpc.spring;

import com.csu.rpc.annotation.RpcService;
import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.server.process.ServerProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

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

            RpcServiceInfo serviceInfo = new RpcServiceInfo(group, version, serviceName);

            serverProvider.publishServer(bean, serviceInfo);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName);
        return bean;
    }
}

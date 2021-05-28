package com.csu.rpc.spring;

import com.csu.rpc.annotation.RpcReference;
import com.csu.rpc.annotation.RpcService;
import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.config.ServerRpcConfig;
import com.csu.rpc.proxy.RpcClientProxy;
import com.csu.rpc.server.process.ServerProvider;
import com.csu.rpc.server.process.processImpl.ServerProviderImpl;
import com.csu.rpc.utils.SingletonFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @Author Chen Yu
 * @Date 2021/4/13 21:26
 */
@Component
public class SpringBeanPostProcessor implements BeanPostProcessor {

    private volatile boolean serverConfigInit = true;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        /**
         * 处理服务
         * 自动将服务注册到服务器里面
         */
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {

            if (serverConfigInit) {
                initServerConfig();
            }

            ServerProvider serverProvider = SingletonFactory.getInstance(ServerProviderImpl.class);

            RpcService serviceAnnotation = bean.getClass().getAnnotation(RpcService.class);
            String group = serviceAnnotation.group();
            Byte version = serviceAnnotation.version();
            String serviceName = serviceAnnotation.serviceName();

            RpcServiceInfo serviceInfo = new RpcServiceInfo(serviceName, group, version);

            serverProvider.publishServer(bean, serviceInfo);
        }
        return bean;
    }

    /**
     * 一点小瑕疵
     * 智能先这样处理了
     */
    private void initServerConfig() {
        SingletonFactory.getInstance(ServerRpcConfig.class);
        serverConfigInit = false;
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

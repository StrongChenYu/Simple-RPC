package com.csu.rpc.spring;

import com.csu.rpc.annotation.RpcScan;
import com.csu.rpc.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * @Author Chen Yu
 * @Date 2021/4/19 20:50
 */
@Slf4j
public class RpcScannerRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;
    private static final String BASE_PACKET_FIELD_NAME = "basePackage";

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata classMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(classMetadata.getAnnotationAttributes(RpcScan.class.getName()));
        String[] basePackages = null;

        //读取rpcScan注解中定义的basePacket属性值
        if (attributes != null) {
            basePackages = attributes.getStringArray(BASE_PACKET_FIELD_NAME);
        }

        //如果没有读到，则以带有RpcScan的类所在的默认包名，作为扫描包的类
        if (basePackages == null || basePackages.length == 0) {
            basePackages = new String[]{((StandardAnnotationMetadata) classMetadata).getIntrospectedClass().getPackage().getName()};
        }

        /**
         * 到这里，扫描的包就在basePacket中了
         * 之后就根据basePacket将所有的service扫描进去
         */
        RpcServiceScanner rpcServiceScanner = new RpcServiceScanner(registry, RpcService.class);
        if (resourceLoader != null) {
            rpcServiceScanner.setResourceLoader(resourceLoader);
        }

        int scanService = rpcServiceScanner.scan(basePackages);
        log.info("Total scan {} bean in project", scanService);

    }

    public static void main(String[] args) {
        Package aPackage = RpcServiceScanner.class.getPackage();
        System.out.println(aPackage.getName());
    }
}

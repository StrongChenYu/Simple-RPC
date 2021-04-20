package com.csu.rpc.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * @Author Chen Yu
 * @Date 2021/4/19 20:56
 */
public class RpcServiceScanner extends ClassPathBeanDefinitionScanner {

    public RpcServiceScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> type) {
        super(registry);
        super.addIncludeFilter(new AnnotationTypeFilter(type));
    }

    @Override
    public int scan(String... basePackages) {
        return super.scan(basePackages);
    }
}

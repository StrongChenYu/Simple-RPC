package com.csu.rpc.annotation;

import com.csu.rpc.constant.RpcConstants;

import java.lang.annotation.*;

/**
 * @Author Chen Yu
 * @Date 2021/4/14 20:29
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RpcService {
    String version() default RpcConstants.DEFAULT_VERSION;
    String group() default RpcConstants.DEFAULT_GROUP;
    String serviceName() default "";
}

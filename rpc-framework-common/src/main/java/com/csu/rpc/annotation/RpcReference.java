package com.csu.rpc.annotation;

import com.csu.rpc.constant.RpcConstants;

import java.lang.annotation.*;

/**
 * @Author Chen Yu
 * @Date 2021/3/29 20:37
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RpcReference {
    byte version() default RpcConstants.DEFAULT_VERSION;
    String group() default RpcConstants.DEFAULT_GROUP;
}

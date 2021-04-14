package com.csu.rpc.annotation;

import java.lang.annotation.*;

/**
 * @Author Chen Yu
 * @Date 2021/4/14 20:29
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RpcService {
    String version() default "";
    String group() default "";
    String serviceName() default "";
}

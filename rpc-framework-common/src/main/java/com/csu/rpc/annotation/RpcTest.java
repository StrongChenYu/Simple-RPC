package com.csu.rpc.annotation;

import java.lang.annotation.*;

/**
 * @Author Chen Yu
 * @Date 2021/3/29 20:44
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RpcTest {
}

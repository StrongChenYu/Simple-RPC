package com.csu.rpc.annotation;

import com.csu.rpc.spring.RpcConfig;
import com.csu.rpc.spring.RpcScannerRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author Chen Yu
 * @Date 2021/4/19 20:52
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(RpcScannerRegister.class)
@Documented
public @interface RpcScan {

    String[] basePackage();
}

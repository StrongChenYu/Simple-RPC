package com.csu.rpc.scan;

import com.csu.rpc.annotation.RpcReference;
import com.csu.rpc.controller.HelloController;
import com.csu.rpc.proxy.RpcClientProxy;
import com.csu.rpc.service.HelloService;

import java.lang.reflect.Field;

/**
 * 自动扫描某个包下面的类
 * 然后将其自动配置进去
 * @Author Chen Yu
 * @Date 2021/3/30 20:18
 */
public class AutoScanConfig {

    RpcClientProxy proxy = new RpcClientProxy();

    public void scanAndAutoConfigProxy() {

        Field[] declaredFields = HelloController.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getAnnotation(RpcReference.class) == null) {
                continue;
            }

            field.setAccessible(true);
//
            HelloService helloService = this.proxy.getProxy(HelloService.class);
//           这里得new一个实体类的对象才可以将相应的属性赋值
//            field.set();
        }

    }

}

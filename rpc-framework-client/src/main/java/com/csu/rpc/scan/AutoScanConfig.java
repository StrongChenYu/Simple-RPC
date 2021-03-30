package com.csu.rpc.scan;

import com.csu.rpc.annotation.RpcReference;
import com.csu.rpc.controller.HelloController;

import java.lang.reflect.Field;

/**
 * 自动扫描某个包下面的类
 * 然后将其自动配置进去
 * @Author Chen Yu
 * @Date 2021/3/30 20:18
 */
public class AutoScanConfig {

    public void scanAndAutoConfigProxy() {

        Field[] declaredFields = HelloController.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getAnnotation(RpcReference.class) == null) {
                continue;
            }

            field.setAccessible(true);
//
//           这里得new一个实体类的对象才可以将相应的属性赋值
//            field.set();
        }

    }

}

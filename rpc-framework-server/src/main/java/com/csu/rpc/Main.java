package com.csu.rpc;

import com.csu.rpc.annotation.RpcScan;
import com.csu.rpc.spring.RpcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * @Author Chen Yu
 * @Date 2021/3/25 20:07
 */
@RpcScan(basePackage = {"com.csu.rpc"})
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        RpcConfig bean = context.getBean(RpcConfig.class);
        System.out.println(bean);

        System.out.println(bean.getClientConfig());
        System.out.println(bean.getServerConfig());
    }
}

package com.csu.rpc.spring;

import com.csu.rpc.config.RpcConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ConfigClassLoad implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        RpcConfig rpcConfig = RpcConfig.getRpcConfig();
        rpcConfig.afterConfigInit();
    }
}

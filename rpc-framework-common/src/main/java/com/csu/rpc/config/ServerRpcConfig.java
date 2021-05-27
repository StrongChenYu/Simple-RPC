package com.csu.rpc.config;

import com.csu.rpc.bean.ConfigBean;
import com.csu.rpc.bean.ServerConfigBean;
import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.discovery.DiscoveryContext;
import com.csu.rpc.discovery.ServerDiscovery;
import com.csu.rpc.registry.RegistryContext;
import com.csu.rpc.registry.ServiceRegistry;
import com.csu.rpc.utils.SingletonFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class ServerRpcConfig extends RpcConfig {
    ServerConfigBean serverConfigBean = new ServerConfigBean();

    public ServerRpcConfig() {
        loadConfig();
        RpcConfig.setRpcConfig(this);
    }

    @Override
    public ServerConfigBean getConfigBean() {
        return serverConfigBean;
    }

    @Override
    public String getConfigFileName() {
        return RpcConstants.SERVER_CONFIG;
    }

    @Override
    protected void validateConfig() {

    }

    @Override
    protected void classConfigCustom(ConfigBean configBean) {
        String registryAlgorithm = serverConfigBean.getRegisterCentral();
        ServiceRegistry serviceRegistry = SingletonFactory.getInstance(registryMap.get(registryAlgorithm));
        SingletonFactory.getInstance(RegistryContext.class).setServiceRegistry(serviceRegistry);
    }

}

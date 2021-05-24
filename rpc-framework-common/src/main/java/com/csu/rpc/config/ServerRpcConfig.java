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
    }

    @Override
    public ServerConfigBean getConfigBean() {
        return serverConfigBean;
    }

    @Override
    protected void customConfigRead(Properties configProperties) {
        URL serverUrl = this.getClass().getClassLoader().getResource(RpcConstants.SERVER_CONFIG);

        try {
            if (serverUrl != null) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(serverUrl.getFile()));
                configProperties.load(bufferedReader);
            }
        } catch (Exception e) {
            exit("custom config fill error!");
        }
    }

    @Override
    protected void configSet(Properties configProperties) {
        for (Map.Entry<Object, Object> entry : configProperties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            String fieldName = key.split("\\.")[2];

            configObject(serverConfigBean, fieldName, value);
        }
    }

    @Override
    protected void validateConfig() {

    }

    @Override
    protected void classConfig() {

        /**
         * 服务端配置注册中心
         */
        String registryAlgorithm = serverConfigBean.getRegisterCentral();
        ServiceRegistry serviceRegistry = SingletonFactory.getInstance(registryMap.get(registryAlgorithm));
        SingletonFactory.getInstance(RegistryContext.class).setServiceRegistry(serviceRegistry);

        classConfigCommon(serverConfigBean);
    }
}

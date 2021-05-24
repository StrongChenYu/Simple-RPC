package com.csu.rpc.config;

import com.csu.rpc.bean.ClientConfigBean;
import com.csu.rpc.bean.ConfigBean;
import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.discovery.DiscoveryContext;
import com.csu.rpc.discovery.ServerDiscovery;
import com.csu.rpc.discovery.loadbalance.LoadBalance;
import com.csu.rpc.discovery.loadbalance.LoadBalanceContext;
import com.csu.rpc.utils.SingletonFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class ClientRpcConfig extends RpcConfig {

    ClientConfigBean clientConfigBean = new ClientConfigBean();

    public ClientRpcConfig() {
        loadConfig();
    }

    @Override
    public ClientConfigBean getConfigBean() {
        return clientConfigBean;
    }

    @Override
    protected void customConfigRead(Properties configProperties) {
        URL clientUrl = this.getClass().getClassLoader().getResource(RpcConstants.CLIENT_CONFIG);

        try {
            if (clientUrl != null) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(clientUrl.getFile()));
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

            configObject(clientConfigBean, fieldName, value);
        }
    }


    @Override
    protected void validateConfig() {

    }

    @Override
    protected void classConfig() {

        /**
         * 客户端选择算法选择
         */
        String lookUpAlgorithm = clientConfigBean.getSelectAddressAlgorithm();
        LoadBalance loadBalance = SingletonFactory.getInstance(loadBalanceMap.get(lookUpAlgorithm));
        SingletonFactory.getInstance(LoadBalanceContext.class).setLoadBalance(loadBalance);

        /**
         * 客户端注册中心配置
         */
        String registerCentral = clientConfigBean.getRegisterCentral();
        ServerDiscovery serverDiscovery = SingletonFactory.getInstance(discoveryMap.get(registerCentral));
        SingletonFactory.getInstance(DiscoveryContext.class).setServerDiscovery(serverDiscovery);

        classConfigCommon(clientConfigBean);
    }
}

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
        RpcConfig.setRpcConfig(this);
    }

    public ClientConfigBean getConfigBean() {
        return clientConfigBean;
    }

    @Override
    public String getConfigFileName() {
        return RpcConstants.CLIENT_CONFIG;
    }

    @Override
    protected void validateConfig() {

    }


    /**
     * 这里看看将来能不能在封装的好一点
     * @param configBean
     */
    @Override
    protected void classConfigCustom(ConfigBean configBean) {

        /**
         * 客户端注册中心配置
         */
        String registerCentral = clientConfigBean.getRegisterCentral();
        ServerDiscovery serverDiscovery = SingletonFactory.getInstance(discoveryMap.get(registerCentral));
        SingletonFactory.getInstance(DiscoveryContext.class).setServerDiscovery(serverDiscovery);

        /**
         * 客户端选择算法选择
         */
        String lookUpAlgorithm = clientConfigBean.getSelectAddressAlgorithm();
        LoadBalance loadBalance = SingletonFactory.getInstance(loadBalanceMap.get(lookUpAlgorithm));
        SingletonFactory.getInstance(LoadBalanceContext.class).setLoadBalance(loadBalance);
    }

}

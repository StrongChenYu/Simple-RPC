package com.csu.rpc.discovery;

import com.csu.rpc.bean.RpcServiceInfo;
import com.csu.rpc.config.ClientRpcConfig;
import com.csu.rpc.config.ServerRpcConfig;
import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.discovery.loadbalance.LoadBalance;
import com.csu.rpc.discovery.loadbalance.LoadBalanceContext;
import com.csu.rpc.enums.LoadBalanceTypeEnum;
import com.csu.rpc.config.RpcConfig;
import com.csu.rpc.utils.SingletonFactory;
import com.csu.rpc.utils.ZookeeperUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author Chen Yu
 * @Date 2021/4/7 19:52
 */
@Slf4j
public class ZkServerDiscovery implements ServerDiscovery {

    private final ZookeeperUtil zkUtils = SingletonFactory.getInstance(ZookeeperUtil.class);
    private static final String SERVICE_PREFIX = RpcConstants.SERVICE_PREFIX;
    private final ClientRpcConfig rpcConfig = SingletonFactory.getInstance(ClientRpcConfig.class);

    private LoadBalance getLoadBalance() {
        return SingletonFactory.getInstance(LoadBalanceContext.class);
    }

    @Override
    public InetSocketAddress lookupServer(RpcServiceInfo serviceInfo) {

        /**
         * 获取拥有该服务的所有服务器
         */
        String zookeeperAddress = rpcConfig.getConfigBean().getZookeeperAddress();
        List<String> children = zkUtils.getChildren(zookeeperAddress, SERVICE_PREFIX + serviceInfo.toRegisterRpcServiceName());

        if (children == null || children.size() == 0) {
            log.error("Zookeeper don't have any information about service {}", serviceInfo.getServiceName());
            throw new RuntimeException("Zookeeper don't have any information about service");
        }

        LoadBalance loadBalance = getLoadBalance();
        String serverPath = loadBalance.selectServer(children, serviceInfo.getServiceName());

        //log
        log.info("Client choose server: {}", serverPath);
        String[] splits = serverPath.split(":");
        InetSocketAddress address = new InetSocketAddress(splits[0], Integer.parseInt(splits[1]));
        return address;
    }

}

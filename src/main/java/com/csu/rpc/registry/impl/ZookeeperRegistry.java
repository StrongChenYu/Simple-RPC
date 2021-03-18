package com.csu.rpc.registry.impl;

import com.csu.rpc.ZookeeperUtil;
import com.csu.rpc.registry.ServiceRegistry;
import com.csu.rpc.utils.SingletonFactory;

import java.net.InetSocketAddress;
/**
 * zookeeper注册中心API
 *
 * @Author Chen Yu
 * @Date 2021/3/17 19:58
 */
public class ZookeeperRegistry implements ServiceRegistry {

    private static final String ZOOKEEPER_ADDRESS = "127.0.0.1:2181";
    private static final String SPLIT_SYMBOL = ";";

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        ZookeeperUtil zkUtil = SingletonFactory.getInstance(ZookeeperUtil.class);

        /**
         * 格式
         * name:ip;ip;ip....
         *
         * 这里如果不存在就创建一个新的节点
         * 如果存在就在原有的内容后面加一个节点
         */
        String value = zkUtil.getValue(ZOOKEEPER_ADDRESS, rpcServiceName);
        if (value == null) {
            zkUtil.createPersistentNode(ZOOKEEPER_ADDRESS, rpcServiceName, inetSocketAddress.toString());
        } else {
            StringBuffer sb = new StringBuffer(value);
            sb.append(";");
            sb.append(rpcServiceName);
            zkUtil.updateNode(ZOOKEEPER_ADDRESS, rpcServiceName ,sb.toString());
        }
    }

    @Override
    public void unRegisterService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        ZookeeperUtil zkUtil = SingletonFactory.getInstance(ZookeeperUtil.class);

        /**
         * 删除节点，如果不存在就不用删除
         * 如果存在就删除掉
         */
        String value = zkUtil.getValue(ZOOKEEPER_ADDRESS, rpcServiceName);
        if (value == null) {
            return;
        }

        String[] split = value.split(SPLIT_SYMBOL);
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals(inetSocketAddress.toString())) {
                res.append(split[i]);
                res.append(";");
            }
        }
        zkUtil.updateNode(ZOOKEEPER_ADDRESS, rpcServiceName, res.toString());
    }
}

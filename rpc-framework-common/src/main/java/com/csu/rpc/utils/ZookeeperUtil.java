package com.csu.rpc.utils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author Chen Yu
 * @Date 2021/3/18 20:56
 */
@Slf4j
public class ZookeeperUtil {

    private final Map<String, List<String>> cache = new HashMap<>();
    
    public CuratorFramework getZkClient(String zkAddress) {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString(zkAddress)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(policy)
                .namespace("testRpc")
                .build();

        //只是start，并不会连接到连接
        zkClient.start();

        try {
            if (!zkClient.blockUntilConnected(5, TimeUnit.SECONDS)) {
                log.error("Can not connect to zookeeper!");
                throw new RuntimeException("Time out waiting to connect to ZK!");
            } else {
                log.info("Successfully connect to zookeeper");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zkClient;
    }
    /**
     * 增加操作
     * zookeeper操作的api，不涉及任何操作
     * @param path
     * @param value
     */
    public void createPersistentNode(String address, String path, String value) {

        /**
         * checkExist如果检查到不存在的内容
         * 会返回null
         *
         * 之所以要进行这步是因为对存在的节点进行set会抛出异常
         */
        try (CuratorFramework client = getZkClient(address)) {
            if (client.checkExists().forPath(path) != null) {
                //节点已经存在，这时候怎么办？
                log.warn("{} already exists", path);
                return;
            }
            //节点不存在
            //先将节点转化为字节数组
            byte[] bytesValue = value != null ? value.getBytes(StandardCharsets.UTF_8) : null;

            //创建节点
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(path, bytesValue);

            log.info("{} created successfully ! ", path);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新操作
     * 对不存在的节点进行update会抛异常
     * @param path
     * @param value
     */
    public void updateNode(String address, String path, String value) {

        try (CuratorFramework client = getZkClient(address)) {
            if (client.checkExists().forPath(path) == null) {
                //节点不存在
                log.warn("{} does not exist", path);
                return;
            }

            byte[] bytesValue = value != null ? value.getBytes(StandardCharsets.UTF_8) : null;
            client.setData().forPath(path, bytesValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查
     * 客户端使用的时候再写
     * @param path
     * @return
     */
    public String getValue(String address, String path) {
        //没有相应的key返回null
        try (CuratorFramework client = getZkClient(address)){
            if (client.checkExists().forPath(path) != null) {
                //节点存在
                byte[] bytes = client.getData().forPath(path);
                return new String(bytes, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取路径下所有的子路径
     * @param address
     * @param path
     * @return
     */
    public synchronized List<String> getChildren(String address, String path) {

        /**
         * 这里加一层缓存
         * 防止重复请求
         * 将结果缓存到客户端
         */
        if (cache.containsKey(path)) {
            return cache.get(path);
        }

        try (CuratorFramework client = getZkClient(address)){
            if (client.checkExists().forPath(path) != null) {
                //节点存在
                List<String> strings = client.getChildren().forPath(path);
                //创建缓存，防止防止重复访问zookeeper
                cache.put(path, strings);
                return strings;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteNode(String address, String path) {
        try (CuratorFramework client = getZkClient(address)){
            if (client.checkExists().forPath(path) == null) {
                //节点不存在
                log.warn("{} does not exist", path);
                return;
            }

            /**
             * 多线程运行时，这里可能判断后，然后path就被其他节点删除掉了
             * 所有这个地方会报错
             * 没办法，只能处理异常
             */
            client.delete().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

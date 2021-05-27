package com.csu.rpc.config;

import com.csu.rpc.bean.ClientConfigBean;
import com.csu.rpc.bean.ConfigBean;
import com.csu.rpc.bean.ServerConfigBean;
import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.discovery.DiscoveryContext;
import com.csu.rpc.discovery.RedisServerDiscovery;
import com.csu.rpc.discovery.ServerDiscovery;
import com.csu.rpc.discovery.ZkServerDiscovery;
import com.csu.rpc.discovery.loadbalance.LoadBalance;
import com.csu.rpc.discovery.loadbalance.LoadBalanceContext;
import com.csu.rpc.discovery.loadbalance.RandomLoadBalance;
import com.csu.rpc.dto.compress.Compress;
import com.csu.rpc.dto.compress.CompressContext;
import com.csu.rpc.dto.compress.impl.GzipCompress;
import com.csu.rpc.dto.serializer.Serializer;
import com.csu.rpc.dto.serializer.SerializerContext;
import com.csu.rpc.dto.serializer.impl.KryoSerializer;
import com.csu.rpc.enums.CompressTypeEnum;
import com.csu.rpc.enums.LoadBalanceTypeEnum;
import com.csu.rpc.enums.RegistryTypeEnum;
import com.csu.rpc.enums.SerializerTypeEnum;
import com.csu.rpc.registry.ServiceRegistry;
import com.csu.rpc.registry.impl.RedisRegistry;
import com.csu.rpc.registry.impl.ZookeeperRegistry;
import com.csu.rpc.utils.SingletonFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Getter
public abstract class RpcConfig {

    //这里感觉没有必要用并发map，因为没有put操作
    static final Map<String, Class<? extends ServerDiscovery>> discoveryMap = new HashMap<>();
    static final Map<String, Class<? extends LoadBalance>> loadBalanceMap = new HashMap<>();
    static final Map<String, Class<? extends Compress>> compressMap = new HashMap<>();
    static final Map<String, Class<? extends Serializer>> serializerMap = new HashMap<>();
    static final Map<String, Class<? extends ServiceRegistry>> registryMap = new HashMap<>();

    /**
     * 这里保存一份实例
     * 因为SingleFactory get不出
     * 到底是客户端还是服务端的实例
     */
    private static RpcConfig rpcConfig = null;

    /**
     * 这里用来记录到底是哪个客户端的配置
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig != null) {
            return rpcConfig;
        }
        throw new RuntimeException("config class should not be null!");
    }


    public static void setRpcConfig(RpcConfig rpcConfig) {
        RpcConfig.rpcConfig = rpcConfig;
    }


    static {

        //服务发现操作（客户端）
        discoveryMap.put(RegistryTypeEnum.ZOOKEEPER.getName(), ZkServerDiscovery.class);
        discoveryMap.put(RegistryTypeEnum.REDIS.getName(), RedisServerDiscovery.class);

        //客户端选择服务算法
        loadBalanceMap.put(LoadBalanceTypeEnum.RANDOM.getName(), RandomLoadBalance.class);

        //压缩算法
        compressMap.put(CompressTypeEnum.GZIP.getName(), GzipCompress.class);

        //编码解编码
        serializerMap.put(SerializerTypeEnum.KRYO.getName(), KryoSerializer.class);

        //注册中心配置操作（服务端）
        registryMap.put(RegistryTypeEnum.ZOOKEEPER.getName(), ZookeeperRegistry.class);
        registryMap.put(RegistryTypeEnum.REDIS.getName(), RedisRegistry.class);
    }

    public abstract ConfigBean getConfigBean();

    /**
     * 这里使用模板方法模式
     */
     void loadConfig() {

        Properties configProperties = new Properties();

        /**
         * 1. 加载默认配置
         * 2. 加载自定义配置
         */
        configRead(configProperties);


        /**
         * 3.
         * 加载完毕之后将配置文件中的内容注入到
         * serverConfig
         * clientConfig
         * 两个配置文件中
         */
        configSet(configProperties);

        /**
         * 4.
         * 检测配置文件中的内容正确性
         */
        validateConfig();


        /**
         * 5.
         * 根据将配置文件中的配置
         * 生成相应的策略类
         * 这里这样调用会出事
         */
        //classConfig();


        log.info("load config success!");
    }


    public void afterConfigInit() {
         classConfig();
    }


    private void configRead(Properties properties) {
        defaultConfigRead(properties);
        customConfigRead(properties);
    }

    /**
     * 读取自定义配置文件
     * proper
     * @throws IOException
     */
    private void customConfigRead(Properties configProperties) {
        String fileName = getConfigFileName();
        URL clientUrl = this.getClass().getClassLoader().getResource(fileName);

        try {
            if (clientUrl != null) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(clientUrl.getFile()));
                configProperties.load(bufferedReader);
            }

        } catch (Exception e) {
            exit("custom config fill error!");
        }
    }

    public abstract String getConfigFileName();

    /**
     * 将configProperties配置为到clientConfig和serverConfig里面
     * @param configProperties
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void configSet(Properties configProperties) {
        for (Map.Entry<Object, Object> entry : configProperties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            String fieldName = key.split("\\.")[2];

            ConfigBean configBean = getConfigBean();
            configObject(configBean, fieldName, value);
        }
    }


    void configObject(Object object, String fieldName, String value){
        Field declaredField = null;
        try {
            declaredField = object.getClass().getField(fieldName);

            Class<?> type = declaredField.getType();

            if (type == int.class || type == Integer.class) {
                declaredField.set(object, Integer.valueOf(value));
            } else if (type == String.class) {
                declaredField.set(object, value);
            } else {
                //......目前预计就这两种情况
                throw new RuntimeException("may be need more field config!");
            }

        } catch (Exception e) {
            /**
             * 这里的异常不做处理
             */
            log.warn("some error occur in config set to object!");
        }
    }


    /**
     * 验证config是否正确
     * @return
     */
    protected abstract void validateConfig();


    private void classConfig() {
        ConfigBean configBean = getConfigBean();
        classConfigCommon(configBean);
        classConfigCustom(configBean);
    }

    protected abstract void classConfigCustom(ConfigBean configBean);


    /**
     * 公共的配置
     *
     */
    void classConfigCommon(ConfigBean configBean) {
        classConfigCodecMode(configBean);
        classConfigCompressMode(configBean);
    }

    /**
     * 解压缩压缩配置
     * @param configBean
     */
    private void classConfigCompressMode(ConfigBean configBean) {
        String compressMode = configBean.getCompressMode();
        Compress compress = SingletonFactory.getInstance(compressMap.get(compressMode));
        SingletonFactory.getInstance(CompressContext.class).setCompress(compress);
    }

    /**
     * 编码解码方式配置
     * @param configBean
     */
    private void classConfigCodecMode(ConfigBean configBean) {
        String codecMode = configBean.getCodecMode();
        Serializer serializer = SingletonFactory.getInstance(serializerMap.get(codecMode));
        SingletonFactory.getInstance(SerializerContext.class).setSerializer(serializer);
    }

    /**
     * 读取默认的配置文件
     * @throws IOException
     */
    private void defaultConfigRead(Properties configProperties) {
        URL defaultConfig = this.getClass().getClassLoader().getResource(RpcConstants.DEFAULT_CONFIG);

        if (defaultConfig == null) {
            exit("default config file miss!");
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(defaultConfig.getFile()));
            configProperties.load(bufferedReader);
        } catch (IOException e) {
            exit("config file error!", e);
        }
    }

    void exit(String error) {
        log.error(error);
        System.exit(0);
    }

    void exit(String error, Exception e) {
        log.error(error);
        System.exit(0);
        e.printStackTrace();
    }


    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {

        ConfigBean serverConfigBean = new ServerConfigBean();
        Field declaredFields = serverConfigBean.getClass().getField("zookeeperAddress");
        declaredFields.set(serverConfigBean,"123");
        System.out.println(serverConfigBean);
//
//        Properties properties = new Properties();
//
//        InputStream in1 = rpcConfig.getClass().getClassLoader().getResourceAsStream("rpc.default.properties");
//        InputStream in2 = rpcConfig.getClass().getClassLoader().getResourceAsStream("test.properties");
//        // 使用properties对象加载输入流
//        properties.load(in1);
//        //properties.load(in2);
//        //获取key对应的value值
//        System.out.println(properties.getProperty("name"));
//        System.out.println(properties.getProperty("rpc.client.zookeeperAddress"));
//
//        Set<Object> objects = properties.keySet();
//        for(Map.Entry<Object, Object> entry : properties.entrySet()) {
//            System.out.println(entry.getKey().getClass());
//            System.out.println(entry.getValue().getClass());
//        }

//        RpcConfig config = new RpcConfig();
//        System.out.println(config.getClientConfig());
//        System.out.println(config.getServerConfig());

//        Object cast = type.cast("123");
//        System.out.println(cast);
//        System.out.println(maxRetry);
    }


}

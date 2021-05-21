package com.csu.rpc.config;

import com.csu.rpc.constant.RpcConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Getter
public class RpcConfig {

    public final static RpcConfig RPC_CONFIG = new RpcConfig();

    private CustomClientConfig clientConfig;
    private CustomServerConfig serverConfig;
    private final static String SERVER_PREFIX = "rpc.server";
    private final static String CLIENT_PREFIX = "rpc.client";

    private RpcConfig() {
        loadConfig();
    }

    private void loadConfig() {

        clientConfig = new CustomClientConfig();
        serverConfig = new CustomServerConfig();
        Properties configProperties = new Properties();

        /**
         * 加载默认配置和自定义的配置
         */
        try {
            defaultConfigRead(configProperties);
            customConfigRead(configProperties);
        } catch (IOException e) {
            log.error("config file error!");
            System.exit(0);
            e.printStackTrace();
        }


        /**
         * 加载完毕之后将配置文件中的内容注入到
         * serverConfig
         * clientConfig
         * 两个配置文件中
         */
        try {
            configSet(configProperties);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("config file read complete but error occur in config class!");
            System.exit(0);
            e.printStackTrace();
        }

        if (!validateConfig()) {
            log.error("config file format validate fail!");
            System.exit(0);
            throw new RuntimeException("config file read complete but error occur in config class!");
        }

        log.info("load config success!");
    }


    private boolean validateConfig() {
        /**
         * 这个方法用来验证配置文件到底有没有出错
         */
        return true;
    }

    /**
     * 将configProperties配置为到clientConfig和serverConfig里面
     * @param configProperties
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void configSet(Properties configProperties) throws NoSuchFieldException, IllegalAccessException {

        for (Map.Entry<Object, Object> entry : configProperties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            String fieldName = key.split("\\.")[2];

            if (key.startsWith(SERVER_PREFIX)) {
                configObject(serverConfig, fieldName, value);
            } else if (key.startsWith(CLIENT_PREFIX)) {
                configObject(clientConfig, fieldName, value);
            } else {
                //其他的在这里处理
            }
        }
    }

    private void configObject(Object object, String fieldName, String value) throws IllegalAccessException, NoSuchFieldException {

        Field declaredField = object.getClass().getDeclaredField(fieldName);
        Class<?> type = declaredField.getType();

        if (type == int.class || type == Integer.class) {
            declaredField.set(object, Integer.valueOf(value));
        } else if (type == String.class) {
            declaredField.set(object, value);
        } else {
            //......目前预计就这两种情况
            throw new RuntimeException("may be need more field config!");
        }
    }


    /**
     * 读取自定义配置文件
     * proper
     * @throws IOException
     */
    private void customConfigRead(Properties configProperties) throws IOException {
        URL clientUrl = this.getClass().getClassLoader().getResource(RpcConstants.CLIENT_CONFIG);
        URL serverUrl = this.getClass().getClassLoader().getResource(RpcConstants.SERVER_CONFIG);


        if (clientUrl != null) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(clientUrl.getFile()));
            configProperties.load(bufferedReader);
        }

        if (serverUrl != null) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(serverUrl.getFile()));
            configProperties.load(bufferedReader);
        }

    }

    /**
     * 读取默认的配置文件
     * @throws IOException
     */
    private void defaultConfigRead(Properties configProperties) throws IOException {
        URL defaultConfig = this.getClass().getClassLoader().getResource(RpcConstants.DEFAULT_CONFIG);

        if (defaultConfig == null) {
            log.error("default config file miss!");
            System.exit(0);
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(defaultConfig.getFile()));
        configProperties.load(bufferedReader);

    }




    public static void main(String[] args) throws IOException, NoSuchFieldException {
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

        RpcConfig config = new RpcConfig();
        System.out.println(config.getClientConfig());
        System.out.println(config.getServerConfig());

//        Object cast = type.cast("123");
//        System.out.println(cast);
//        System.out.println(maxRetry);
    }


}

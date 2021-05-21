package com.csu.rpc.constant;



public class RpcConstants {

    public static final byte[] MAGIC_NUMBER = {(byte) 'A', (byte) 'A',(byte) 'A',(byte) 'A'};
    public static final String CLIENT_CONFIG = "client.properties";
    public static final String SERVER_CONFIG = "server.properties";
    public static final String DEFAULT_CONFIG = "rpc.default.properties";
    public static final String SERVICE_PREFIX = "/rpc/";
    public static final Integer GZIP_COMPRESS_GZIP_SIZE = 1024 * 4;


    // TODO: 2021/5/21 未处理的配置值
    public static final byte VERSION = 1;
    public static final String DEFAULT_VERSION = "0.0.0";
    public static final String DEFAULT_GROUP = "group0";
}

package com.csu.rpc.constant;



public class RpcConstants {

    public static final byte[] MAGIC_NUMBER = {(byte) 'A', (byte) 'A',(byte) 'A',(byte) 'A'};
    public static final String IP = "127.0.0.1";

    public static final byte VERSION = 1;
    public static final String ZOOKEEPER_ADDRESS = "127.0.0.1:2181";
    public static final String SERVICE_PREFIX = "/rpc/";
    public static final Integer DEFAULT_PORT = 8000;
    public static final String DEFAULT_VERSION = "0.0.0";
    public static final String DEFAULT_GROUP = "group0";
    public static final Integer MAX_RETRY = 1;
    public static final Integer GZIP_COMPRESS_GZIP_SIZE = 1024 * 4;
}

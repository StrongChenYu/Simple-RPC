package com.csu.rpc.dto.protocol;

public class ProtocolStruct {
    /**
     * 魔数，用来标识是否属于协议
     */
    private int magicNumber;

    /**
     * 版本
     * 协议的版本
     */
    private byte version;

    /**
     * 数据包的长度
     * 用来表示数据包的长度
     */
    private int length;

    /**
     * 消息的类型
     * 标识类型为请求还是响应
     */
    private byte messageType;

    /**
     * 包会进行压缩传输，节省流量
     * 这个字段标识使用的解压缩算法
     */
    private byte compressType;

    /**
     * 使用的序列化算法
     */
    private byte serializeType;

    /**
     *
     */
    private int requestId;

    /**
     * 协议的主体
     */
    private byte[] body;
}

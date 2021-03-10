package com.csu.rpc.dto.protocol;


import io.netty.buffer.ByteBuf;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProtocolStruct {
    /**
     * 魔数，用来标识是否属于协议
     */
    private byte[] magicNumbers;

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

    public static ProtocolStruct decode(ByteBuf byteBuf) {
        ProtocolStruct protocolStruct = new ProtocolStruct();

        //读取魔数
        byte[] magic = new byte[4];
        byteBuf.readBytes(magic);
        protocolStruct.setMagicNumbers(magic);

        //读取版本
        protocolStruct.setVersion(byteBuf.readByte());

        //数据包的长度
        int length = byteBuf.readInt();
        protocolStruct.setLength(length);

        //数据包的类型
        protocolStruct.setMessageType(byteBuf.readByte());

        //压缩类型
        protocolStruct.setCompressType(byteBuf.readByte());

        //序列化算法
        protocolStruct.setSerializeType(byteBuf.readByte());

        //requestId
        protocolStruct.setRequestId(byteBuf.readInt());

        //读取对象的主体
        byte[] body = new byte[length];
        byteBuf.readBytes(body);
        protocolStruct.setBody(body);

        return protocolStruct;
    }
}

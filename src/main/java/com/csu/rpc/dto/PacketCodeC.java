package com.csu.rpc.dto;

import com.csu.rpc.dto.compress.Compress;
import com.csu.rpc.dto.protocol.ProtocolStruct;
import com.csu.rpc.serializer.Serializer;
import com.csu.rpc.utils.ConstantUtils;
import io.netty.buffer.ByteBuf;

public class PacketCodeC {

    public static final byte[] MAGIC_NUMBER = {(byte)'A', (byte)'A', (byte)'A', (byte) 'A'};

    public Packet decode(ByteBuf byteBuf) {

        ProtocolStruct decodeStruct = ProtocolStruct.transformToProtocol(byteBuf);

        //获取序列化协议
        byte serializeType = decodeStruct.getSerializeType();

        //获取压缩算法
        byte compressType = decodeStruct.getCompressType();

        //获取包的类型
        byte messageType = decodeStruct.getMessageType();

        //数据字节
        byte[] body = decodeStruct.getBody();

        /**
         * 1. 根据压缩算法解压缩
         * 2. 根据序列化协议和包的类型反序列化
         */
        Compress compress = ConstantUtils.getCompressType(compressType);

        //1.
        byte[] decompressBody = compress.decompress(body);

        //2.
        Class<?> clazz = ConstantUtils.getMessageType(messageType);
        Serializer serializer = ConstantUtils.getSerializeAlgorithm(serializeType);

        Object deserialize = serializer.deserialize(decompressBody, clazz);
        if (deserialize instanceof Packet) return (Packet) deserialize;

        return null;
    }

    public void encode(ByteBuf byteBuf, Packet packet) {

        ProtocolStruct protocolStruct = ProtocolStruct.transformToProtocol(packet);

        byteBuf.writeBytes(protocolStruct.getMagicNumbers());
        byteBuf.writeByte(protocolStruct.getVersion());
        byteBuf.writeInt(protocolStruct.getLength());
        byteBuf.writeByte(protocolStruct.getMessageType());
        byteBuf.writeByte(protocolStruct.getCompressType());
        byteBuf.writeByte(protocolStruct.getSerializeType());
        byteBuf.writeInt(protocolStruct.getRequestId());
        byteBuf.writeBytes(protocolStruct.getBody());
    }

}

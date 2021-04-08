package com.csu.rpc.dto;

import com.csu.rpc.dto.compress.Compress;
import com.csu.rpc.dto.serializer.Serializer;
import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.enums.CompressTypeEnum;
import com.csu.rpc.enums.PacketTypeEnum;
import com.csu.rpc.enums.SerializerTypeEnum;
import com.csu.rpc.utils.SingletonFactory;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class PacketCodeC {

    public static PacketCodeC PACKETCODEC = new PacketCodeC();

    private final SerializerTypeEnum serializerType = SerializerTypeEnum.KRYO;
    private final CompressTypeEnum compressType = CompressTypeEnum.GZIP;

    private Class<? extends Packet> getPacketType(Byte type) {
        return PacketTypeEnum.getPacketClassByCode(type);
    }

    private Serializer getSerializerAlgorithm(Byte type) {
        Class<? extends Serializer> serializerClass = SerializerTypeEnum.getSerializerClassByCode(type);

        if (serializerClass == null) {
            throw new RuntimeException("SerializerClass can't be null!");
        }

        return SingletonFactory.getInstance(serializerClass);
    }

    private Compress getCompressAlgorithm(Byte type) {
        Class<? extends Compress> compressClass = CompressTypeEnum.getCompressClassByCode(type);

        if (compressClass == null) {
            throw new RuntimeException("CompressClass can't be null!");
        }

        return SingletonFactory.getInstance(compressClass);
    }

    public Packet decode(ByteBuf byteBuf) {

        //读取魔数
        byte[] magic = new byte[4];
        byteBuf.readBytes(magic);

        //读取版本
        byteBuf.readByte();

        //数据包的长度
        int length = byteBuf.readInt();

        //数据包的类型
        Byte packetType = byteBuf.readByte();

        //压缩类型
        Byte compressType = byteBuf.readByte();

        //序列化算法
        Byte serializerType = byteBuf.readByte();

        //requestId,这个暂时还不知道有什么用
        byteBuf.readInt();

        //读取对象的主体
        byte[] body = new byte[length];
        byteBuf.readBytes(body);


        /**
         * 1. 根据压缩算法解压缩
         * 2. 根据序列化协议和包的类型反序列化
         */
        Compress compress = getCompressAlgorithm(compressType);

        //1.
        byte[] decompressBody = compress.decompress(body);

        //2.
        Class<?> clazz = getPacketType(packetType);
        Serializer serializer = getSerializerAlgorithm(serializerType);

        Object deserialize = serializer.deserialize(decompressBody, clazz);

        if (deserialize instanceof Packet) {
            return (Packet) deserialize;
        }

        return null;
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        Compress compress = getCompressAlgorithm(serializerType.getCode());
        Serializer serializer = getSerializerAlgorithm(serializerType.getCode());

        byte[] compressBytes = compress.compress(serializer.serialize(packet));

        byteBuf.writeBytes(RpcConstants.MAGIC_NUMBER);
        byteBuf.writeByte(RpcConstants.VERSION);
        byteBuf.writeInt(compressBytes.length);
        byteBuf.writeByte(packet.serializerType());
        byteBuf.writeByte(compressType.getCode());
        byteBuf.writeByte(serializerType.getCode());

        //返回的时候，不需要请求的ID,所以随便赋值一个
        byteBuf.writeInt(1);
        byteBuf.writeBytes(compressBytes);
    }

}

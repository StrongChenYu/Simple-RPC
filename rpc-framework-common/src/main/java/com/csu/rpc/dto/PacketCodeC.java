package com.csu.rpc.dto;

import com.csu.rpc.config.RpcConfig;
import com.csu.rpc.dto.compress.Compress;
import com.csu.rpc.dto.compress.CompressContext;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.serializer.Serializer;
import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.dto.serializer.SerializerContext;
import com.csu.rpc.enums.CompressTypeEnum;
import com.csu.rpc.enums.PacketTypeEnum;
import com.csu.rpc.enums.SerializerTypeEnum;
import com.csu.rpc.utils.SingletonFactory;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 这里要get到到底是客户端的配置还是服务端的配置
 * 想到的一个解决方案只能是
 * 把SingleFactory中的每一个bean都拿出来试一试
 * 然后找到继承了RpcConfig中的bean
 * 看是RpcClientConfig还是RpcServerConfig
 *
 */
@AllArgsConstructor
@Slf4j
public class PacketCodeC {

    public static PacketCodeC PACKETCODEC = SingletonFactory.getInstance(PacketCodeC.class);

    private final SerializerTypeEnum serializerType = getSerializerType();
    private final CompressTypeEnum compressType = getCompressType();

    private Class<? extends Packet> getPacketType(Byte type) {
        return PacketTypeEnum.getPacketClassByCode(type);
    }

    private Serializer getSerializerAlgorithm(Byte type) {
        //这里做个校验吧
        if (!serializerType.getCode().equals(type)) {
            log.info("code mode in config file is not equals with actual packet");
            throw new RuntimeException("code mode in config file is not equals with actual packet");
        }

        return SingletonFactory.getInstance(SerializerContext.class);
    }

    private Compress getCompressAlgorithm(Byte type) {
        //这里做校验
        if (!compressType.getCode().equals(type)) {
            log.info("compress type in config file is not equals with actual packet");
            throw new RuntimeException("compress type in config file is not equals with actual packet");
        }

        return SingletonFactory.getInstance(CompressContext.class);
    }

    //根据config中的内容获取序列化算法
    private SerializerTypeEnum getSerializerType() {
        RpcConfig rpcConfig = RpcConfig.getRpcConfig();
        String codecMode = rpcConfig.getConfigBean().getCodecMode();
        return SerializerTypeEnum.getSerializerClassByName(codecMode);
    }

    //根据config中的内容获取压缩解压缩算法
    private CompressTypeEnum getCompressType() {
        RpcConfig rpcConfig = RpcConfig.getRpcConfig();
        String compressMode = rpcConfig.getConfigBean().getCompressMode();
        return CompressTypeEnum.getCompressClassByName(compressMode);
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
        byteBuf.writeByte(RpcConstants.DEFAULT_VERSION);
        byteBuf.writeInt(compressBytes.length);
        byteBuf.writeByte(packet.serializerType());
        byteBuf.writeByte(compressType.getCode());
        byteBuf.writeByte(serializerType.getCode());

        //返回的时候，不需要请求的ID,所以随便赋值一个
        byteBuf.writeInt(1);
        byteBuf.writeBytes(compressBytes);

    }

}

package com.csu.rpc.dto;

import com.csu.rpc.dto.compress.Compress;
import com.csu.rpc.dto.compress.CompressAlgorithm;
import com.csu.rpc.dto.compress.GzipCompress;
import com.csu.rpc.dto.request.RpcRequest;
import com.csu.rpc.dto.response.RpcResponse;
import com.csu.rpc.dto.serializer.KryoSerializer;
import com.csu.rpc.dto.serializer.Serializer;
import com.csu.rpc.dto.serializer.SerializerAlgorithm;
import com.csu.rpc.utils.RpcConstants;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacketCodeC {

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;
    private static final Map<Byte, Compress> compressAlgorithmMap;

    private static final byte DEFAULT_SERIALIZER_ALGORITHM = 1;
    private static final byte DEFAULT_COMPRESS_ALGORITHM = 1;

    public static List<Class<?>> getAllClasses() {
        return new ArrayList<>(packetTypeMap.values());
    }

    static {
        packetTypeMap = new ConcurrentHashMap<>();
        serializerMap = new ConcurrentHashMap<>();
        compressAlgorithmMap = new ConcurrentHashMap<>();

        initPacketTypeMap();
        initSerializerMap();
        initCompressAlgorithmMap();
    }

    /**
     * 以后再改把
     * 把这几个map中的东西都放进去，防止麻烦
     */
    private static void initPacketTypeMap() {
        packetTypeMap.put(Command.RPC_RESPONSE_PACKET, RpcResponse.class);
        packetTypeMap.put(Command.RPC_REQUEST_PACKET, RpcRequest.class);
    }
    private static void initSerializerMap() {
        serializerMap.put(SerializerAlgorithm.KRYO_SERIALIZER_ALGORITHM, new KryoSerializer());
    }
    private static void initCompressAlgorithmMap() {
        compressAlgorithmMap.put(CompressAlgorithm.GZIP_Algorithm, new GzipCompress());
    }

    private static Class<? extends Packet> getPacketType(Byte type) {
        return packetTypeMap.get(type);
    }
    private static Serializer getSerializerAlgorithm(Byte type) {
        return serializerMap.get(type);
    }
    private static Compress getCompressAlgorithm(Byte type) {
        return compressAlgorithmMap.get(type);
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
        Compress compress = getCompressAlgorithm(DEFAULT_COMPRESS_ALGORITHM);
        Serializer serializer = getSerializerAlgorithm(DEFAULT_SERIALIZER_ALGORITHM);

        byte[] compressBytes = compress.compress(serializer.serialize(packet));

        byteBuf.writeBytes(RpcConstants.MAGIC_NUMBER);
        byteBuf.writeByte(RpcConstants.VERSION);
        byteBuf.writeInt(compressBytes.length);
        byteBuf.writeByte(packet.serializerType());
        byteBuf.writeByte(DEFAULT_COMPRESS_ALGORITHM);
        byteBuf.writeByte(DEFAULT_SERIALIZER_ALGORITHM);

        //这个值不知道是啥
        byteBuf.writeInt(1);
        byteBuf.writeBytes(compressBytes);
    }

}

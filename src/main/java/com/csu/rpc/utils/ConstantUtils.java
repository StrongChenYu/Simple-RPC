package com.csu.rpc.utils;

import com.csu.rpc.dto.compress.Compress;
import com.csu.rpc.dto.compress.GzipCompress;
import com.csu.rpc.serializer.KryoSerializer;
import com.csu.rpc.serializer.Serializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConstantUtils {
    private static final Map<Byte, Compress> compressMap;
    private static final Map<Byte, Class<?>> classMap;
    private static final Map<Byte, Serializer> serializerMap;

    public static void setClass(Byte id, Class<?> clazz) {
        classMap.put(id, clazz);
    }

    static {
        compressMap = new ConcurrentHashMap<>();
        classMap = new ConcurrentHashMap<>();
        serializerMap = new ConcurrentHashMap<>();


        compressMap.put(RpcConstants.GZIP_COMPRESSOR, new GzipCompress());
        serializerMap.put(RpcConstants.KRYO_SERIALIZER, new KryoSerializer());
    }

    public static Compress getCompressType(byte compressType) {
        return compressMap.get(compressType);
    }

    public static Class<?> getMessageType(byte messageType) {
        return classMap.get(messageType);
    }

    public static Serializer getSerializeAlgorithm(byte serializeType) {
        return serializerMap.get(serializeType);
    }
}

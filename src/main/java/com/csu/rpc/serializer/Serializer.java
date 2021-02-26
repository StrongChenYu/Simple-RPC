package com.csu.rpc.serializer;

import com.csu.rpc.serializer.Exception.SerializeException;

public interface Serializer {

    byte[] serialize(Object object);
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}

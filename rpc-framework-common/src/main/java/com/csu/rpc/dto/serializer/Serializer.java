package com.csu.rpc.dto.serializer;


public interface Serializer {


    byte[] serialize(Object object);
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}

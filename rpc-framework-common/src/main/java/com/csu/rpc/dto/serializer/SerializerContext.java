package com.csu.rpc.dto.serializer;

public class SerializerContext implements Serializer{

    Serializer serializer;

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public byte[] serialize(Object object) {
        return serializer.serialize(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return serializer.deserialize(bytes, clazz);
    }
}

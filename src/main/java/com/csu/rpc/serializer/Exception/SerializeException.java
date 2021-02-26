package com.csu.rpc.serializer.Exception;

public class SerializeException extends RuntimeException {
    public SerializeException(String serialization_failed) {
        super(serialization_failed);
    }
}

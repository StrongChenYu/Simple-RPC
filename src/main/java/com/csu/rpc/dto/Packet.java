package com.csu.rpc.dto;

public abstract class Packet {

    private Byte version = 1;

    public abstract byte getMessageType();

    public abstract byte getCompressType();

    public abstract byte getSerializerType();

    public abstract byte getRequestId();
}

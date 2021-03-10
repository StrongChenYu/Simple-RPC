package com.csu.rpc.dto;

public abstract class Packet {

    private Byte version = 1;

    public abstract Byte getCommand();
}

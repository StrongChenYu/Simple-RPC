package com.csu.rpc.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class TestPacket extends Packet {

    private String id;

    @Override
    public byte getMessageType() {
        return 1;
    }

    @Override
    public byte getCompressType() {
        return 1;
    }

    @Override
    public byte getSerializerType() {
        return 1;
    }

    @Override
    public byte getRequestId() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestPacket that = (TestPacket) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

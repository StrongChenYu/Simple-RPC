package com.csu.rpc.protocol;

import com.csu.rpc.dto.Packet;
import com.csu.rpc.dto.PacketCodeC;
import com.csu.rpc.dto.request.RpcRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

public class PacketCodeCTest {

    @Test
    public void Test() {

        /**
         * 主要比较code后在decode出来能不能得到一样的包
         */
        RpcRequest packet = new RpcRequest();
        packet.setInterfaceName("interface");
        packet.setMethodName("method");
        PacketCodeC packetCodeC = new PacketCodeC();

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        packetCodeC.encode(byteBuf, packet);
        Packet decode = packetCodeC.decode(byteBuf);

        Assert.assertTrue(decode instanceof RpcRequest);

        RpcRequest testPacket = (RpcRequest) decode;
        Assert.assertEquals(testPacket, packet);
    }
}

package com.csu.rpc.protocol;

import com.csu.rpc.dto.Packet;
import com.csu.rpc.dto.PacketCodeC;
import com.csu.rpc.dto.TestPacket;
import com.csu.rpc.utils.ConstantUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PacketCodeCTest {

    @Before
    public void before() {
        ConstantUtils.setClass((byte)1, TestPacket.class);
    }

    @Test
    public void Test() {

        /**
         * 主要比较code后在decode出来能不能得到一样的包
         */

        TestPacket packet = new TestPacket();
        packet.setId("chenyu");
        PacketCodeC packetCodeC = new PacketCodeC();

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        packetCodeC.encode(byteBuf, packet);

        Packet decode = packetCodeC.decode(byteBuf);

        Assert.assertTrue(decode instanceof TestPacket);

        TestPacket testPacket = (TestPacket) decode;
        Assert.assertEquals(testPacket, packet);
    }

}

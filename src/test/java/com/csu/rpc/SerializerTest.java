package com.csu.rpc;

import com.csu.rpc.serializer.KryoSerializer;
import com.csu.rpc.dto.request.RpcRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SerializerTest {

//    @Test
//    public void serializeTest() throws SerializeException {
//        KryoSerializer serializer = KryoSerializer.INSTANCE;
//        RpcRequest rpcRequest = RpcRequest.builder()
//                .interfaceName("test")
//                .methodName("hello").build();
//
//        //System.out.println(rpcRequest);
//        byte[] binary = serializer.serialize(rpcRequest);
//        RpcRequest deserializeRequest = serializer.deserialize(binary, RpcRequest.class);
//
//        System.out.println(deserializeRequest);
//        Assert.assertEquals(deserializeRequest, rpcRequest);
//    }
//
//    public void helpTest(String interfaceName, String methodName) throws SerializeException {
//        KryoSerializer serializer = KryoSerializer.INSTANCE;
//        RpcRequest rpcRequest = RpcRequest.builder()
//                .interfaceName(interfaceName)
//                .methodName(methodName).build();
//
//        //System.out.println(rpcRequest);
//        byte[] binary = serializer.serialize(rpcRequest);
//        RpcRequest deserializeRequest = serializer.deserialize(binary, RpcRequest.class);
//
//        System.out.println(deserializeRequest);
//        Assert.assertEquals(deserializeRequest, rpcRequest);
//    }
//
//    @Test
//    public void multiThreadTest() throws InterruptedException {
//        List<Thread> allThread = new ArrayList<>();
//        int threadNum = 10;
//
//        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
//        for (int i = 0; i < threadNum; i++) {
//            allThread.add(new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        helpTest("interface" + Thread.currentThread(), "methodName" + Thread.currentThread());
//                        countDownLatch.countDown();
//                    } catch (SerializeException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }));
//        }
//
//        for (Thread thread : allThread) {
//            thread.start();
//        }
//
//        countDownLatch.await();
//
//        //Thread.currentThread().join();
//    }

}



package com.csu.rpc.proxy.jdk;

public class SmsServiceImpl{

    public void send(String message) {
        System.out.println("sendMessage：" + message);
    }
}

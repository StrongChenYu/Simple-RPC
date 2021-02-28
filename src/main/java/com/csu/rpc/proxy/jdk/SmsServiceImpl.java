package com.csu.rpc.proxy.jdk;

public class SmsServiceImpl implements SmsService{
    @Override
    public void send(String message) {
        System.out.println("sendMessage：" + message);
    }
}

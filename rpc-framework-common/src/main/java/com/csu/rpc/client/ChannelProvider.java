package com.csu.rpc.client;


import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelProvider {

    private final Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public Channel getChannel(InetSocketAddress address) {
        String key = address.toString();

        if(channelMap.containsKey(key)) {
            Channel channel = channelMap.get(key);

            if (channel != null && channel.isActive()) {
                return channel;
            } else {
                channelMap.remove(key);
            }
        }

        return null;
    }

    public void putChannel(InetSocketAddress address, Channel channel) {
        String key = address.toString();
        channelMap.put(key, channel);
    }
}

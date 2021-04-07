package com.csu.rpc.registry.impl;

import com.csu.rpc.registry.AbstractLoadBalance;

import java.util.List;
import java.util.Random;

/**
 * @Author Chen Yu
 * @Date 2021/4/7 19:41
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    @Override
    protected String doSelect(List<String> serverList, String serviceName) {
        /**
         * nextInt(upBound)
         * [0,upBound)
         */
        Random random = new Random();
        return serverList.get(random.nextInt(serverList.size()));
    }

}

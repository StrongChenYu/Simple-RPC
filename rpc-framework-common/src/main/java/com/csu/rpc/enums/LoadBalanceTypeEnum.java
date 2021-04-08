package com.csu.rpc.enums;

import com.csu.rpc.dto.compress.Compress;
import com.csu.rpc.dto.compress.impl.GzipCompress;
import com.csu.rpc.registry.LoadBalance;
import com.csu.rpc.registry.impl.RandomLoadBalance;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Chen Yu
 * @Date 2021/4/8 20:49
 */
@AllArgsConstructor
@Getter
public enum LoadBalanceTypeEnum {

    RANDOM((byte)1, "randomBalance", RandomLoadBalance.class);

    private Byte code;
    private String name;
    private Class<? extends LoadBalance> clazz;

}

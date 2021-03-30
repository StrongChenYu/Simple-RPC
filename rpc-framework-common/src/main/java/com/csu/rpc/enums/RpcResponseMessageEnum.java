package com.csu.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author Chen Yu
 * @Date 2021/3/30 21:43
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcResponseMessageEnum {

    SUCCESS(200, "Remote procedure call success!"),
    SERVICE_NOT_FIND(300, "Remote Service not find in this server!"),
    INVOKE_FAIL(400, "Some unexpected error occur in method call!");

    private final int code;
    private final String message;
}

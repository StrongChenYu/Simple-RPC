package com.csu.rpc.bean;

import lombok.*;

/**
 * @Author Chen Yu
 * @Date 2021/3/23 19:37
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInfo {
    String name;
    Object serverImpl;
    Class<?> interFace;
}

package com.csu.rpc.dto.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
public class RpcResponse {
    private String message;
}

package com.csu.rpc.enums;

import com.csu.rpc.dto.compress.Compress;
import com.csu.rpc.dto.compress.impl.GzipCompress;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Chen Yu
 * @Date 2021/4/8 19:34
 */
@Getter
@AllArgsConstructor
public enum CompressTypeEnum {

    GZIP((byte)1, "gzip", GzipCompress.class);

    private Byte code;
    private String name;
    private Class<? extends Compress> clazz;

    public static Class<? extends Compress> getCompressClassByCode(Byte code) {
        for (CompressTypeEnum typeEnum : CompressTypeEnum.values()) {
            if (typeEnum.getCode() == code) {
                return typeEnum.getClazz();
            }
        }
        return null;
    }
}

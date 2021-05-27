package com.csu.rpc.enums;

import com.csu.rpc.dto.serializer.Serializer;
import com.csu.rpc.dto.serializer.impl.KryoSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Chen Yu
 * @Date 2021/4/8 19:36
 */
@Getter
@AllArgsConstructor
public enum SerializerTypeEnum {

    KRYO((byte)1, "kryo", KryoSerializer.class);

    private Byte code;
    private String name;
    private Class<? extends Serializer> clazz;

    public static Class<? extends Serializer> getSerializerClassByCode(Byte code) {
        for (SerializerTypeEnum typeEnum : SerializerTypeEnum.values()) {
            if (typeEnum.getCode() == code) {
                return typeEnum.getClazz();
            }
        }
        return null;
    }

    public static SerializerTypeEnum getSerializerClassByName(String name) {
        name = name.toLowerCase();
        for (SerializerTypeEnum typeEnum : SerializerTypeEnum.values()) {
            if (typeEnum.getName().equals(name)) {
                return typeEnum;
            }
        }
        return null;
    }
}

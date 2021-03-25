package com.csu.rpc.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SingletonFactory {
    private static final Map<String, Object> OBJECT_MAP = new HashMap<>();

    public SingletonFactory() {
    }

    public SingletonFactory(int string) {

    }

    public static <T> T getInstance(Class<T> clazz) {
        String key = clazz.toString();
        Object instance;
        synchronized (SingletonFactory.class) {
            instance = OBJECT_MAP.get(key);
            if (instance == null) {
                try {
                    //使用instance的无参构造器，然后用这个构造器构造一个实例
                    //System.out.println("创建对象：" + clazz.getName());
                    instance = clazz.getDeclaredConstructor().newInstance();
                    OBJECT_MAP.put(key, instance);

                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        return clazz.cast(instance);
    }

    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println(SingletonFactory.class.getDeclaredConstructor(int.class));
    }
}

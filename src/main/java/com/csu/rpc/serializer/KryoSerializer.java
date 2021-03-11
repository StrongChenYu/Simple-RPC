package com.csu.rpc.serializer;

import com.csu.rpc.dto.TestPacket;
import com.csu.rpc.dto.request.RpcRequest;

import com.csu.rpc.dto.response.RpcResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoSerializer implements Serializer {

    public static final KryoSerializer INSTANCE = new KryoSerializer();

    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcRequest.class);
        kryo.register(RpcResponse.class);
        kryo.register(TestPacket.class);
        return kryo;
    });

    @Override
    public byte[] serialize(Object object) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {

            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output, object);
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)){

            Kryo kryo = kryoThreadLocal.get();
            Object object = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();

            return clazz.cast(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        byte[] array = new byte[5];
//        array[0] = 'A';
//        array[1] = 'B';
//        array[2] = 'C';
//        array[3] = 'D';
//        array[4] = 'E';
//        ByteArrayInputStream stream = new ByteArrayInputStream(array);
//        System.out.println(stream.read());
//        System.out.println(stream.read());
//        System.out.println(stream.read());
//    }
}

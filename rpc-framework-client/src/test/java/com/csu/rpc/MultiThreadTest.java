package com.csu.rpc;

import com.csu.rpc.controller.HelloController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CountDownLatch;

public class MultiThreadTest {

    private static final Integer numThread = 2000;

    @Test
    public void testMultiThread() throws InterruptedException {

        Thread[] threads = new Thread[numThread];
        CountDownLatch countDownLatch = new CountDownLatch(numThread);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientMain.class);

        for (int i = 0; i < numThread; i++) {
            threads[i] = new Thread(() -> {
                HelloController controller = context.getBean(HelloController.class);
                Assert.assertEquals(1, controller.test());
                countDownLatch.countDown();
            });
        }
        for (int i = 0; i < numThread; i++) {
            threads[i].start();
        }

        countDownLatch.await();
    }
}

package com.csu.rpc.protocol;


import com.csu.rpc.dto.compress.Compress;
import com.csu.rpc.dto.compress.GzipCompress;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class CompressTest {

    @Test
    public void compressTest() {
        Compress compress = new GzipCompress();
        String[] testString = {"abc", "46465465464f46a4e6f4a64f6a", "a", "chen"};

        for (String s : testString) {
            byte[] bytes = s.getBytes(StandardCharsets.US_ASCII);
            byte[] compressArray = compress.compress(bytes);

            byte[] decompressArray = compress.decompress(compressArray);
            String finalString = new String(decompressArray, 0 , decompressArray.length, StandardCharsets.US_ASCII);

            Assert.assertEquals(s, finalString);
        }
    }

    private void printBytesArray(byte[] bytes) {
        for (byte b : bytes) {
            System.out.print(b);
            System.out.print(" ");
        }
        System.out.println();
    }

}

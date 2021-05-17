package com.csu.rpc.dto.compress.impl;

import com.csu.rpc.constant.RpcConstants;
import com.csu.rpc.dto.compress.Compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipCompress implements Compress {

    private static final Integer BUFFER_SIZE = RpcConstants.GZIP_COMPRESS_GZIP_SIZE;

    @Override
    public byte[] compress(byte[] bytes) {
        if (bytes == null) return null;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipInputStream = new GZIPOutputStream(out)){
            gzipInputStream.write(bytes);
            gzipInputStream.flush();
            gzipInputStream.finish();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPInputStream unGzip = new GZIPInputStream(new ByteArrayInputStream(bytes))){
            byte[] buffer = new byte[BUFFER_SIZE];
            int n;
            while ((n = unGzip.read(buffer)) > -1) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

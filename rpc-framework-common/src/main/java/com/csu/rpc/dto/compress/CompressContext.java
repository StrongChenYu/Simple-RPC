package com.csu.rpc.dto.compress;


public class CompressContext implements Compress{

    private Compress compress;

    public void setCompress(Compress compress) {
        this.compress = compress;
    }

    @Override
    public byte[] compress(byte[] bytes) {
        return compress.compress(bytes);
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return compress.decompress(bytes);
    }
}

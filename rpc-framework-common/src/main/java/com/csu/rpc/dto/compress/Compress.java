package com.csu.rpc.dto.compress;

public interface Compress {

    byte[] compress(byte[] bytes);
    byte[] decompress(byte[] bytes);
}


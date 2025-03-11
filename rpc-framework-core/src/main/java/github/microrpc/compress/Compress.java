package github.microrpc.compress;

import github.microrpc.extension.SPI;

/**
 * 压缩接口
 */
@SPI
public interface Compress {

    byte[] compress(byte[] bytes);


    byte[] decompress(byte[] bytes);
}

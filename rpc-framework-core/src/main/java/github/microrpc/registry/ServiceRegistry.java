package github.microrpc.registry;

import github.microrpc.extension.SPI;

import java.net.InetSocketAddress;

/**
 * 服务注册接口
 */
@SPI
public interface ServiceRegistry {
    /**
     * register service
     *
     * @param rpcServiceName    rpc service name
     * @param inetSocketAddress service address
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);

}

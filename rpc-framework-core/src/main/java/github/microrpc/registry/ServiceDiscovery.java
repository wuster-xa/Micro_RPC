package github.microrpc.registry;

import github.microrpc.extension.SPI;
import github.microrpc.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * 服务发现接口
 */
@SPI
public interface ServiceDiscovery {
    /**
     * lookup service by rpcServiceName
     *
     * @param rpcRequest rpc service pojo
     * @return service address
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}

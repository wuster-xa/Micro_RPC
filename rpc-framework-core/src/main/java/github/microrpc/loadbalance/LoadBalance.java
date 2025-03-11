package github.microrpc.loadbalance;

import github.microrpc.extension.SPI;
import github.microrpc.remoting.dto.RpcRequest;

import java.util.List;

/**
 * 负载均衡接口
 */
@SPI
public interface LoadBalance {
    /**
     * Choose one from the list of existing service addresses list
     *
     * @param serviceUrlList Service address list
     * @param rpcRequest
     * @return target service address
     */
    String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest);
}

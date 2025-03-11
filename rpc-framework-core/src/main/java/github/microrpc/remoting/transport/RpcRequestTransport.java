package github.microrpc.remoting.transport;

import github.microrpc.extension.SPI;
import github.microrpc.remoting.dto.RpcRequest;

/**
 * 传输RPC请求的接口
 */
@SPI
public interface RpcRequestTransport {
    /**
     * send rpc request to server and get result
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}

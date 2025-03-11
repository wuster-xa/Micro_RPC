package github.microrpc.proxy;

import github.microrpc.config.RpcServiceConfig;
import github.microrpc.enums.RpcErrorMessageEnum;
import github.microrpc.enums.RpcResponseCodeEnum;
import github.microrpc.exception.RpcException;
import github.microrpc.remoting.dto.RpcRequest;
import github.microrpc.remoting.dto.RpcResponse;
import github.microrpc.remoting.transport.RpcRequestTransport;
import github.microrpc.remoting.transport.netty.client.NettyRpcClient;
import github.microrpc.remoting.transport.socket.SocketRpcClient;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * RPC客户端动态代理类
 */
//TODO 添加CGLib动态代理
@Slf4j
@AllArgsConstructor
public class RpcClientProxy implements InvocationHandler {

    private static final String INTERFACE_NAME = "interfaceName";

    /**
     * Used to send requests to the server.And there are two implementations: socket and netty
     */
    private final RpcRequestTransport rpcRequestTransport;
    private final RpcServiceConfig rpcServiceConfig;

    public RpcClientProxy(RpcRequestTransport rpcRequestTransport) {
        this.rpcRequestTransport = rpcRequestTransport;
        this.rpcServiceConfig = new RpcServiceConfig();
    }

    /**
     * get the proxy object
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    /**
     * This method is actually called when you use a proxy object to call a method.
     * The proxy object is the object you get through the getProxy method.
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.info("invoked method: [{}]", method.getName());
        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .group(rpcServiceConfig.getGroup())
                .version(rpcServiceConfig.getVersion())
                .build();
        RpcResponse<Object> rpcResponse = null;
        if (rpcRequestTransport instanceof NettyRpcClient) {
            CompletableFuture<RpcResponse<Object>> completableFuture = (CompletableFuture<RpcResponse<Object>>) rpcRequestTransport.sendRpcRequest(rpcRequest);
            rpcResponse = completableFuture.get();
        }
        if (rpcRequestTransport instanceof SocketRpcClient) {
            rpcResponse = (RpcResponse<Object>) rpcRequestTransport.sendRpcRequest(rpcRequest);
        }
        this.check(rpcResponse, rpcRequest);
        return rpcResponse.getData();
    }

    private void check(RpcResponse<Object> rpcResponse, RpcRequest rpcRequest) {
        if (rpcResponse == null) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RpcException(RpcErrorMessageEnum.REQUEST_NOT_MATCH_RESPONSE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (rpcResponse.getCode() == null || !rpcResponse.getCode().equals(RpcResponseCodeEnum.SUCCESS.getCode())) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
    }
}

package github.microrpc.remoting.transport.socket;

import github.microrpc.enums.ServiceDiscoveryEnum;
import github.microrpc.exception.RpcException;
import github.microrpc.extension.ExtensionLoader;
import github.microrpc.registry.ServiceDiscovery;
import github.microrpc.remoting.dto.RpcRequest;
import github.microrpc.remoting.transport.RpcRequestTransport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Socket方式实现的RPC客户端
 */
@AllArgsConstructor
@Slf4j
@Component
public class SocketRpcClient implements RpcRequestTransport {
    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension(ServiceDiscoveryEnum.ZK.getName());
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest);
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // Send data to the server through the output stream
            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // Read RpcResponse from the input stream
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RpcException("调用服务失败:", e);
        }
    }
}

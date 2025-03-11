package github.microrpc.autoconfigure;

import github.microrpc.config.RpcServiceConfig;
import github.microrpc.proxy.RpcClientProxy;
import github.microrpc.remoting.transport.RpcRequestTransport;
import github.microrpc.remoting.transport.netty.client.NettyRpcClient;
import github.microrpc.remoting.transport.netty.server.NettyRpcServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RpcProperties.class)
@Slf4j
public class RpcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public NettyRpcServer nettyRpcServer(RpcProperties properties) {
        return new NettyRpcServer(properties.getServicePort());
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcRequestTransport rpcClient() {
        return new NettyRpcClient();
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcServiceConfig rpcServiceConfig(RpcProperties properties) {
        return RpcServiceConfig.builder()
                .group(properties.getGroup())
                .version(properties.getVersion())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcClientProxy rpcClientProxy(RpcRequestTransport rpcClient, RpcServiceConfig serviceConfig) {
        return new RpcClientProxy(rpcClient, serviceConfig);
    }
} 

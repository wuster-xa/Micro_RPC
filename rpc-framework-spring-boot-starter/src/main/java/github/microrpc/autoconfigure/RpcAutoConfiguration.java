package github.microrpc.autoconfigure;

import github.microrpc.config.RpcServiceConfig;
import github.microrpc.proxy.RpcClientProxy;
import github.microrpc.registry.ServiceDiscovery;
import github.microrpc.registry.ServiceRegistry;
import github.microrpc.registry.zk.ZkServiceDiscoveryImpl;
import github.microrpc.registry.zk.ZkServiceRegistryImpl;
import github.microrpc.provider.ServiceProvider;
import github.microrpc.provider.impl.ZkServiceProviderImpl;
import github.microrpc.remoting.transport.RpcRequestTransport;
import github.microrpc.remoting.transport.netty.client.NettyRpcClient;
import github.microrpc.remoting.transport.netty.server.NettyRpcServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RPC自动配置类
 */
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
    @ConditionalOnMissingBean(ServiceProvider.class)
    public ServiceProvider serviceProvider() {
        return new ZkServiceProviderImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ServiceRegistry.class)
    public ServiceRegistry serviceRegistry() {
        return new ZkServiceRegistryImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ServiceDiscovery.class)
    public ServiceDiscovery serviceDiscovery() {
        return new ZkServiceDiscoveryImpl();
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

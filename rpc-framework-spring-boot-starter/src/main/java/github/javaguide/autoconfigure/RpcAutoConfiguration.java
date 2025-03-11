/**
 * RPC自动配置类
 */
@Configuration
@EnableConfigurationProperties(RpcProperties.class)
public class RpcAutoConfiguration {

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
    @ConditionalOnMissingBean(RpcRequestTransport.class)
    public RpcRequestTransport rpcRequestTransport() {
        return new NettyRpcClient();
    }

    @Bean
    @ConditionalOnMissingBean(RpcClientProxy.class)
    public RpcClientProxy rpcClientProxy(RpcRequestTransport rpcRequestTransport) {
        return new RpcClientProxy(rpcRequestTransport);
    }
} 
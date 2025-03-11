/**
 * RPC配置属性类
 */
@ConfigurationProperties(prefix = "rpc")
@Data
public class RpcProperties {
    private int port;
    private String registryAddr;
} 
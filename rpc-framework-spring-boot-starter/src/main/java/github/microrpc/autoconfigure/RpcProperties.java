package github.microrpc.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rpc")
public class RpcProperties {
    /**
     * 服务注册中心地址
     */
    private String registryAddress = "127.0.0.1:2181";

    /**
     * 服务暴露端口
     */
    private int servicePort = 9000;

    /**
     * 序列化类
     */
    private String serializationType = "kryo";

    /**
     * 压缩类型
     */
    private String compressType = "gzip";

    /**
     * 负载均衡策略
     */
    private String loadBalance = "random";

    /**
     * 服务调用超时时间(毫秒)
     */
    private long timeout = 5000;

    /**
     * 服务提供者组
     */
    private String group = "";

    /**
     * 服务版本�?
     */
    private String version = "";
} 

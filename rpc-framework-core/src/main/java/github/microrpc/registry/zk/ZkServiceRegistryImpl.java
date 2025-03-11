package github.microrpc.registry.zk;

import github.microrpc.registry.ServiceRegistry;
import github.microrpc.registry.zk.util.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;

/**
 * 基于Zookeeper实现的服务注册
 */
@Slf4j
public class ZkServiceRegistryImpl implements ServiceRegistry {

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        //TODO 添加创建临时结点选项，根据SPI动态选择
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
}

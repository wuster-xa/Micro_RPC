package github.microrpc.loadbalance.loadbalancer;

import github.microrpc.loadbalance.AbstractLoadBalance;
import github.microrpc.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡策略
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}

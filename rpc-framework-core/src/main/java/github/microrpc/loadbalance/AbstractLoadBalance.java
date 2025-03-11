package github.microrpc.loadbalance;

import github.microrpc.remoting.dto.RpcRequest;
import github.microrpc.utils.CollectionUtil;

import java.util.List;

/**
 * 负载均衡抽象类
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest) {
        if (CollectionUtil.isEmpty(serviceAddresses)) {
            return null;
        }
        if (serviceAddresses.size() == 1) {
            return serviceAddresses.get(0);
        }
        return doSelect(serviceAddresses, rpcRequest);
    }

    protected abstract String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest);

}

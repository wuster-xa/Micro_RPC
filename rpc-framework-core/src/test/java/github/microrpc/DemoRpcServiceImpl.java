package github.microrpc;

import github.microrpc.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * 示例RPC服务实现类
 */
@Slf4j
@RpcService(group = "test1", version = "version1")
public class DemoRpcServiceImpl implements DemoRpcService {

    @Override
    public String hello() {
        return "hello";
    }
}

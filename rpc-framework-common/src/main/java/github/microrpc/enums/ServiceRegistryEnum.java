package github.microrpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务注册方式枚举类
 */
@AllArgsConstructor
@Getter
public enum ServiceRegistryEnum {

    ZK("zk");

    private final String name;
}

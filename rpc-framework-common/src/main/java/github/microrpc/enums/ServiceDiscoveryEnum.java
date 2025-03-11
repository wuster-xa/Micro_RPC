package github.microrpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务发现方式枚举类
 */
@AllArgsConstructor
@Getter
public enum ServiceDiscoveryEnum {

    ZK("zk");

    private final String name;
}

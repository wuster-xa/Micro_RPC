package github.microrpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 负载均衡策略枚举类
 */
@AllArgsConstructor
@Getter
public enum LoadBalanceEnum {

    LOADBALANCE("loadBalance");

    private final String name;
}

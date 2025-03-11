package github.microrpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RPC请求传输方式枚举类
 */
@AllArgsConstructor
@Getter
public enum RpcRequestTransportEnum {

    NETTY("netty"),
    SOCKET("socket");

    private final String name;
}

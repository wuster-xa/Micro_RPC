# MicroRPC Framework Documentation

## 项目简介
MicroRPC 是一个轻量级的 RPC 框架，旨在简化分布式系统中的服务调用。它提供了高效的通信机制和易于使用的接口，支持多种序列化和压缩方式。

## 目录结构

```
MicroRPC/
├── rpc-framework-core/          # 核心实现
├── rpc-framework-common/        # 公共模块
├── rpc-framework-spring-boot-starter/  # Spring Boot 启动器
├── rpc-framework-dependencies/  # 依赖管理
├── examples/                    # 示例模块
│   ├── api/                    # 接口定义
│   ├── provider/              # 服务提供者示例
│   └── consumer/              # 服务消费者示例
└── docs/                       # 文档
```

## 快速开始

### 1. 添加依赖

```xml
<dependency>
    <groupId>github.microrpc</groupId>
    <artifactId>rpc-framework-spring-boot-starter</artifactId>
    <version>${version}</version>
</dependency>
```

### 2. 定义服务接口

```java
@RpcService
public interface UserService {
    User getUser(Long id);
}
```

### 3. 实现服务接口

```java
@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(Long id) {
        // 实现逻辑
    }
}
```

### 4. 使用服务

```java
@RestController
public class UserController {
    @RpcReference
    private UserService userService;
    
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
```
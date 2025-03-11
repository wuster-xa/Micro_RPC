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

## 配置选项
- `rpc.server.port`: 指定 RPC 服务器的端口。
- `rpc.client.timeout`: 设置客户端请求超时时间。

## 贡献指南
欢迎贡献代码！请遵循以下步骤：
1. Fork 本仓库
2. 创建分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 支持和反馈
如有问题或建议，请通过 GitHub Issues 提交，或发送邮件至 support@microrpc.com。

## 许可证信息
本项目基于 MIT 许可证开源，详情请参阅 LICENSE 文件。

## 更多文档

- [架构设计](./architecture.md): 详细介绍 MicroRPC 的架构设计，包括各个模块的职责和交互方式。
- [配置说明](./configuration.md): 列出所有可用的配置选项，并说明每个选项的作用。
- [高级特性](./advanced-features.md): 介绍 MicroRPC 提供的高级功能，如负载均衡、服务发现等。
- [最佳实践](./best-practices.md): 提供使用 MicroRPC 的最佳实践和建议。
- [常见问题](./faq.md): 解答用户在使用过程中可能遇到的常见问题。 
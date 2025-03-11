/**
 * RPC服务注解处理器
 */
@Slf4j
@Component
public class RpcServiceAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final ServiceProvider serviceProvider;

    public RpcServiceAnnotationBeanPostProcessor(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        if (rpcService != null) {
            log.info("[{}] is annotated with [{}]", beanClass.getName(), RpcService.class.getCanonicalName());
            // build RpcServiceConfig
            RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                    .group(rpcService.group())
                    .version(rpcService.version())
                    .service(bean).build();
            serviceProvider.publishService(rpcServiceConfig);
        }
        return bean;
    }
} 
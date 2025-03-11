package github.microrpc.spring;

import github.microrpc.annotation.RpcService;
import github.microrpc.provider.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * RPC服务注解处理器
 */
@Slf4j
public class RpcServiceAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final ServiceProvider serviceProvider;

    public RpcServiceAnnotationBeanPostProcessor(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        if (rpcService != null) {
            log.info("[{}] is annotated with [{}]", beanClass.getName(), RpcService.class.getCanonicalName());
            // 发布 RPC 服务
            serviceProvider.publishService(bean, rpcService.group(), rpcService.version());
        }
        return bean;
    }
} 

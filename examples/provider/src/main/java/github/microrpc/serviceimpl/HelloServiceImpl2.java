package github.microrpc.serviceimpl;

import github.microrpc.Hello;
import github.microrpc.HelloService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServiceImpl2 implements HelloService {

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl2收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl2返回: {}.", result);
        return result;
    }
}

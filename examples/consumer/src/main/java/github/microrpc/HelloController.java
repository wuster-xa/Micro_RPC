package github.microrpc;

import github.microrpc.annotation.RpcReference;
import org.springframework.stereotype.Component;


@Component
public class HelloController {

    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;

    public void test() {
        String hello = this.helloService.hello(new Hello("111", "222"));
        System.out.println(helloService.hello(new Hello("111", "222")));
    }
}

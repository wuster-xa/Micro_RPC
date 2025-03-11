package github.microrpc;

import github.microrpc.annotation.RpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan(basePackage = {"github.microrpc"})
public class NettyClientMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
        HelloController helloController = (HelloController) applicationContext.getBean("helloController");
        helloController.test();
    }
}

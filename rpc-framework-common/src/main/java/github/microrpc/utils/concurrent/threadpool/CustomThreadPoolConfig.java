package github.microrpc.utils.concurrent.threadpool;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池自定义配置类
 */
@Getter
@Setter
public class CustomThreadPoolConfig {
    /**
     * 线程池默认参数
     */
    private static final int DEFAULT_CORE_POOL_SIZE = 10;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 100;
    private static final int DEFAULT_KEEP_ALIVE_TIME = 60;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    private static final int DEFAULT_BLOCKING_QUEUE_CAPACITY = 100;
    private static final RejectedExecutionHandler DEFAULT_HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();
    /**
     * 可配置参数
     */
    private int corePoolSize = DEFAULT_CORE_POOL_SIZE;
    private int maximumPoolSize = DEFAULT_MAXIMUM_POOL_SIZE;
    private long keepAliveTime = DEFAULT_KEEP_ALIVE_TIME;
    private TimeUnit unit = DEFAULT_TIME_UNIT;
    // 使用有界队列
    private BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(DEFAULT_BLOCKING_QUEUE_CAPACITY);
    /**
     * 拒绝策略
     */
    private RejectedExecutionHandler handler = DEFAULT_HANDLER;
}

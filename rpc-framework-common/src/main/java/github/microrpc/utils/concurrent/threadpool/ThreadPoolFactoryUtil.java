package github.microrpc.utils.concurrent.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工厂，创建线程池
 */
@Slf4j
public final class ThreadPoolFactoryUtil {

    /**
     * 通过threadNamePrefix区分不同线程池（我们可以把相同threadNamePrefix的线程池看作是为同一业务场景服务）
     * key: threadNamePrefix
     * value: 线程池
     */
    private static final Map<String, ExecutorService> THREAD_POOLS = new ConcurrentHashMap<>();

    private ThreadPoolFactoryUtil() {
    }

    public static ExecutorService createCustomThreadPoolIfAbsent(String threadNamePrefix) {
        CustomThreadPoolConfig customThreadPoolConfig = new CustomThreadPoolConfig();
        return createCustomThreadPoolIfAbsent(customThreadPoolConfig, threadNamePrefix, false);
    }

    public static ExecutorService createCustomThreadPoolIfAbsent(String threadNamePrefix,
            CustomThreadPoolConfig customThreadPoolConfig) {
        return createCustomThreadPoolIfAbsent(customThreadPoolConfig, threadNamePrefix, false);
    }

    public static ExecutorService createCustomThreadPoolIfAbsent(CustomThreadPoolConfig customThreadPoolConfig,
            String threadNamePrefix, Boolean daemon) {
        ExecutorService threadPool = THREAD_POOLS.computeIfAbsent(threadNamePrefix,
                k -> createThreadPool(customThreadPoolConfig, threadNamePrefix, daemon));
        // 如果threadPool被shutdown了就重新创建一个
        if (threadPool.isShutdown() || threadPool.isTerminated()) {
            THREAD_POOLS.remove(threadNamePrefix);
            threadPool = createThreadPool(customThreadPoolConfig, threadNamePrefix, daemon);
            THREAD_POOLS.put(threadNamePrefix, threadPool);
        }
        return threadPool;
    }

    /**
     * 创建 ThreadFactory
     * 。如果threadNamePrefix不为空则使用自建ThreadFactory，否则使用defaultThreadFactory
     *
     * @param threadNamePrefix 作为创建的线程名字的前缀
     * @param daemon           指定是否为 Daemon Thread(守护线程)
     * @return ThreadFactory
     */
    private static ThreadFactory createThreadFactory(String threadNamePrefix, Boolean daemon) {
        if (threadNamePrefix != null) {
            if (daemon != null) {
                return new ThreadFactoryBuilder()
                        .setNameFormat(threadNamePrefix + "-%d")
                        .setDaemon(daemon).build();
            } else {
                return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
            }
        }
        return Thread::new;
    }

    /**
     * 创建自定义配置的线程池
     *
     * @param customThreadPoolConfig 线程池自定义配置
     * @param threadNamePrefix       线程名称前缀
     * @param daemon                 是否守护线程
     * @return ExecutorService
     */
    private static ExecutorService createThreadPool(CustomThreadPoolConfig customThreadPoolConfig,
            String threadNamePrefix, Boolean daemon) {
        ThreadFactory threadFactory = createThreadFactory(threadNamePrefix, daemon);
        return new ThreadPoolExecutor(
                customThreadPoolConfig.getCorePoolSize(),
                customThreadPoolConfig.getMaximumPoolSize(),
                customThreadPoolConfig.getKeepAliveTime(),
                customThreadPoolConfig.getUnit(),
                customThreadPoolConfig.getWorkQueue(),
                threadFactory,
                customThreadPoolConfig.getHandler());
    }

    /**
     * 创建调度线程池
     */
    public static ScheduledExecutorService createScheduledThreadPool(int corePoolSize, String threadNamePrefix,
            boolean daemon) {
        ThreadFactory threadFactory = createThreadFactory(threadNamePrefix, daemon);
        return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
    }

    /**
     * 关闭所有线程池
     */
    public static void shutDownAllThreadPool() {
        log.info("关闭所有线程池...");
        THREAD_POOLS.forEach((threadNamePrefix, executorService) -> {
            shutDownThreadPool(executorService);
        });
    }

    /**
     * 关闭指定线程池
     *
     * @param executorService 线程池
     */
    public static void shutDownThreadPool(ExecutorService executorService) {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
            try {
                log.info("等待线程池优雅关闭");
                if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                    log.error("线程池超时未关闭，强制关闭");
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                log.error("线程池关闭中断", e);
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
            log.info("线程池已关闭");
        }
    }
}

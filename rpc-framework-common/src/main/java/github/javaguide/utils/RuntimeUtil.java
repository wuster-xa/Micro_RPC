package github.javaguide.utils;

/**
 * Utility class for runtime operations.
 */
public class RuntimeUtil {
    /**
     * 获取CPU的核心数
     *
     * @return cpu的核心数
     */
    public static int cpus() {
        return Runtime.getRuntime().availableProcessors();
    }
}

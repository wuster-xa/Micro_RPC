package github.microrpc.utils;

/**
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str 待判断字符串
     * @return 是否为空
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断字符串是否非空
     *
     * @param str 待判断字符串
     * @return 是否非空
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 转为首字母小写的驼峰命名
     *
     * @param str 待转换字符串
     * @return 首字母小写的驼峰命名
     */
    public static String toLowerCamelCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 转为首字母大写的驼峰命名
     *
     * @param str 待转换字符串
     * @return 首字母大写的驼峰命名
     */
    public static String toUpperCamelCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

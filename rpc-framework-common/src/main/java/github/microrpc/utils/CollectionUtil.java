package github.microrpc.utils;

import java.util.Collection;

/**
 * Utility methods for collection operations.
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

}

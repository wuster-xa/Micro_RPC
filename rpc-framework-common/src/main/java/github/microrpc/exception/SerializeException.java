package github.microrpc.exception;

/**
 * 序列化异常
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String message) {
        super(message);
    }
}

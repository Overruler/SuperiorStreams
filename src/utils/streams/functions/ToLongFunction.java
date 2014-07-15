package utils.streams.functions;

/**
 * @see java.util.function.ToLongFunction<T>
 * @author Timo Kinnunen
 */
/**
 * @see java.util.function.ToLongFunction
 * @param <T>
 */
@FunctionalInterface
public interface ToLongFunction<T> extends ExToLongFunction<T, RuntimeException>, java.util.function.ToLongFunction<T> {}

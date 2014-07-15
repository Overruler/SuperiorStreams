package utils.streams.functions;

/**
 * @see java.util.function.ToLongBiFunction<T, U>
 * @author Timo Kinnunen
 */
/**
 * @see java.util.function.ToLongBiFunction
 * @param <T>
 * @param <U>
 */
@FunctionalInterface
public interface ToLongBiFunction<T, U> extends ExToLongBiFunction<T, U, RuntimeException>, java.util.function.ToLongBiFunction<T, U> {}

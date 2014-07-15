package utils.streams.functions;

/**
 * @see java.util.function.ToIntBiFunction<T, U>
 * @author Timo Kinnunen
 */
/**
 * @see java.util.function.ToIntBiFunction
 * @param <T>
 * @param <U>
 */
@FunctionalInterface
public interface ToIntBiFunction<T, U> extends ExToIntBiFunction<T, U, RuntimeException>, java.util.function.ToIntBiFunction<T, U> {}

package utils.streams.functions;

/**
 * @see java.util.function.ToIntFunction<T>
 * @author Timo Kinnunen
 */
/**
 * @see java.util.function.ToIntFunction
 * @param <T>
 */
@FunctionalInterface
public interface ToIntFunction<T> extends ExToIntFunction<T, RuntimeException>, java.util.function.ToIntFunction<T> {}

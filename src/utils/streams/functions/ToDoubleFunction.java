package utils.streams.functions;

/**
 * @see java.util.function.ToDoubleFunction<T>
 * @author Timo Kinnunen
 */
/**
 * @see java.util.function.ToDoubleFunction
 * @param <T>
 */
@FunctionalInterface
public interface ToDoubleFunction<T> extends ExToDoubleFunction<T, RuntimeException>, java.util.function.ToDoubleFunction<T> {}

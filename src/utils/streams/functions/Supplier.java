package utils.streams.functions;

/**
 * @see java.util.function.Supplier<T>
 * @author Timo Kinnunen
 */
/**
 * @see java.util.function.Supplier
 * @param <T>
 */
@FunctionalInterface
public interface Supplier<T> extends ExSupplier<T, RuntimeException>, java.util.function.Supplier<T> {}

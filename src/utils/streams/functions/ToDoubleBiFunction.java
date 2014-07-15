package utils.streams.functions;

/**
 * @see java.util.function.ToDoubleBiFunction<T, U>
 * @author Timo Kinnunen
 */
/**
 * @see java.util.function.ToDoubleBiFunction
 * @param <T>
 * @param <U>
 */
@FunctionalInterface
public interface ToDoubleBiFunction<T, U> extends ExToDoubleBiFunction<T, U, RuntimeException>, java.util.function.ToDoubleBiFunction<T, U> {}

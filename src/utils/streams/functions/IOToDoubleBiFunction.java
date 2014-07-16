package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @see java.util.function.ToDoubleBiFunction
 */
@FunctionalInterface
public interface IOToDoubleBiFunction<T, U> extends ExToDoubleBiFunction<T, U, IOException> {
	static <T, U> IOToDoubleBiFunction<T, U> recheck(java.util.function.ToDoubleBiFunction<T, U> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				return unchecked.applyAsDouble(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default ToDoubleBiFunction<T, U> uncheck() {
		return (T t, U u) -> {
			try {
				return applyAsDouble(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

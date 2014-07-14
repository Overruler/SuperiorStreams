package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface ExToIntFunction<T, E extends Exception> {
	int applyAsInt(T t1) throws E;
	static <T, E extends Exception> ExToIntFunction<T, E> recheck(
		java.util.function.ToIntFunction<T> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.ToIntFunction<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return applyAsInt(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}

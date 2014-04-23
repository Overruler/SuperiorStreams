package utils.streams.functions;

import java.util.Objects;
import java.util.function.LongFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongFunction<R, E extends Exception> {

	R apply(long t1) throws E;

	default <V> ExLongFunction<V, E> andThen(ExFunction<? super R, ? extends V, E> after) {
		Objects.requireNonNull(after);
		return (long t) -> after.apply(apply(t));
	}

	static <R, E extends Exception> ExLongFunction<R, E> recheck(LongFunction<R> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default LongFunction<R> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


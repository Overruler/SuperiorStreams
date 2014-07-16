package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @see java.util.function.BiPredicate
 */
@FunctionalInterface
public interface BiPredicate<T, U> extends ExBiPredicate<T, U, RuntimeException>, java.util.function.BiPredicate<T, U> {
	default BiPredicate<T, U> and(BiPredicate<? super T, ? super U> other) {
		Objects.requireNonNull(other);
		return (T t, U u) -> test(t, u) && other.test(t, u);
	}
	default @Override BiPredicate<T, U> negate() {
		return (T t, U u) -> !test(t, u);
	}
	default BiPredicate<T, U> or(BiPredicate<? super T, ? super U> other) {
		Objects.requireNonNull(other);
		return (T t, U u) -> test(t, u) || other.test(t, u);
	}
	default <E extends Exception> ExBiPredicate<T, U, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				return test(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}

package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.BiPredicate
 * @param <T>
 * @param <U>
 * @param <E>
 */
@FunctionalInterface
public interface ExBiPredicate<T, U, E extends Exception> {
	/**
	 * @param t
	 * @param u
	 * @return boolean
	 * @throws E
	 * @see java.util.function.BiPredicate#test
	 */
	boolean test(T t, U u) throws E;
	default ExBiPredicate<T, U, E> and(ExBiPredicate<? super T, ? super U, E> other) {
		Objects.requireNonNull(other);
		return (T t, U u) -> test(t, u) && other.test(t, u);
	}
	default ExBiPredicate<T, U, E> negate() {
		return (T t, U u) -> !test(t, u);
	}
	default ExBiPredicate<T, U, E> or(ExBiPredicate<? super T, ? super U, E> other) {
		Objects.requireNonNull(other);
		return (T t, U u) -> test(t, u) || other.test(t, u);
	}
	static <T, U, E extends Exception> ExBiPredicate<T, U, E> recheck(
		java.util.function.BiPredicate<T, U> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				return unchecked.test(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.BiPredicate<T, U> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				return test(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}

package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.Predicate
 * @param <T>
 * @param <E>
 */
@FunctionalInterface
public interface ExPredicate<T, E extends Exception> {
	/**
	 * @param t
	 * @return boolean
	 * @throws E
	 * @see java.util.function.Predicate#test
	 */
	boolean test(T t) throws E;
	default ExPredicate<T, E> and(ExPredicate<? super T, E> other) {
		Objects.requireNonNull(other);
		return (T t) -> test(t) && other.test(t);
	}
	default ExPredicate<T, E> negate() {
		return (T t) -> !test(t);
	}
	default ExPredicate<T, E> or(ExPredicate<? super T, E> other) {
		Objects.requireNonNull(other);
		return (T t) -> test(t) || other.test(t);
	}
	static <T, E extends Exception> ExPredicate<T, E> recheck(
		java.util.function.Predicate<T> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.Predicate<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return test(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}

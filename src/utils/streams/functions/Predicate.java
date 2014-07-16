package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.Predicate
 */
@FunctionalInterface
public interface Predicate<T> extends ExPredicate<T, RuntimeException>, java.util.function.Predicate<T> {
	default Predicate<T> and(Predicate<? super T> other) {
		Objects.requireNonNull(other);
		return (T t) -> test(t) && other.test(t);
	}
	default @Override Predicate<T> negate() {
		return (T t) -> !test(t);
	}
	default Predicate<T> or(Predicate<? super T> other) {
		Objects.requireNonNull(other);
		return (T t) -> test(t) || other.test(t);
	}
	default <E extends Exception> ExPredicate<T, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}

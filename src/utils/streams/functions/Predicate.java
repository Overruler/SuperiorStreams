package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.Predicate
 * @param <T>
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
}

package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.BiPredicate
 * @param <T>
 * @param <U>
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
}

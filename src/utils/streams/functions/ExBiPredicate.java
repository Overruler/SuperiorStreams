package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExBiPredicate<T, U, E extends Exception> {
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
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.BiPredicate<T, U> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

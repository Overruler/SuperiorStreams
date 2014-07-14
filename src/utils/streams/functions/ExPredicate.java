package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExPredicate<T, E extends Exception> {
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
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.Predicate<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongPredicate<E extends Exception> {
	boolean test(long t) throws E;
	default ExLongPredicate<E> and(ExLongPredicate<E> other) {
		Objects.requireNonNull(other);
		return (long t) -> test(t) && other.test(t);
	}
	default ExLongPredicate<E> negate() {
		return (long t) -> !test(t);
	}
	default ExLongPredicate<E> or(ExLongPredicate<E> other) {
		Objects.requireNonNull(other);
		return (long t) -> test(t) || other.test(t);
	}
	static <E extends Exception> ExLongPredicate<E> recheck(
		java.util.function.LongPredicate unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.LongPredicate uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

package utils.streams.functions;

import java.util.Objects;
import java.util.function.LongPredicate;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongPredicate<E extends Exception> {

	boolean test(long t1) throws E;

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

	static <E extends Exception> ExLongPredicate<E> recheck(LongPredicate unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default LongPredicate uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


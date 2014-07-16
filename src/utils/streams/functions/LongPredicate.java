package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongPredicate
 */
@FunctionalInterface
public interface LongPredicate extends ExLongPredicate<RuntimeException>, java.util.function.LongPredicate {
	default LongPredicate and(LongPredicate other) {
		Objects.requireNonNull(other);
		return (long t) -> test(t) && other.test(t);
	}
	default @Override LongPredicate negate() {
		return (long t) -> !test(t);
	}
	default LongPredicate or(LongPredicate other) {
		Objects.requireNonNull(other);
		return (long t) -> test(t) || other.test(t);
	}
	default <E extends Exception> ExLongPredicate<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				return test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}

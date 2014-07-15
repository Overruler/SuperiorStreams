package utils.streams.functions;

import java.util.Objects;

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
}

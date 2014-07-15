package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.IntPredicate

 */
@FunctionalInterface
public interface IntPredicate extends ExIntPredicate<RuntimeException>, java.util.function.IntPredicate {
	default IntPredicate and(IntPredicate other) {
		Objects.requireNonNull(other);
		return (int t) -> test(t) && other.test(t);
	}
	default @Override IntPredicate negate() {
		return (int t) -> !test(t);
	}
	default IntPredicate or(IntPredicate other) {
		Objects.requireNonNull(other);
		return (int t) -> test(t) || other.test(t);
	}
}

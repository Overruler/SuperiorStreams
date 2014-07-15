package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.DoublePredicate

 */
@FunctionalInterface
public interface DoublePredicate extends ExDoublePredicate<RuntimeException>, java.util.function.DoublePredicate {
	default DoublePredicate and(DoublePredicate other) {
		Objects.requireNonNull(other);
		return (double t) -> test(t) && other.test(t);
	}
	default @Override DoublePredicate negate() {
		return (double t) -> !test(t);
	}
	default DoublePredicate or(DoublePredicate other) {
		Objects.requireNonNull(other);
		return (double t) -> test(t) || other.test(t);
	}
}

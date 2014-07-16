package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

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
	default <E extends Exception> ExDoublePredicate<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				return test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}

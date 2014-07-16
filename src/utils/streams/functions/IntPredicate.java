package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

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
	default <E extends Exception> ExIntPredicate<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				return test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}

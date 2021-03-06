package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.IntPredicate
 */
@FunctionalInterface
public interface ExIntPredicate<E extends Exception> {
	/**
	 * @param t
	 * @return boolean
	 * @throws E
	 * @see java.util.function.IntPredicate#test
	 */
	boolean test(int t) throws E;
	default ExIntPredicate<E> and(ExIntPredicate<E> other) {
		Objects.requireNonNull(other);
		return (int t) -> test(t) && other.test(t);
	}
	default ExIntPredicate<E> negate() {
		return (int t) -> !test(t);
	}
	default ExIntPredicate<E> or(ExIntPredicate<E> other) {
		Objects.requireNonNull(other);
		return (int t) -> test(t) || other.test(t);
	}
	static <E extends Exception> ExIntPredicate<E> recheck(
		java.util.function.IntPredicate unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				return unchecked.test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default IntPredicate uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				return test(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}

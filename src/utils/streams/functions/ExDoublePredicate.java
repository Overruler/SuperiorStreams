package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.DoublePredicate
 */
@FunctionalInterface
public interface ExDoublePredicate<E extends Exception> {
	/**
	 * @param t
	 * @return boolean
	 * @throws E
	 * @see java.util.function.DoublePredicate#test
	 */
	boolean test(double t) throws E;
	default ExDoublePredicate<E> and(ExDoublePredicate<E> other) {
		Objects.requireNonNull(other);
		return (double t) -> test(t) && other.test(t);
	}
	default ExDoublePredicate<E> negate() {
		return (double t) -> !test(t);
	}
	default ExDoublePredicate<E> or(ExDoublePredicate<E> other) {
		Objects.requireNonNull(other);
		return (double t) -> test(t) || other.test(t);
	}
	static <E extends Exception> ExDoublePredicate<E> recheck(
		java.util.function.DoublePredicate unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				return unchecked.test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default DoublePredicate uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				return test(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}

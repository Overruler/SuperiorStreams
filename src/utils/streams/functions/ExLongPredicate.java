package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

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
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				return unchecked.test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.LongPredicate uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				return test(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}

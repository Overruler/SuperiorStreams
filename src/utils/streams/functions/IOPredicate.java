package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.Predicate
 * @param <T>
 */
@FunctionalInterface
public interface IOPredicate<T> extends ExPredicate<T, IOException> {
	default IOPredicate<T> and(IOPredicate<? super T> other) {
		Objects.requireNonNull(other);
		return (T t) -> test(t) && other.test(t);
	}
	default @Override IOPredicate<T> negate() {
		return (T t) -> !test(t);
	}
	default IOPredicate<T> or(IOPredicate<? super T> other) {
		Objects.requireNonNull(other);
		return (T t) -> test(t) || other.test(t);
	}
	static <T> IOPredicate<T> recheck(java.util.function.Predicate<T> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.Predicate<T> uncheck() {
		return (T t) -> {
			try {
				return test(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

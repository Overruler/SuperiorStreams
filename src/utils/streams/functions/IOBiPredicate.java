package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @see java.util.function.BiPredicate
 */
@FunctionalInterface
public interface IOBiPredicate<T, U> extends ExBiPredicate<T, U, IOException> {
	default IOBiPredicate<T, U> and(IOBiPredicate<? super T, ? super U> other) {
		Objects.requireNonNull(other);
		return (T t, U u) -> test(t, u) && other.test(t, u);
	}
	default @Override IOBiPredicate<T, U> negate() {
		return (T t, U u) -> !test(t, u);
	}
	default IOBiPredicate<T, U> or(IOBiPredicate<? super T, ? super U> other) {
		Objects.requireNonNull(other);
		return (T t, U u) -> test(t, u) || other.test(t, u);
	}
	static <T, U> IOBiPredicate<T, U> recheck(java.util.function.BiPredicate<T, U> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				return unchecked.test(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default BiPredicate<T, U> uncheck() {
		return (T t, U u) -> {
			try {
				return test(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

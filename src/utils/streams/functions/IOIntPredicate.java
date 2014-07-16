package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntPredicate
 */
@FunctionalInterface
public interface IOIntPredicate extends ExIntPredicate<IOException> {
	default IOIntPredicate and(IOIntPredicate other) {
		Objects.requireNonNull(other);
		return (int t) -> test(t) && other.test(t);
	}
	default @Override IOIntPredicate negate() {
		return (int t) -> !test(t);
	}
	default IOIntPredicate or(IOIntPredicate other) {
		Objects.requireNonNull(other);
		return (int t) -> test(t) || other.test(t);
	}
	static IOIntPredicate recheck(java.util.function.IntPredicate unchecked) {
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				return unchecked.test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default IntPredicate uncheck() {
		return (int t) -> {
			try {
				return test(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

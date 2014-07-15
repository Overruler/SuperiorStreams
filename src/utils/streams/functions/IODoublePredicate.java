package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoublePredicate

 */
@FunctionalInterface
public interface IODoublePredicate extends ExDoublePredicate<IOException> {
	default IODoublePredicate and(IODoublePredicate other) {
		Objects.requireNonNull(other);
		return (double t) -> test(t) && other.test(t);
	}
	default @Override IODoublePredicate negate() {
		return (double t) -> !test(t);
	}
	default IODoublePredicate or(IODoublePredicate other) {
		Objects.requireNonNull(other);
		return (double t) -> test(t) || other.test(t);
	}
	static IODoublePredicate recheck(java.util.function.DoublePredicate unchecked) {
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				return unchecked.test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.DoublePredicate uncheck() {
		return (double t) -> {
			try {
				return test(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

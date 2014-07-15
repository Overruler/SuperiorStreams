package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongPredicate

 */
@FunctionalInterface
public interface IOLongPredicate extends ExLongPredicate<IOException> {
	default IOLongPredicate and(IOLongPredicate other) {
		Objects.requireNonNull(other);
		return (long t) -> test(t) && other.test(t);
	}
	default @Override IOLongPredicate negate() {
		return (long t) -> !test(t);
	}
	default IOLongPredicate or(IOLongPredicate other) {
		Objects.requireNonNull(other);
		return (long t) -> test(t) || other.test(t);
	}
	static IOLongPredicate recheck(java.util.function.LongPredicate unchecked) {
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				return unchecked.test(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.LongPredicate uncheck() {
		return (long t) -> {
			try {
				return test(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

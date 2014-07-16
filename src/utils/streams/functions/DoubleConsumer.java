package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleConsumer
 */
@FunctionalInterface
public interface DoubleConsumer extends ExDoubleConsumer<RuntimeException>, java.util.function.DoubleConsumer {
	default DoubleConsumer andThen(DoubleConsumer after) {
		Objects.requireNonNull(after);
		return (double t) -> {
			accept(t);
			after.accept(t);
		};
	}
	default <E extends Exception> ExDoubleConsumer<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}

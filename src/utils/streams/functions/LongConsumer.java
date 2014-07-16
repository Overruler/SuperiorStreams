package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongConsumer
 */
@FunctionalInterface
public interface LongConsumer extends ExLongConsumer<RuntimeException>, java.util.function.LongConsumer {
	default LongConsumer andThen(LongConsumer after) {
		Objects.requireNonNull(after);
		return (long t) -> {
			accept(t);
			after.accept(t);
		};
	}
	default <E extends Exception> ExLongConsumer<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}

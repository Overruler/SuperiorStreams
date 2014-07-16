package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntConsumer
 */
@FunctionalInterface
public interface IntConsumer extends ExIntConsumer<RuntimeException>, java.util.function.IntConsumer {
	default IntConsumer andThen(IntConsumer after) {
		Objects.requireNonNull(after);
		return (int t) -> {
			accept(t);
			after.accept(t);
		};
	}
	default <E extends Exception> ExIntConsumer<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}

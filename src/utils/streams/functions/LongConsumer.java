package utils.streams.functions;

import java.util.Objects;

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
}

package utils.streams.functions;

import java.util.Objects;

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
}

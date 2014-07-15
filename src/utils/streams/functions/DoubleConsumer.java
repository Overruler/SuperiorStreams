package utils.streams.functions;

import java.util.Objects;

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
}

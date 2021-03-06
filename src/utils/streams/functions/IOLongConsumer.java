package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongConsumer
 */
@FunctionalInterface
public interface IOLongConsumer extends ExLongConsumer<IOException> {
	default IOLongConsumer andThen(IOLongConsumer after) {
		Objects.requireNonNull(after);
		return (long t) -> {
			accept(t);
			after.accept(t);
		};
	}
	static IOLongConsumer recheck(java.util.function.LongConsumer unchecked) {
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				unchecked.accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default LongConsumer uncheck() {
		return (long t) -> {
			try {
				accept(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

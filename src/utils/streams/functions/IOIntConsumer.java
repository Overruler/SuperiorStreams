package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntConsumer
 */
@FunctionalInterface
public interface IOIntConsumer extends ExIntConsumer<IOException> {
	default IOIntConsumer andThen(IOIntConsumer after) {
		Objects.requireNonNull(after);
		return (int t) -> {
			accept(t);
			after.accept(t);
		};
	}
	static IOIntConsumer recheck(java.util.function.IntConsumer unchecked) {
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				unchecked.accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default IntConsumer uncheck() {
		return (int t) -> {
			try {
				accept(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

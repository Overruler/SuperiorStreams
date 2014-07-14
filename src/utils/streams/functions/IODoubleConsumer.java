package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface IODoubleConsumer extends ExDoubleConsumer<IOException> {
	default IODoubleConsumer andThen(IODoubleConsumer after) {
		Objects.requireNonNull(after);
		return (double t) -> {
			accept(t);
			after.accept(t);
		};
	}
	static IODoubleConsumer recheck(java.util.function.DoubleConsumer unchecked) {
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				unchecked.accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.DoubleConsumer uncheck() {
		return (double t) -> {
			try {
				accept(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

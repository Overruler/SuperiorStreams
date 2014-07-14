package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface IOLongToIntFunction extends ExLongToIntFunction<IOException> {
	static IOLongToIntFunction recheck(java.util.function.LongToIntFunction unchecked) {
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				return unchecked.applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.LongToIntFunction uncheck() {
		return (long t) -> {
			try {
				return applyAsInt(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongToDoubleFunction
 */
@FunctionalInterface
public interface IOLongToDoubleFunction extends ExLongToDoubleFunction<IOException> {
	static IOLongToDoubleFunction recheck(java.util.function.LongToDoubleFunction unchecked) {
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				return unchecked.applyAsDouble(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default LongToDoubleFunction uncheck() {
		return (long t) -> {
			try {
				return applyAsDouble(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

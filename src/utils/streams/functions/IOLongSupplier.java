package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongSupplier

 */
@FunctionalInterface
public interface IOLongSupplier extends ExLongSupplier<IOException> {
	static IOLongSupplier recheck(java.util.function.LongSupplier unchecked) {
		Objects.requireNonNull(unchecked);
		return () -> {
			try {
				return unchecked.getAsLong();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.LongSupplier uncheck() {
		return () -> {
			try {
				return getAsLong();
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

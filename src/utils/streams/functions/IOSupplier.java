package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.Supplier
 */
@FunctionalInterface
public interface IOSupplier<T> extends ExSupplier<T, IOException> {
	static <T> IOSupplier<T> recheck(java.util.function.Supplier<T> unchecked) {
		Objects.requireNonNull(unchecked);
		return () -> {
			try {
				return unchecked.get();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default Supplier<T> uncheck() {
		return () -> {
			try {
				return get();
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

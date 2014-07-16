package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleSupplier
 */
@FunctionalInterface
public interface IODoubleSupplier extends ExDoubleSupplier<IOException> {
	static IODoubleSupplier recheck(java.util.function.DoubleSupplier unchecked) {
		Objects.requireNonNull(unchecked);
		return () -> {
			try {
				return unchecked.getAsDouble();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default DoubleSupplier uncheck() {
		return () -> {
			try {
				return getAsDouble();
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

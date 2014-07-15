package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntSupplier

 */
@FunctionalInterface
public interface IOIntSupplier extends ExIntSupplier<IOException> {
	static IOIntSupplier recheck(java.util.function.IntSupplier unchecked) {
		Objects.requireNonNull(unchecked);
		return () -> {
			try {
				return unchecked.getAsInt();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.IntSupplier uncheck() {
		return () -> {
			try {
				return getAsInt();
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

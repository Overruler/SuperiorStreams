package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntToDoubleFunction

 */
@FunctionalInterface
public interface IOIntToDoubleFunction extends ExIntToDoubleFunction<IOException> {
	static IOIntToDoubleFunction recheck(java.util.function.IntToDoubleFunction unchecked) {
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				return unchecked.applyAsDouble(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.IntToDoubleFunction uncheck() {
		return (int t) -> {
			try {
				return applyAsDouble(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

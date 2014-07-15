package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntToLongFunction

 */
@FunctionalInterface
public interface IOIntToLongFunction extends ExIntToLongFunction<IOException> {
	static IOIntToLongFunction recheck(java.util.function.IntToLongFunction unchecked) {
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				return unchecked.applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.IntToLongFunction uncheck() {
		return (int t) -> {
			try {
				return applyAsLong(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

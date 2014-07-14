package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface IODoubleToIntFunction extends ExDoubleToIntFunction<IOException> {
	static IODoubleToIntFunction recheck(java.util.function.DoubleToIntFunction unchecked) {
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				return unchecked.applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.DoubleToIntFunction uncheck() {
		return (double t) -> {
			try {
				return applyAsInt(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

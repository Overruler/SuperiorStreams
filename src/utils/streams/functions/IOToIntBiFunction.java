package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface IOToIntBiFunction<T, U> extends ExToIntBiFunction<T, U, IOException> {
	static <T, U> IOToIntBiFunction<T, U> recheck(java.util.function.ToIntBiFunction<T, U> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				return unchecked.applyAsInt(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.ToIntBiFunction<T, U> uncheck() {
		return (T t, U u) -> {
			try {
				return applyAsInt(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

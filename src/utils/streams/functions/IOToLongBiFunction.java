package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface IOToLongBiFunction<T, U> extends ExToLongBiFunction<T, U, IOException> {
	static <T, U> IOToLongBiFunction<T, U> recheck(java.util.function.ToLongBiFunction<T, U> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				return unchecked.applyAsLong(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.ToLongBiFunction<T, U> uncheck() {
		return (T t, U u) -> {
			try {
				return applyAsLong(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

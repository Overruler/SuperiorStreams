package utils.streams.functions;

import java.util.function.Supplier;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExSupplier<T, E extends Exception> {

	T get() throws E;

	static <T, E extends Exception> ExSupplier<T, E> recheck(Supplier<T> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default Supplier<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


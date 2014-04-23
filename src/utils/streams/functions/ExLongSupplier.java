package utils.streams.functions;

import java.util.function.LongSupplier;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongSupplier<E extends Exception> {

	long getAsLong() throws E;

	static <E extends Exception> ExLongSupplier<E> recheck(LongSupplier unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default LongSupplier uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


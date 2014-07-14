package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongSupplier<E extends Exception> {
	long getAsLong() throws E;
	static <E extends Exception> ExLongSupplier<E> recheck(
		java.util.function.LongSupplier unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.LongSupplier uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

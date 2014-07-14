package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleSupplier<E extends Exception> {
	double getAsDouble() throws E;
	static <E extends Exception> ExDoubleSupplier<E> recheck(
		java.util.function.DoubleSupplier unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.DoubleSupplier uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

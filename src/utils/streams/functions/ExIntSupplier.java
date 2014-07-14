package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntSupplier<E extends Exception> {
	int getAsInt() throws E;
	static <E extends Exception> ExIntSupplier<E> recheck(
		java.util.function.IntSupplier unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.IntSupplier uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

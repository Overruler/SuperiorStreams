package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntToLongFunction<E extends Exception> {
	long applyAsLong(int t1) throws E;
	static <E extends Exception> ExIntToLongFunction<E> recheck(
		java.util.function.IntToLongFunction unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.IntToLongFunction uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

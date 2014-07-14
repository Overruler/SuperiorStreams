package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongBinaryOperator<E extends Exception> {
	long applyAsLong(long t1, long t2) throws E;
	static <E extends Exception> ExLongBinaryOperator<E> recheck(
		java.util.function.LongBinaryOperator unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.LongBinaryOperator uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

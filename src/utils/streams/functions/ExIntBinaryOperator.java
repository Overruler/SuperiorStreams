package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntBinaryOperator<E extends Exception> {
	int applyAsInt(int t1, int t2) throws E;
	static <E extends Exception> ExIntBinaryOperator<E> recheck(
		java.util.function.IntBinaryOperator unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.IntBinaryOperator uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

package utils.streams.functions;

import java.util.Comparator;
import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExBinaryOperator<T, E extends Exception> extends ExBiFunction<T, T, T, E> {
	static <T, E extends Exception> ExBinaryOperator<T, E> minBy(Comparator<? super T> comparator) {
		Objects.requireNonNull(comparator);
		return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
	}
	static <T, E extends Exception> ExBinaryOperator<T, E> maxBy(Comparator<? super T> comparator) {
		Objects.requireNonNull(comparator);
		return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
	}
	static <T, E extends Exception> ExBinaryOperator<T, E> recheck(
		java.util.function.BinaryOperator<T> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default @Override java.util.function.BinaryOperator<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

package utils.streams.functions;

import java.util.Comparator;
import java.util.Objects;

/**
 * @see java.util.function.BinaryOperator
 * @param <T>
 */
@FunctionalInterface
public interface BinaryOperator<T> extends ExBinaryOperator<T, RuntimeException>, BiFunction<T, T, T>,
	java.util.function.BinaryOperator<T> {
	static <T> BinaryOperator<T> minBy(Comparator<? super T> comparator) {
		Objects.requireNonNull(comparator);
		return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
	}
	static <T> BinaryOperator<T> maxBy(Comparator<? super T> comparator) {
		Objects.requireNonNull(comparator);
		return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
	}
}

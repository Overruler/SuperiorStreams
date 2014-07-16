package utils.streams.functions;

import java.util.Comparator;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.BinaryOperator
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
	default @Override <E extends Exception> ExBinaryOperator<T, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, T u) -> {
			try {
				return apply(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}

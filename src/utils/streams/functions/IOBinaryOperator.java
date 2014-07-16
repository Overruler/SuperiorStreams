package utils.streams.functions;

import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.BinaryOperator
 */
@FunctionalInterface
public interface IOBinaryOperator<T> extends ExBinaryOperator<T, IOException>, IOBiFunction<T, T, T> {
	static <T> IOBinaryOperator<T> minBy(Comparator<? super T> comparator) {
		Objects.requireNonNull(comparator);
		return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
	}
	static <T> IOBinaryOperator<T> maxBy(Comparator<? super T> comparator) {
		Objects.requireNonNull(comparator);
		return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
	}
	static <T> IOBinaryOperator<T> recheck(java.util.function.BinaryOperator<T> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t, T u) -> {
			try {
				return unchecked.apply(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default @Override BinaryOperator<T> uncheck() {
		return (T t, T u) -> {
			try {
				return apply(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}

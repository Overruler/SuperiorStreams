package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface ExLongBinaryOperator<E extends Exception> {
	long applyAsLong(long t1, long t2) throws E;
	static <E extends Exception> ExLongBinaryOperator<E> recheck(
		java.util.function.LongBinaryOperator unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (long t, long u) -> {
			try {
				return unchecked.applyAsLong(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.LongBinaryOperator uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t, long u) -> {
			try {
				return applyAsLong(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}

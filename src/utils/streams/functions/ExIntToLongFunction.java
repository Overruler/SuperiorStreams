package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface ExIntToLongFunction<E extends Exception> {
	long applyAsLong(int t1) throws E;
	static <E extends Exception> ExIntToLongFunction<E> recheck(
		java.util.function.IntToLongFunction unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				return unchecked.applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.IntToLongFunction uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				return applyAsLong(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}

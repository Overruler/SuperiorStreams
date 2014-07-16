package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.LongToIntFunction
 */
@FunctionalInterface
public interface ExLongToIntFunction<E extends Exception> {
	/**
	 * @param t
	 * @return int
	 * @throws E
	 * @see java.util.function.LongToIntFunction#applyAsInt
	 */
	int applyAsInt(long t) throws E;
	static <E extends Exception> ExLongToIntFunction<E> recheck(
		java.util.function.LongToIntFunction unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				return unchecked.applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default LongToIntFunction uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				return applyAsInt(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}

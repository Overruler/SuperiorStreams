package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface ExIntUnaryOperator<E extends Exception> {
	int applyAsInt(int t) throws E;
	default ExIntUnaryOperator<E> compose(ExIntUnaryOperator<E> before) {
		Objects.requireNonNull(before);
		return (int v) -> applyAsInt(before.applyAsInt(v));
	}
	default ExIntUnaryOperator<E> andThen(ExIntUnaryOperator<E> after) {
		Objects.requireNonNull(after);
		return (int t) -> after.applyAsInt(applyAsInt(t));
	}
	static <E extends Exception> ExIntUnaryOperator<E> identity() {
		return t -> t;
	}
	static <E extends Exception> ExIntUnaryOperator<E> recheck(
		java.util.function.IntUnaryOperator unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				return unchecked.applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.IntUnaryOperator uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				return applyAsInt(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}

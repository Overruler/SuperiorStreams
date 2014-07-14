package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface ExDoubleBinaryOperator<E extends Exception> {
	double applyAsDouble(double t1, double t2) throws E;
	static <E extends Exception> ExDoubleBinaryOperator<E> recheck(
		java.util.function.DoubleBinaryOperator unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (double t, double u) -> {
			try {
				return unchecked.applyAsDouble(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.DoubleBinaryOperator uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t, double u) -> {
			try {
				return applyAsDouble(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}

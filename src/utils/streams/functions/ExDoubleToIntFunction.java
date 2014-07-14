package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleToIntFunction<E extends Exception> {
	int applyAsInt(double t1) throws E;
	static <E extends Exception> ExDoubleToIntFunction<E> recheck(
		java.util.function.DoubleToIntFunction unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.DoubleToIntFunction uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

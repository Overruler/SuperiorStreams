package utils.streams.functions;

import java.util.function.DoubleToLongFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleToLongFunction<E extends Exception> {

	long applyAsLong(double t1) throws E;

	static <E extends Exception> ExDoubleToLongFunction<E> recheck(DoubleToLongFunction unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default DoubleToLongFunction uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


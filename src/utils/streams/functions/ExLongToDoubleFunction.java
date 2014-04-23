package utils.streams.functions;

import java.util.function.LongToDoubleFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongToDoubleFunction<E extends Exception> {

	double applyAsDouble(long t1) throws E;

	static <E extends Exception> ExLongToDoubleFunction<E> recheck(LongToDoubleFunction unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default LongToDoubleFunction uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


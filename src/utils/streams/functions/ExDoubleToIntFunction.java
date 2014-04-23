package utils.streams.functions;

import java.util.function.DoubleToIntFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleToIntFunction<E extends Exception> {

	int applyAsInt(double t1) throws E;

	static <E extends Exception> ExDoubleToIntFunction<E> recheck(DoubleToIntFunction unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default DoubleToIntFunction uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


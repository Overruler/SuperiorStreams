package utils.streams.functions;

import java.util.function.IntToDoubleFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntToDoubleFunction<E extends Exception> {

	double applyAsDouble(int t1) throws E;

	static <E extends Exception> ExIntToDoubleFunction<E> recheck(IntToDoubleFunction unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default IntToDoubleFunction uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


package utils.streams.functions;

import java.util.function.IntToLongFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntToLongFunction<E extends Exception> {

	long applyAsLong(int t1) throws E;

	static <E extends Exception> ExIntToLongFunction<E> recheck(IntToLongFunction unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default IntToLongFunction uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


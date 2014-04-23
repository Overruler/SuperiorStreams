package utils.streams.functions;

import java.util.function.LongToIntFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongToIntFunction<E extends Exception> {

	int applyAsInt(long t1) throws E;

	static <E extends Exception> ExLongToIntFunction<E> recheck(LongToIntFunction unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default LongToIntFunction uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


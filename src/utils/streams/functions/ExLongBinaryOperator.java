package utils.streams.functions;

import java.util.function.LongBinaryOperator;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongBinaryOperator<E extends Exception> {

	long applyAsLong(long t1, long t2) throws E;

	static <E extends Exception> ExLongBinaryOperator<E> recheck(LongBinaryOperator unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default LongBinaryOperator uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


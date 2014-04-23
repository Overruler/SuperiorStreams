package utils.streams.functions;

import java.util.function.IntBinaryOperator;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntBinaryOperator<E extends Exception> {

	int applyAsInt(int t1, int t2) throws E;

	static <E extends Exception> ExIntBinaryOperator<E> recheck(IntBinaryOperator unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default IntBinaryOperator uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


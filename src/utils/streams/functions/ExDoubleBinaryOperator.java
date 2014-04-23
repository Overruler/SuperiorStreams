package utils.streams.functions;

import java.util.function.DoubleBinaryOperator;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleBinaryOperator<E extends Exception> {

	double applyAsDouble(double t1, double t2) throws E;

	static <E extends Exception> ExDoubleBinaryOperator<E> recheck(DoubleBinaryOperator unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default DoubleBinaryOperator uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


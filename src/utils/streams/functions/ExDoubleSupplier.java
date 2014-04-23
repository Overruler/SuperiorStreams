package utils.streams.functions;

import java.util.function.DoubleSupplier;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleSupplier<E extends Exception> {

	double getAsDouble() throws E;

	static <E extends Exception> ExDoubleSupplier<E> recheck(DoubleSupplier unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default DoubleSupplier uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


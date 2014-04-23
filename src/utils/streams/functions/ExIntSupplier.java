package utils.streams.functions;

import java.util.function.IntSupplier;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntSupplier<E extends Exception> {

	int getAsInt() throws E;

	static <E extends Exception> ExIntSupplier<E> recheck(IntSupplier unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default IntSupplier uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


package utils.streams.functions;

import java.util.Objects;
import java.util.function.DoubleConsumer;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleConsumer<E extends Exception> {

	void accept(double t1) throws E;

	default ExDoubleConsumer<E> andThen(ExDoubleConsumer<E> after) {
		Objects.requireNonNull(after);
		return (double t1) -> {
			accept(t1);
			after.accept(t1);
		};
	}

	static <E extends Exception> ExDoubleConsumer<E> recheck(DoubleConsumer unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default DoubleConsumer uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


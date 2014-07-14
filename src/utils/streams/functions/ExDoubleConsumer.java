package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleConsumer<E extends Exception> {
	void accept(double t) throws E;
	default ExDoubleConsumer<E> andThen(ExDoubleConsumer<E> after) {
		Objects.requireNonNull(after);
		return (double t) -> {
			accept(t);
			after.accept(t);
		};
	}
	static <E extends Exception> ExDoubleConsumer<E> recheck(
		java.util.function.DoubleConsumer unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.DoubleConsumer uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

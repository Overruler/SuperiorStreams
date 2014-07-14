package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongConsumer<E extends Exception> {
	void accept(long t) throws E;
	default ExLongConsumer<E> andThen(ExLongConsumer<E> after) {
		Objects.requireNonNull(after);
		return (long t) -> {
			accept(t);
			after.accept(t);
		};
	}
	static <E extends Exception> ExLongConsumer<E> recheck(
		java.util.function.LongConsumer unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.LongConsumer uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

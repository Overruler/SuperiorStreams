package utils.streams.functions;

import java.util.Objects;
import java.util.function.LongConsumer;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongConsumer<E extends Exception> {

	void accept(long t1) throws E;

	default ExLongConsumer<E> andThen(ExLongConsumer<E> after) {
		Objects.requireNonNull(after);
		return (long t1) -> {
			accept(t1);
			after.accept(t1);
		};
	}

	static <E extends Exception> ExLongConsumer<E> recheck(LongConsumer unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default LongConsumer uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


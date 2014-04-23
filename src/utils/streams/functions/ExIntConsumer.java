package utils.streams.functions;

import java.util.Objects;
import java.util.function.IntConsumer;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntConsumer<E extends Exception> {

	void accept(int t1) throws E;

	default ExIntConsumer<E> andThen(ExIntConsumer<E> after) {
		Objects.requireNonNull(after);
		return (int t1) -> {
			accept(t1);
			after.accept(t1);
		};
	}

	static <E extends Exception> ExIntConsumer<E> recheck(IntConsumer unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default IntConsumer uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}


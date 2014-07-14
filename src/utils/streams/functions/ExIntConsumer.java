package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntConsumer<E extends Exception> {
	void accept(int t) throws E;
	default ExIntConsumer<E> andThen(ExIntConsumer<E> after) {
		Objects.requireNonNull(after);
		return (int t) -> {
			accept(t);
			after.accept(t);
		};
	}
	static <E extends Exception> ExIntConsumer<E> recheck(
		java.util.function.IntConsumer unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.IntConsumer uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

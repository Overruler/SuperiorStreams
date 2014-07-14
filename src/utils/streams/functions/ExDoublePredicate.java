package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoublePredicate<E extends Exception> {
	boolean test(double t) throws E;
	default ExDoublePredicate<E> and(ExDoublePredicate<E> other) {
		Objects.requireNonNull(other);
		return (double t) -> test(t) && other.test(t);
	}
	default ExDoublePredicate<E> negate() {
		return (double t) -> !test(t);
	}
	default ExDoublePredicate<E> or(ExDoublePredicate<E> other) {
		Objects.requireNonNull(other);
		return (double t) -> test(t) || other.test(t);
	}
	static <E extends Exception> ExDoublePredicate<E> recheck(
		java.util.function.DoublePredicate unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.DoublePredicate uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

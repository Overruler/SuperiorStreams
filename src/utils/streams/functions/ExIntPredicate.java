package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntPredicate<E extends Exception> {
	boolean test(int t) throws E;
	default ExIntPredicate<E> and(ExIntPredicate<E> other) {
		Objects.requireNonNull(other);
		return (int t) -> test(t) && other.test(t);
	}
	default ExIntPredicate<E> negate() {
		return (int t) -> !test(t);
	}
	default ExIntPredicate<E> or(ExIntPredicate<E> other) {
		Objects.requireNonNull(other);
		return (int t) -> test(t) || other.test(t);
	}
	static <E extends Exception> ExIntPredicate<E> recheck(
		java.util.function.IntPredicate unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.IntPredicate uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

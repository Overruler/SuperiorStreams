package utils.streams.codegen;

import java.util.Objects;
import java.util.function.IntBinaryOperator;
import utils.streams.WrapperException;
import utils.streams.functions.ExIntBinaryOperator;

public interface TemplateRechecked {
	static <E extends Exception> ExIntBinaryOperator<E> rechecked(Class<E> classOfE, IntBinaryOperator it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1, int t2) -> {
			try {
				return it.applyAsInt(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}

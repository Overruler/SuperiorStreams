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

	static String[] markers() {
		return new String[] {
		/**/"MARKER_UNCHECKED_TYPE", "IntBinaryOperator                    ",
		/**/"MARKER_RECHECKED_TYPE", "IntBinaryOperatorish<E>              ",
		/**/"MARK_RETURNS", "return it.                           ",
		/**/"MARK_METHOD_NAME     ", "applyAsInt                           ",
		/**/"MARK_TYPE_VARIABLES  ", "static <E extends Exception>         ",
		/**/"MARK_CALL_VARIABLES  ", "(int t1, int t2)                     ",
		/**/"MARK_CALL_ARGUMENTS  ", "(t1, t2)                             ",
		};
	}
}

package utils.streams;

import java.util.Objects;

public final class WrapperException extends UncheckedExceptionWrappingException {

	private <E extends Exception> WrapperException(E e, Class<E> classOfE) {
		super(e, classOfE);
		Objects.requireNonNull(classOfE);
	}
	public static <E extends Exception> E show(RuntimeException e, Class<E> classOfE) {
		return unwrapCause(classOfE, e);
	}
	public static <E extends Exception> RuntimeException hide(Exception e, Class<E> classOfE) {
		Objects.requireNonNull(e);
		if(RuntimeException.class.isInstance(e)) {
			throw RuntimeException.class.cast(e);
		}
		if(classOfE.isInstance(e)) {
			return new WrapperException(classOfE.cast(e), classOfE);
		}
		throw new UnexpectedCastFailureException(e, classOfE);
	}
	static <E extends Exception> E unwrapException(RuntimeException e, Class<E> classOfE) {
		return unwrapCause(classOfE, e);
	}
}

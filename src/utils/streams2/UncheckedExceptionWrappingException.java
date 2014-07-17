package utils.streams2;

import java.util.Objects;

class UncheckedExceptionWrappingException extends RuntimeException {
	final Class<?> originalClass;

	UncheckedExceptionWrappingException(Exception e, Class<?> classOfE) {
		super(e);
		originalClass = Objects.requireNonNull(classOfE);
	}
	public @Override final synchronized Throwable fillInStackTrace() {
		return this;
	}
	static <E extends Exception> E unwrapCause(Class<E> classOfE, RuntimeException e) {
		Throwable cause = e.getCause();
		if(classOfE.isInstance(cause) == false) {
			throw e;
		}
		return classOfE.cast(cause);
	}
}

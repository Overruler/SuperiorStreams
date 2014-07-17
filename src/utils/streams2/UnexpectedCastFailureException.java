package utils.streams2;

import java.util.Objects;

public class UnexpectedCastFailureException extends ClassCastException {
	public UnexpectedCastFailureException(Exception e, Class<?> baseClass) {
		super("Cannot wrap exception of " + e.getClass() + " as " + baseClass);
		Objects.requireNonNull(baseClass);
		addSuppressed(e);
	}
}

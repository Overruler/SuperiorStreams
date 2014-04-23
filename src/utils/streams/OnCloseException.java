package utils.streams;


public final class OnCloseException extends UncheckedExceptionWrappingException {

  public OnCloseException(Exception e, Class<?> classOfE) {
    super(e, classOfE);
  }

  public <E extends Exception> void rethrow(Class<E> classOfE) throws E {
    throw unwrapCause(classOfE, this);
  }

  public <E extends Exception> E unwrap(Class<E> classOfE) {
    return unwrapCause(classOfE, this);
  }
}

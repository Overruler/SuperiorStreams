package utils.streams2;

public interface Streamable<T, E extends Exception> {
	java.util.stream.Stream<T> stream() throws E;
	Iterable<T> iterable() throws E;
}

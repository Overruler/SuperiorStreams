package utils.streams;

import java.util.stream.Stream;

public interface Streamable<T, E extends Exception> {
	Stream<T> stream() throws E;
	Iterable<T> iterable() throws E;
}

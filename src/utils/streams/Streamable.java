package utils.streams;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import utils.streams.functions.ExSupplier;

public interface Streamable<T, E extends Exception> {

	Supplier<Stream<T>> maker();
	Class<E> classOfE();
	default Stream<T> stream() throws E {
		return ExSupplier.recheck(maker(), classOfE()).get();
	}
	default Iterable<T> iterate() throws E {
		List<T> collected = stream().collect(Collectors.toList());
		return () -> collected.iterator();
	}
}

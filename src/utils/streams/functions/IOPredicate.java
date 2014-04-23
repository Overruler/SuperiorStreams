package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOPredicate<T> extends ExPredicate<T, IOException> {}

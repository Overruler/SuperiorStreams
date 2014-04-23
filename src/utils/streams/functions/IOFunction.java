package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOFunction<T, R> extends ExFunction<T, R, IOException> {}

package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOBiConsumer<T, U> extends ExBiConsumer<T, U, IOException> {}

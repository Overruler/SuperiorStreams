package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOConsumer<T> extends ExConsumer<T, IOException> {}

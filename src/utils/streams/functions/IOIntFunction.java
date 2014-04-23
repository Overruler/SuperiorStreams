package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOIntFunction<R> extends ExIntFunction<R, IOException> {}

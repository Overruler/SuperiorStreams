package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOLongFunction<R> extends ExLongFunction<R, IOException> {}

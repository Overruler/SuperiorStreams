package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOToIntFunction<T> extends ExToIntFunction<T, IOException> {}

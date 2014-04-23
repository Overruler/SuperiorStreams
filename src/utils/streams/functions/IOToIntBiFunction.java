package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOToIntBiFunction<T, U> extends ExToIntBiFunction<T, U, IOException> {}

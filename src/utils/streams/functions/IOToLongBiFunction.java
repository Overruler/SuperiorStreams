package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOToLongBiFunction<T, U> extends ExToLongBiFunction<T, U, IOException> {}

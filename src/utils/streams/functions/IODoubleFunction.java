package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IODoubleFunction<R> extends ExDoubleFunction<R, IOException> {}

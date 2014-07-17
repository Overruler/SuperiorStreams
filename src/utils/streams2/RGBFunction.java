package utils.streams2;

@FunctionalInterface
public interface RGBFunction {
	int applyAsComponent(int alpha, int red, int green, int blue);
}

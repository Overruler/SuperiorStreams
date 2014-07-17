package utils.streams2;

@FunctionalInterface
public interface HSBFunction {
	double applyAsComponent(float hue, float saturation, float brightness, float alpha);
}

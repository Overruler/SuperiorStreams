package utils.streams;

@FunctionalInterface
public interface HSBFunction {
	double applyAsComponent(float hue, float saturation, float brightness, float alpha);
}

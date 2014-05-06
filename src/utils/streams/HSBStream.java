package utils.streams;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

//*Q*
public class HSBStream extends AbstractStream<float[], RuntimeException,
Stream<float[]>,
HSBStream,
UnIntStream,
UnLongStream,
UnDoubleStream,
Consumer<? super float[]>,
Predicate<? super float[]>,
BinaryOperator<float[]>,
Comparator<? super float[]>,
Function<? super float[], ? extends IntStream>,
Function<? super float[], ? extends LongStream>,
Function<? super float[], ? extends DoubleStream>,
ToIntFunction<? super float[]>,
ToLongFunction<? super float[]>,
ToDoubleFunction<? super float[]>> {//*E*

	private final int w;
	private final int h;

	public HSBStream(Supplier<Stream<float[]>> supplier, int w, int h) {
		super(supplier);
		this.w = w;
		this.h = h;
	}
	<OLD> HSBStream(Supplier<OLD> older, Function<OLD, Stream<float[]>> converter, int w, int h) {
		super(older, converter);
		this.w = w;
		this.h = h;
	}
	protected @Override Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	protected @Override Stream<float[]> castToStream(Stream<float[]> stream) {
		return stream;
	}
	protected @Override HSBStream asSELF(Function<Stream<float[]>, Stream<float[]>> func) {
		return new HSBStream(supplier, func, w, h);
	}
	protected @Override UnIntStream asIS(Function<Stream<float[]>, IntStream> func) {
		return new UnIntStream(supplier, func);
	}
	protected @Override UnLongStream asLS(Function<Stream<float[]>, LongStream> func) {
		return new UnLongStream(supplier, func);
	}
	protected @Override UnDoubleStream asDS(Function<Stream<float[]>, DoubleStream> func) {
		return new UnDoubleStream(supplier, func);
	}
	protected @Override Function<? super float[], ? extends IntStream> castToIntStream(
	  Function<? super float[], ? extends IntStream> mapper) {
		return mapper;
	}
	protected @Override Function<? super float[], ? extends LongStream> castToLongStream(
	  Function<? super float[], ? extends LongStream> mapper) {
		return mapper;
	}
	protected @Override Function<? super float[], ? extends DoubleStream> castToDoubleStream(
	  Function<? super float[], ? extends DoubleStream> mapper) {
		return mapper;
	}
	protected @Override ToIntFunction<? super float[]> castToInt(ToIntFunction<? super float[]> mapper) {
		return mapper;
	}
	protected @Override ToLongFunction<? super float[]> castToLong(ToLongFunction<? super float[]> mapper) {
		return mapper;
	}
	protected @Override ToDoubleFunction<? super float[]> castToDouble(ToDoubleFunction<? super float[]> mapper) {
		return mapper;
	}
	protected @Override BinaryOperator<float[]> castToBinaryOperators(BinaryOperator<float[]> combiner) {
		return combiner;
	}
	protected @Override Comparator<? super float[]> castToComparators(Comparator<? super float[]> comparator) {
		return comparator;
	}
	protected @Override Consumer<? super float[]> castToConsumers(Consumer<? super float[]> action) {
		return action;
	}
	protected @Override Predicate<? super float[]> castToPredicates(Predicate<? super float[]> allowed) {
		return allowed;
	}
	public <R> UnStream<R> map(Function<? super float[], ? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> UnStream<R> map(
	  Function<? super float[], ? extends R> mapper,
	  Predicate<float[]>... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> UnStream<R> flatMap(Function<? super float[], ? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> UnStream<R> flatMap(
	  Function<? super float[], ? extends Stream<? extends R>> mapper,
	  Predicate<float[]>... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> Map<K, ArrayList<float[]>> toMap(Function<? super float[], ? extends K> classifier) {
		return toMapInternal(castToClassifier(classifier));
	}
	public final @SafeVarargs <K> Map<K, ArrayList<float[]>> toMap(
	  Function<? super float[], ? extends K> classifier,
	  Predicate<float[]>... allowed) {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMapInternal(classifier);
		}
		return toMapInternal(classifier);
	}
	public <K, L, M> M toMultiMap(
	  Function<? super float[], ? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<ArrayList<float[]>, L> intoList) {
		return toMultiMapInternal(castToClassifier(classifier), intoMap, intoList);
	}
	public final @SafeVarargs <K, L, M> M toMultiMap(
	  Function<? super float[], ? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<ArrayList<float[]>, L> intoList,
	  Predicate<float[]>... allowed) {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMultiMapInternal(
			  classifier,
			  intoMap,
			  intoList);
		}
		return toMultiMapInternal(classifier, intoMap, intoList);
	}
	private static <K> Function<? super float[], ? extends K> castToClassifier(
	  Function<? super float[], ? extends K> classifier) {
		return classifier;
	}
	private static <R> Function<? super float[], ? extends Stream<? extends R>> castToFlatMapFunctions(
	  Function<? super float[], ? extends Stream<? extends R>> mapper) {
		return mapper;
	}
	private static <R> Function<? super float[], ? extends R> castToMapFunctions(
	  Function<? super float[], ? extends R> mapping) {
		return mapping;
	}
	private <R> Function<Function<Stream<float[]>, Stream<R>>, UnStream<R>> cast() {
		return f -> new UnStream<>(supplier, f);
	}
	public HSBStream mapColor(UnaryOperator<float[]> mapping) {
		return mapInternal(mapping, f -> new HSBStream(supplier, f, w, h));
	}
	public HSBStream mapHue(HSBFunction mapping) {
		Function<Function<Stream<float[]>, Stream<float[]>>, HSBStream> cast = f -> new HSBStream(supplier, f, w, h);
		UnaryOperator<float[]> mapping2 =
		  f -> new float[] { (float) (mapping.applyAsComponent(f[0] * 360, f[1], f[2], f[3]) / 360), f[1], f[2], f[3] };
		return mapInternal(mapping2, cast);
	}
	public HSBStream mapSaturation(HSBFunction mapping) {
		Function<Function<Stream<float[]>, Stream<float[]>>, HSBStream> cast = f -> new HSBStream(supplier, f, w, h);
		UnaryOperator<float[]> mapping2 =
		  f -> new float[] { f[0], (float) mapping.applyAsComponent(f[0], f[1], f[2], f[3]), f[2], f[3] };
		return mapInternal(mapping2, cast);
	}
	public HSBStream mapBrightness(HSBFunction mapping) {
		Function<Function<Stream<float[]>, Stream<float[]>>, HSBStream> cast = f -> new HSBStream(supplier, f, w, h);
		UnaryOperator<float[]> mapping2 =
		  f -> new float[] { f[0], f[1], (float) mapping.applyAsComponent(f[0], f[1], f[2], f[3]), f[3] };
		return mapInternal(mapping2, cast);
	}
	public HSBStream mapAlpha(HSBFunction mapping) {
		Function<Function<Stream<float[]>, Stream<float[]>>, HSBStream> cast = f -> new HSBStream(supplier, f, w, h);
		UnaryOperator<float[]> mapping2 =
		  f -> new float[] { f[0], f[1], f[2], (float) mapping.applyAsComponent(f[0], f[1], f[2], f[3]) };
		return mapInternal(mapping2, cast);
	}
	public RGBStream toRGB() {
		ToIntFunction<? super float[]> mapper = i -> toARGB(i);
		return new RGBStream(supplier, s -> s.mapToInt(mapper), w, h);
	}
	private static int toARGB(float[] i) {
		return toRGBComponent(i[3]) << 24 | Color.HSBtoRGB(i[0], clamp(i[1]), clamp(i[2]));
	}
	public RGBStream gammaCompress(double gamma) {
		return new RGBStream(supplier, s -> s.mapToInt(i -> toARGB(i, gamma)), w, h);
	}
	private static int toARGB(float[] hsba, double gamma) {
		float hue = hsba[0];
		float saturation = hsba[1];
		float brightness = hsba[2];
		int alpha = (int) (hsba[3] * 255.0f + 0.5f) << 24;
		return hsbToRGB(hue, saturation, brightness, gamma, alpha);
	}
	private static int hsbToRGB(float hue, float saturation, float brightness, double gamma, int alpha) {
		int r = 0, g = 0, b = 0;
		if(saturation == 0) {
			r = g = b = (int) (gamma(brightness, gamma) * 255.0f + 0.5f);
		} else {
			float h = (hue - (float) Math.floor(hue)) * 6.0f;
			float f = h - (float) java.lang.Math.floor(h);
			float p = brightness * (1.0f - saturation);
			float q = brightness * (1.0f - saturation * f);
			float t = brightness * (1.0f - saturation * (1.0f - f));
			switch((int) h) {
				case 0:
					r = (int) (gamma(brightness, gamma) * 255.0f + 0.5f);
					g = (int) (gamma(t, gamma) * 255.0f + 0.5f);
					b = (int) (gamma(p, gamma) * 255.0f + 0.5f);
					break;
				case 1:
					r = (int) (gamma(q, gamma) * 255.0f + 0.5f);
					g = (int) (gamma(brightness, gamma) * 255.0f + 0.5f);
					b = (int) (gamma(p, gamma) * 255.0f + 0.5f);
					break;
				case 2:
					r = (int) (gamma(p, gamma) * 255.0f + 0.5f);
					g = (int) (gamma(brightness, gamma) * 255.0f + 0.5f);
					b = (int) (gamma(t, gamma) * 255.0f + 0.5f);
					break;
				case 3:
					r = (int) (gamma(p, gamma) * 255.0f + 0.5f);
					g = (int) (gamma(q, gamma) * 255.0f + 0.5f);
					b = (int) (gamma(brightness, gamma) * 255.0f + 0.5f);
					break;
				case 4:
					r = (int) (gamma(t, gamma) * 255.0f + 0.5f);
					g = (int) (gamma(p, gamma) * 255.0f + 0.5f);
					b = (int) (gamma(brightness, gamma) * 255.0f + 0.5f);
					break;
				case 5:
					r = (int) (gamma(brightness, gamma) * 255.0f + 0.5f);
					g = (int) (gamma(p, gamma) * 255.0f + 0.5f);
					b = (int) (gamma(q, gamma) * 255.0f + 0.5f);
					break;
			}
		}
		return alpha | r << 16 | g << 8 | b << 0;
	}
	private static float gamma(float f, double gamma) {
		return (float) Math.pow(f, gamma);
	}
	private static int toRGBComponent(float f) {
		return Math.round(clamp(f) * 255);
	}
	private static float clamp(float f) {
		return Math.max(Math.min(1, f), 0);
	}
	public BufferedImage toImage() {
		return toRGB().toImage();
	}
	public void save(String path) throws IOException {
		toRGB().save(path);
	}
	public void save(Path path) throws IOException {
		toRGB().save(path);
	}
}

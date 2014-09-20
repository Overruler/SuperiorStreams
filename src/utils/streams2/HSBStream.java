package utils.streams2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import utils.lists.ArrayList;
import utils.lists.Arrays;
import utils.lists.HashMap;
import utils.streams.functions.BinaryOperator;
import utils.streams.functions.Consumer;
import utils.streams.functions.Function;
import utils.streams.functions.Predicate;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToDoubleFunction;
import utils.streams.functions.ToIntFunction;
import utils.streams.functions.ToLongFunction;
import utils.streams.functions.UnaryOperator;

//*Q*
public class HSBStream extends AbstractStream<float[], RuntimeException,
java.util.stream.Stream<float[]>,
HSBStream,
IntStream,
LongStream,
DoubleStream,
Consumer<? super float[]>,
Predicate<? super float[]>,
BinaryOperator<float[]>,
Comparator<? super float[]>,
Function<? super float[], ? extends java.util.stream.IntStream>,
Function<? super float[], ? extends java.util.stream.LongStream>,
Function<? super float[], ? extends java.util.stream.DoubleStream>,
ToIntFunction<? super float[]>,
ToLongFunction<? super float[]>,
ToDoubleFunction<? super float[]>> {//*E*
	private final int w;
	private final int h;

	public HSBStream(Supplier<java.util.stream.Stream<float[]>> supplier, int w, int h) {
		super(supplier);
		this.w = w;
		this.h = h;
	}
	<OLD> HSBStream(Supplier<OLD> older, Function<OLD, java.util.stream.Stream<float[]>> converter, int w, int h) {
		super(older, converter);
		this.w = w;
		this.h = h;
	}
	protected @Override Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	protected @Override java.util.stream.Stream<float[]> castToStream(java.util.stream.Stream<float[]> stream) {
		return stream;
	}
	protected @Override HSBStream asSELF(
		Function<java.util.stream.Stream<float[]>, java.util.stream.Stream<float[]>> func) {
		return new HSBStream(supplier, func, w, h);
	}
	protected @Override IntStream asIS(Function<java.util.stream.Stream<float[]>, java.util.stream.IntStream> func) {
		return new IntStream(supplier, func);
	}
	protected @Override LongStream asLS(Function<java.util.stream.Stream<float[]>, java.util.stream.LongStream> func) {
		return new LongStream(supplier, func);
	}
	protected @Override DoubleStream
		asDS(Function<java.util.stream.Stream<float[]>, java.util.stream.DoubleStream> func) {
		return new DoubleStream(supplier, func);
	}
	protected @Override Function<? super float[], ? extends java.util.stream.IntStream> castToIntStream(
		Function<? super float[], ? extends java.util.stream.IntStream> mapper) {
		return mapper;
	}
	protected @Override Function<? super float[], ? extends java.util.stream.LongStream> castToLongStream(
		Function<? super float[], ? extends java.util.stream.LongStream> mapper) {
		return mapper;
	}
	protected @Override Function<? super float[], ? extends java.util.stream.DoubleStream> castToDoubleStream(
		Function<? super float[], ? extends java.util.stream.DoubleStream> mapper) {
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
	public <R> Stream<R> map(Function<? super float[], ? extends R> mapper) {
		return mapInternal(mapper, cast());
	}
	public final @SafeVarargs <R> Stream<R> map(
		Function<? super float[], ? extends R> mapper,
		Predicate<? super float[]>... allowed) {
		if(allowed != null && allowed.length > 0) {
			HSBStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(mapper, stream.cast());
		}
		return mapInternal(mapper, cast());
	}
	public <R> Stream<R> flatMap(Function<? super float[], ? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> Stream<R> flatMap(
		Function<? super float[], ? extends Stream<? extends R>> mapper,
		Predicate<? super float[]>... allowed) {
		if(allowed != null && allowed.length > 0) {
			HSBStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, ArrayList<float[]>> toMap(Function<? super float[], ? extends K> classifier) {
		return toMapInternal(castToClassifier(classifier));
	}
	public final @SafeVarargs <K> HashMap<K, ArrayList<float[]>> toMap(
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
	private static <R> Function<? super float[], ? extends java.util.stream.Stream<? extends R>>
		castToFlatMapFunctions(Function<? super float[], ? extends Stream<? extends R>> mapper) {
		return t -> mapper.apply(t).maker().get();
	}
	private <R> Function<Function<java.util.stream.Stream<float[]>, java.util.stream.Stream<R>>, Stream<R>> cast() {
		return f -> new Stream<>(supplier, f);
	}
	public HSBStream mapColor(UnaryOperator<float[]> mapping) {
		return mapInternal(f -> compressHue(mapping.apply(expandHue(f))), f -> new HSBStream(supplier, f, w, h));
	}
	private static float[] compressHue(float[] hsba) {
		hsba[0] /= 360;
		return hsba;
	}
	private static float[] expandHue(float[] hsba) {
		hsba[0] *= 360;
		return hsba;
	}
	public HSBStream mapHue(HSBFunction mapping) {
		Function<Function<java.util.stream.Stream<float[]>, java.util.stream.Stream<float[]>>, HSBStream> cast =
			f -> new HSBStream(supplier, f, w, h);
		UnaryOperator<float[]> mapping2 =
			f -> new float[] { (float) (mapping.applyAsComponent(f[0] * 360, f[1], f[2], f[3]) / 360), f[1], f[2], f[3] };
		return mapInternal(mapping2, cast);
	}
	public HSBStream mapSaturation(HSBFunction mapping) {
		Function<Function<java.util.stream.Stream<float[]>, java.util.stream.Stream<float[]>>, HSBStream> cast =
			f -> new HSBStream(supplier, f, w, h);
		UnaryOperator<float[]> mapping2 =
			f -> new float[] { f[0], (float) mapping.applyAsComponent(f[0], f[1], f[2], f[3]), f[2], f[3] };
		return mapInternal(mapping2, cast);
	}
	public HSBStream mapBrightness(HSBFunction mapping) {
		Function<Function<java.util.stream.Stream<float[]>, java.util.stream.Stream<float[]>>, HSBStream> cast =
			f -> new HSBStream(supplier, f, w, h);
		UnaryOperator<float[]> mapping2 =
			f -> new float[] { f[0], f[1], (float) mapping.applyAsComponent(f[0], f[1], f[2], f[3]), f[3] };
		return mapInternal(mapping2, cast);
	}
	public HSBStream mapAlpha(HSBFunction mapping) {
		Function<Function<java.util.stream.Stream<float[]>, java.util.stream.Stream<float[]>>, HSBStream> cast =
			f -> new HSBStream(supplier, f, w, h);
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

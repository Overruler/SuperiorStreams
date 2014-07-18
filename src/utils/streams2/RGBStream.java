package utils.streams2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import utils.lists2.Arrays;
import utils.lists2.HashMap;
import utils.streams.functions.Function;
import utils.streams.functions.IntBinaryOperator;
import utils.streams.functions.IntConsumer;
import utils.streams.functions.IntFunction;
import utils.streams.functions.IntPredicate;
import utils.streams.functions.IntToDoubleFunction;
import utils.streams.functions.IntToLongFunction;
import utils.streams.functions.IntUnaryOperator;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToDoubleFunction;
import utils.streams.functions.ToIntFunction;
import utils.streams.functions.ToLongFunction;

//*Q*
public final class RGBStream extends AbstractIntStream<RuntimeException,
java.util.stream.IntStream,
Stream<Integer>,
RGBStream,
LongStream,
DoubleStream,
IntConsumer,
IntPredicate,
IntBinaryOperator,
IntFunction<? extends java.util.stream.IntStream>,
IntFunction<? extends java.util.stream.LongStream>,
IntFunction<? extends java.util.stream.DoubleStream>,
ToIntFunction<Integer>,
ToLongFunction<Integer>,
ToDoubleFunction<Integer>> {//*E*
	static final double GAMMA = 1.0;
	private final int w;
	private final int h;

	public RGBStream(Supplier<java.util.stream.IntStream> supplier, int w, int h) {
		super(supplier);
		this.w = w;
		this.h = h;
	}
	<OLD> RGBStream(Supplier<OLD> older, Function<OLD, java.util.stream.IntStream> converter, int w, int h) {
		this(() -> converter.apply(older.get()), w, h);
	}
	protected @Override Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	protected @Override java.util.stream.IntStream castToStream(java.util.stream.IntStream stream) {
		return stream;
	}
	protected @Override Stream<Integer>
		asOS(Function<java.util.stream.IntStream, java.util.stream.Stream<Integer>> func) {
		return new Stream<>(supplier, func);
	}
	protected @Override RGBStream asSELF(Function<java.util.stream.IntStream, java.util.stream.IntStream> func) {
		return new RGBStream(supplier, func, w, h);
	}
	protected @Override LongStream asLS(Function<java.util.stream.IntStream, java.util.stream.LongStream> func) {
		return new LongStream(supplier, func);
	}
	protected @Override DoubleStream asDS(Function<java.util.stream.IntStream, java.util.stream.DoubleStream> func) {
		return new DoubleStream(supplier, func);
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.IntStream> castToIntStream(
		IntFunction<? extends java.util.stream.IntStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.LongStream> castToLongStream(
		IntFunction<? extends java.util.stream.LongStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		IntFunction<? extends java.util.stream.DoubleStream> mapper) {
		return mapper::apply;
	}
	protected @Override IntUnaryOperator castToInt(ToIntFunction<Integer> mapper) {
		return mapper::applyAsInt;
	}
	protected @Override IntToLongFunction castToLong(ToLongFunction<Integer> mapper) {
		return mapper::applyAsLong;
	}
	protected @Override IntToDoubleFunction castToDouble(ToDoubleFunction<Integer> mapper) {
		return mapper::applyAsDouble;
	}
	protected @Override IntBinaryOperator castToBinaryOperators(IntBinaryOperator combiner) {
		return combiner;
	}
	protected @Override IntConsumer castToConsumers(IntConsumer action) {
		return action;
	}
	protected @Override IntPredicate castToPredicates(IntPredicate test) {
		return test;
	}
	public <R> Stream<R> map(IntFunction<? extends R> mapper) {
		return mapInternal(mapper, cast());
	}
	public final @SafeVarargs <R> Stream<R> map(IntFunction<? extends R> mapper, IntPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RGBStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(mapper, stream.cast());
		}
		return mapInternal(mapper, cast());
	}
	public <R> Stream<R> flatMap(IntFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> Stream<R> flatMap(
		IntFunction<? extends Stream<? extends R>> mapper,
		IntPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RGBStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, int[]> toMap(IntFunction<? extends K> classifier) {
		return toMapInternal(classifier, castToClassifier());
	}
	public final @SafeVarargs <K> HashMap<K, int[]> toMap(IntFunction<? extends K> classifier, IntPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMapInternal(
				classifier,
				castToClassifier());
		}
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
		IntFunction<? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<int[], L> intoList) {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private static <K> Function<IntFunction<? extends K>, IntFunction<? extends K>> castToClassifier() {
		return c -> c;
	}
	private static <R> IntFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		IntFunction<? extends Stream<? extends R>> mapper) {
		return t -> mapper.apply(t).maker().get();
	}
	private <R> Function<Function<java.util.stream.IntStream, java.util.stream.Stream<R>>, Stream<R>> cast() {
		return f -> new Stream<>(supplier, f);
	}
	public RGBStream mapColor(IntUnaryOperator mapping) {
		return asSELF(s -> s.map(mapping));
	}
	public RGBStream mapAlpha(RGBFunction mapping) {
		return asSELF(s -> s.map(i -> separate(mapping, i) << 24 | i & 0x00FFFFFF));
	}
	public RGBStream mapRed(RGBFunction mapping) {
		return asSELF(s -> s.map(i -> separate(mapping, i) << 16 | i & 0xFF00FFFF));
	}
	public RGBStream mapGreen(RGBFunction mapping) {
		IntUnaryOperator mapper = i -> separate(mapping, i) << 8 | i & 0xFFFF00FF;
		return asSELF(s -> s.map(mapper));
	}
	public RGBStream mapBlue(RGBFunction mapping) {
		return asSELF(s -> s.map(i -> separate(mapping, i) | i & 0xFFFFFF00));
	}
	private static int separate(RGBFunction mapping, int i) {
		return clamp(mapping.applyAsComponent(i >> 24 & 0xFF, i >> 16 & 0xFF, i >> 8 & 0xFF, i & 0xFF));
	}
	public RGBStream setAlpha(int value) {
		return asSELF(s -> s.map(i -> i & 0x00FFFFFF | value << 24 & 0xFF000000));
	}
	public RGBStream setRed(int value) {
		return asSELF(s -> s.map(i -> i & 0xFF00FFFF | value << 16 & 0x00FF0000));
	}
	public RGBStream setGreen(int value) {
		return asSELF(s -> s.map(i -> i & 0xFFFF00FF | value << 8 & 0x0000FF00));
	}
	public RGBStream setBlue(int value) {
		return asSELF(s -> s.map(i -> i & 0xFFFFFF00 | value & 0x000000FF));
	}
	public RGBStream swapAlphaAndRed() {
		return asSELF(s -> s.map(i -> i & 0x0000FFFF | i >> 8 & 0x00FF0000 | i << 8 & 0xFF000000));
	}
	public RGBStream swapAlphaAndGreen() {
		return asSELF(s -> s.map(i -> i & 0x00FF00FF | i >> 16 & 0x0000FF00 | i << 16 & 0xFF000000));
	}
	public RGBStream swapAlphaAndBlue() {
		return asSELF(s -> s.map(i -> i & 0x00FFFF00 | i >> 24 & 0x000000FF | i << 24 & 0xFF000000));
	}
	public RGBStream swapRedAndGreen() {
		return asSELF(s -> s.map(i -> i & 0xFF0000FF | i >> 8 & 0x0000FF00 | i << 8 & 0x00FF0000));
	}
	public RGBStream swapRedAndBlue() {
		return asSELF(s -> s.map(i -> i & 0xFF00FF00 | i >> 16 & 0x000000FF | i << 16 & 0x00FF0000));
	}
	public RGBStream swapGreenAndBlue() {
		return asSELF(s -> s.map(i -> i & 0xFFFF0000 | i >> 8 & 0x000000FF | i << 8 & 0x0000FF00));
	}
	public HSBStream toHSB() {
		return gammaExpand(GAMMA);
	}
	public HSBStream gammaExpand(double gamma) {
		Function<Function<java.util.stream.IntStream, java.util.stream.Stream<float[]>>, HSBStream> cast =
			f -> new HSBStream(supplier, f, w, h);
		return mapInternal(i -> toLinearHSBA(i, gamma), cast);
	}
	private static float[] toLinearHSBA(int i, double gamma) {
		int linear = toLinear(i, gamma);
		float[] hsba = toHSBA(linear);
		return hsba;
	}
	private static float[] toHSBA(int argb) {
		float[] hsb = Color.RGBtoHSB(argb >> 16 & 0xFF, argb >> 8 & 0xFF, argb & 0xFF, new float[4]);
		hsb[3] = toFloat(argb >> 24);
		return hsb;
	}
	private static int toLinear(int i, double gamma) {
		float[] rgb = toFloatComponents(i);
		float[] fromRGB = new float[] { gamma(rgb[0], gamma), gamma(rgb[1], gamma), gamma(rgb[2], gamma) };
		return toRGB(fromRGB) | i & 0xFF000000;
	}
	private static float gamma(float a, double gamma) {
		return (float) Math.pow(a, 1.0 / gamma);
	}
	private static int toRGB(float[] fromRGB) {
		return toIntComponent(fromRGB[0]) << 16 | toIntComponent(fromRGB[1]) << 8 | toIntComponent(fromRGB[2]);
	}
	private static int toIntComponent(float f) {
		return clamp(Math.round(f * 255));
	}
	private static float[] toFloatComponents(int i) {
		return new float[] { toFloat(i >> 16), toFloat(i >> 8), toFloat(i) };
	}
	private static float toFloat(int i) {
		return (i & 0xFF) / 255.0f;
	}
	public HSBStream toBlackAndWhite() {
		Function<Function<java.util.stream.IntStream, java.util.stream.Stream<float[]>>, HSBStream> cast =
			f -> new HSBStream(supplier, f, w, h);
		return mapInternal(i -> toBW(i), cast);
	}
	private static float[] toBW(int argb) {
		int r = argb >> 16 & 0xFF;
		int g = argb >> 8 & 0xFF;
		int b = argb & 0xFF;
		int y = (2126 * r + 7152 * g + 722 * b) / 255;
		return new float[] { 0, 0, y / 10000f, toFloat(argb >> 24) };
	}
	private static int clamp(int component) {
		return Math.max(Math.min(255, component), 0);
	}
	public BufferedImage toImage() {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, w, h, toArray(), 0, w);
		return image;
	}
	public void save(String path) throws IOException {
		save(Paths.get(path));
	}
	public void save(Path path) throws IOException {
		String formatName = path.getFileName().toString().replaceAll("[^.]*\\.", "").toLowerCase(Locale.ENGLISH);
		if("jpeg".equals(formatName) || "jpg".equals(formatName)) {
			ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
			if(writer == null) {
				throw new IOException("Writer not found for " + path);
			}
			ImageWriteParam p = writer.getDefaultWriteParam();
			p.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			p.setCompressionQuality(1);
			try(FileImageOutputStream output = new FileImageOutputStream(path.toFile())) {
				writer.setOutput(output);
				BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				image.setRGB(0, 0, w, h, toArray(), 0, w);
				writer.write(null, new IIOImage(image, null, null), p);
			} finally {
				writer.dispose();
			}
		} else {
			if(ImageIO.write(toImage(), formatName, path.toFile()) == false) {
				throw new IOException("Writer not found for " + path);
			}
		}
	}
}
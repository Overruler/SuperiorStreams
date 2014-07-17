package utils.streams2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Spliterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collector;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import utils.lists.Pair;
import utils.lists2.ArrayList;
import utils.lists2.HashMap;
import utils.lists2.HashSet;
import utils.lists2.List;
import utils.lists2.Map;
import utils.lists2.Set;
import utils.streams.functions.BiPredicate;
import utils.streams.functions.BinaryOperator;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.Function;
import utils.streams.functions.IOBiPredicate;
import utils.streams.functions.IOConsumer;
import utils.streams.functions.IOFunction;
import utils.streams.functions.IOSupplier;
import utils.streams.functions.Predicate;
import utils.streams.functions.Supplier;

public class Streams {
	public static @SafeVarargs <T> Stream<T> of(T... array) {
		return from(java.util.Arrays.asList(array.clone()));
	}
	public static <T> Stream<T> iterating(Iterable<T> iterable) {
		return new Stream<>(() -> iterableToStream(iterable));
	}
	private static <T> java.util.stream.Stream<T> iterableToStream(Iterable<T> iterable) {
		Spliterator<T> spliterator = iterable.spliterator();
		return StreamSupport.stream(() -> spliterator, spliterator.characteristics(), false);
	}
	public static IntStream ints(int... array) {
		int[] clone = array.clone();
		return new IntStream(() -> java.util.stream.IntStream.of(clone));
	}
	public static LongStream longs(long... array) {
		long[] clone = array.clone();
		return new LongStream(() -> java.util.stream.LongStream.of(clone));
	}
	public static DoubleStream doubles(double... array) {
		double[] clone = array.clone();
		return new DoubleStream(() -> java.util.stream.DoubleStream.of(clone));
	}
	public static <T> Stream<T> from(java.util.Collection<T> collection) {
		return new Stream<>(() -> collection.stream());
	}
	public static IntStream fromCodepoints(String string) {
		return new IntStream(() -> string.codePoints());
	}
	public static <P, T> IOStream<T> from(IOFunction<P, java.util.stream.Stream<T>> changer, P parameter) {
		Function<P, java.util.stream.Stream<T>> function = changer.uncheck(IOException.class);
		return new IOStream<>(() -> function.apply(parameter));
	}
	public static <T> IOStream<T> from(IOSupplier<java.util.stream.Stream<T>> source) {
		return with(source);
	}
	public static <A extends AutoCloseable, T> RioStream<A, T> from(
		File file,
		IOFunction<File, A> resourceAllocator,
		IOFunction<A, java.util.stream.Stream<T>> streamSupplier) {
		IOSupplier<A> allocator = () -> resourceAllocator.apply(file);
		Function<A, java.util.stream.Stream<T>> converter = streamSupplier.uncheck(IOException.class);
		ExConsumer<A, Exception> releaser = a -> a.close();
		return new RioStream<>(allocator.uncheck(IOException.class), converter, releaser.uncheck(Exception.class));
	}
	public static RioStream<JarFile, JarEntry> jarFile(String jar) {
		return jarFile(Paths.get(jar));
	}
	public static RioStream<JarFile, JarEntry> jarFile(Path jar) {
		return jarFile(jar.toFile());
	}
	public static RioStream<JarFile, JarEntry> jarFile(File jar) {
		IOSupplier<JarFile> allocator = () -> openJar(jar);
		Function<JarFile, java.util.stream.Stream<JarEntry>> converter = f -> f.stream();
		IOConsumer<JarFile> releaser = f -> f.close();
		return new RioStream<>(allocator.uncheck(IOException.class), converter, releaser.uncheck(IOException.class));
	}
	public static RioStream<ZipFile, ZipEntry> zipFile(String zip) {
		return zipFile(Paths.get(zip));
	}
	public static RioStream<ZipFile, ZipEntry> zipFile(Path zip) {
		return zipFile(zip.toFile());
	}
	public static RioStream<ZipFile, ZipEntry> zipFile(File zip) {
		IOSupplier<ZipFile> allocator = () -> openZip(zip);
		Function<ZipFile, java.util.stream.Stream<ZipEntry>> converter = f -> f.stream().map(Function.identity());
		IOConsumer<ZipFile> releaser = f -> f.close();
		return new RioStream<>(allocator.uncheck(IOException.class), converter, releaser.uncheck(IOException.class));
	}
	private static JarFile openJar(File jar) throws IOException {
		try {
			return new JarFile(jar);
		} catch(ZipException e) {
			throw addDetail(e, ": " + jar);
		}
	}
	private static ZipFile openZip(File zip) throws IOException {
		try {
			return new ZipFile(zip);
		} catch(ZipException e) {
			throw addDetail(e, ": " + zip);
		}
	}
	private static ZipException addDetail(ZipException exception, String extraDetail) throws ZipException {
		try {
			Field field = Throwable.class.getDeclaredField("detailMessage");
			boolean inaccessible = field.isAccessible() == false;
			try {
				if(inaccessible) {
					field.setAccessible(true);
				}
				String detailMessage = (String) field.get(exception);
				if(detailMessage != null) {
					detailMessage = detailMessage + extraDetail;
					field.set(exception, detailMessage);
				}
			} finally {
				if(inaccessible) {
					field.setAccessible(false);
				}
			}
		} catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ignored) {}
		throw exception;
	}
	public static IOStream<Path> filesList(String name) {
		return filesList(Paths.get(name));
	}
	public static IOStream<Path> filesList(Path path) {
		return with(() -> Files.list(path));
	}
	public static IOStream<Path> filesWalk(String name) {
		return filesWalk(Paths.get(name));
	}
	public static IOStream<Path> filesWalk(Path path) {
		return with(() -> Files.walk(path));
	}
	public static IOStream<Path> filesFind(String name, int maxDepth, IOBiPredicate<Path, BasicFileAttributes> matcher) {
		return filesFind(Paths.get(name), maxDepth, matcher);
	}
	public static IOStream<Path> filesFind(Path path, int maxDepth, IOBiPredicate<Path, BasicFileAttributes> matcher) {
		return with(() -> Files.find(path, maxDepth, matcher.uncheck(IOException.class)));
	}
	public static IOStream<String> linesInFile(String name) {
		return linesInFile(Paths.get(name));
	}
	public static IOStream<String> linesInFile(Path path) {
		return with(() -> Files.lines(path));
	}
	private static <R> IOStream<R> with(IOSupplier<java.util.stream.Stream<R>> maker) {
		return new IOStream<>(maker.uncheck(IOException.class));
	}
	public static <E> ArrayList<E> where(List<E> list, Predicate<E> pass) {
		Supplier<ArrayList<E>> collectionFactory = ArrayList::new;
		Collector<E, ArrayList<E>, ArrayList<E>> collector = Collectors.toCollection(collectionFactory);
		return list.stream().filter(pass).collect(collector);
	}
	public static <E> HashSet<E> where(Set<E> set, Predicate<E> pass) {
		Supplier<HashSet<E>> collectionFactory = HashSet::new;
		Collector<E, HashSet<E>, HashSet<E>> collector = Collectors.toCollection(collectionFactory);
		return set.stream().filter(pass).collect(collector);
	}
	public static <K, V> HashMap<K, V> where(Map<K, V> map, BiPredicate<K, V> pass) {
		Predicate<? super Pair<K, V>> allowed = e -> pass.test(e.lhs, e.rhs);
		Function<? super Pair<K, V>, ? extends K> keyMapper = e -> e.lhs;
		Function<? super Pair<K, V>, ? extends V> function = e -> e.rhs;
		BinaryOperator<V> mergeFunction = (l, r) -> l;
		Supplier<HashMap<K, V>> mapSupplier = HashMap::new;
		Collector<Pair<K, V>, HashMap<K, V>, HashMap<K, V>> collector =
			Collectors.toMap(keyMapper, function, mergeFunction, mapSupplier);
		return map.entrySet().stream().filter(allowed).collect(collector);
	}
	public static HSBStream loadImageInHSB(String path) throws IOException {
		return loadImageInRGB(path).toHSB();
	}
	public static HSBStream loadImageInHSB(Path path) throws IOException {
		return loadImageInRGB(path).toHSB();
	}
	public static RGBStream loadImageInRGB(String path) throws IOException {
		return loadImageInRGB(Paths.get(path));
	}
	public static RGBStream loadImageInRGB(Path path) throws IOException {
		BufferedImage image = ImageIO.read(path.toFile());
		if(image == null) {
			throw new IOException("No ImageReader could read file " + path);
		}
		return imageToRGB(image);
	}
	public static HSBStream imageToHSB(BufferedImage image) {
		return imageToRGB(image).toHSB();
	}
	public static RGBStream imageToRGB(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		int[] data = new int[w * h];
		image.getRGB(0, 0, w, h, data, 0, w);
		return new RGBStream(() -> java.util.stream.IntStream.of(data), w, h);
	}
}

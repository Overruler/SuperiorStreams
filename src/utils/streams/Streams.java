package utils.streams;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.IOConsumer;
import utils.streams.functions.IOFunction;
import utils.streams.functions.IOSupplier;

public class Streams {

	public static @SafeVarargs <T> UnStream<T> of(T... array) {
		return from(Arrays.asList(array.clone()));
	}
	public static UnIntStream ints(int... array) {
		int[] clone = array.clone();
		return new UnIntStream(() -> IntStream.of(clone));
	}
	public static UnLongStream longs(long... array) {
		long[] clone = array.clone();
		return new UnLongStream(() -> LongStream.of(clone));
	}
	public static UnDoubleStream doubles(double... array) {
		double[] clone = array.clone();
		return new UnDoubleStream(() -> DoubleStream.of(clone));
	}
	public static <T> UnStream<T> from(Collection<T> collection) {
		return new UnStream<>(() -> collection.stream());
	}
	public static UnIntStream fromCodepoints(String string) {
		return new UnIntStream(() -> string.codePoints());
	}
	public static <P, T> IOStream<T> from(IOFunction<P, Stream<T>> changer, P parameter) {
		Function<P, Stream<T>> function = changer.uncheck(IOException.class);
		return new IOStream<>(() -> function.apply(parameter));
	}
	public static <T> IOStream<T> from(IOSupplier<Stream<T>> source) {
		return with(source);
	}
	public static <A extends AutoCloseable, T> RioStream<A, T> from(
	  File file,
	  IOFunction<File, A> resourceAllocator,
	  IOFunction<A, Stream<T>> streamSupplier) {
		IOSupplier<A> allocator = () -> resourceAllocator.apply(file);
		Function<A, Stream<T>> converter = streamSupplier.uncheck(IOException.class);
		ExConsumer<A, Exception> releaser = a -> a.close();
		return new RioStream<>(allocator.uncheck(IOException.class), converter, releaser.uncheck(Exception.class));
	}
	public static RioStream<JarFile, JarEntry> jarFile(File jar) {
		IOSupplier<JarFile> allocator = () -> new JarFile(jar);
		Function<JarFile, Stream<JarEntry>> converter = f -> f.stream();
		IOConsumer<JarFile> releaser = f -> f.close();
		return new RioStream<>(allocator.uncheck(IOException.class), converter, releaser.uncheck(IOException.class));
	}
	public static RioStream<ZipFile, ZipEntry> zipFile(File zip) {
		IOSupplier<ZipFile> allocator = () -> new ZipFile(zip);
		Function<ZipFile, Stream<ZipEntry>> converter = f -> f.stream().map(Function.identity());
		IOConsumer<ZipFile> releaser = f -> f.close();
		return new RioStream<>(allocator.uncheck(IOException.class), converter, releaser.uncheck(IOException.class));
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
	public static IOStream<String> linesInFile(String name) {
		return linesInFile(Paths.get(name));
	}
	public static IOStream<String> linesInFile(Path path) {
		return with(() -> Files.lines(path));
	}
	static <R> IOStream<R> with(IOSupplier<Stream<R>> maker) {
		return new IOStream<>(maker.uncheck(IOException.class));
	}
	public static <E> ArrayList<E> where(List<E> list, Predicate<E> pass) {
		return list.stream().filter(pass).collect(Collectors.toCollection(ArrayList::new));
	}
	public static <E> HashSet<E> where(Set<E> set, Predicate<E> pass) {
		return set.stream().filter(pass).collect(Collectors.toCollection(HashSet::new));
	}
	public static <K, V> HashMap<K, V> where(Map<K, V> map, BiPredicate<K, V> pass) {
		return map.entrySet().stream().filter(e -> pass.test(e.getKey(), e.getValue())).collect(
		  Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (l, r) -> l, HashMap::new));
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
		return new RGBStream(() -> IntStream.of(data), w, h);
	}
}

package readme;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import utils.streams.HSBStream;
import utils.streams.IOStream;
import utils.streams.RGBStream;
import utils.streams.Stream2;
import utils.streams.Streams;
import utils.streams.functions.IOFunction;

//*Q*
public class ReadmeMd {
/*
SuperiorStreams
===============

It is possible to have your cake and eat it too.

The Streams API in Java 8 doesn't handle exceptions or resources properly. Superior Streams are a wrapper around the JDK Streams API that adds proper exception passing and resource management without complicating the API.

## Superior examples

Lambda expressions throwing IOExceptions are just as simple to use as those throwing unchecked exceptions:

```java
/*/
static Object[] example1(IOStream<File> s3, Stream<File> s33) {

	IOFunction<File, JarFile> toJarFile1 = JarFile::new;
	IOStream<JarFile> s4 = s3.map(toJarFile1);

	// Using JDK Streams requires twisting and turning:

	Function<File, JarFile> toJarFile2 = (File file) -> {
		try {
			return new JarFile(file);
		} catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	};
	Stream<JarFile> s43 = s33.map(toJarFile2);

return new Object[] {s4,s43}; }
/*/
```

Resources are released as soon as they are no longer needed, automatically and transparently:

```java
/*/
static void example2(Path install, Predicate<Path> isJdk) throws IOException {

	IOStream<Path> s1 = Streams.filesList(install);
	IOStream<Path> s2 = s1.filter(isJdk);
	for(Path path : s1.iterable()) {
		System.out.println(path);
	}
	for(Path path : s2.iterable()) {
		System.out.println(path);
	}

	// JDK Streams need to be manually tracked at each use site:

	try(Stream<Path> s11 = Files.list(install);
		Stream<Path> s12 = Files.list(install);) {

		Stream<Path> s22 = s12.filter(isJdk);
		for(Path path : (Iterable<Path>) () -> s11.iterator()) {
			System.out.println(path);
		}
		for(Path path : (Iterable<Path>) () -> s22.iterator()) {
			System.out.println(path);
		}
	}
}
/*/
```

Superior Streams can be freely reused as many times as needed:

```java
/*/
static Object[] example3(List<String> JDK_PROJECTS) {

	Stream2<String> projects1 = Streams.from(JDK_PROJECTS);
	Predicate<String> isPrefix1 = (String path) -> projects1.anyMatch(path::startsWith);

	// Each Stream from JDK can only be used once or the result is an exception at runtime:

	Supplier<Stream<String>> projects2 = () -> JDK_PROJECTS.stream();
	Predicate<String> isPrefix2 = (String path) -> projects2.get().anyMatch(path::startsWith);

return new Object[] {isPrefix1,isPrefix2}; }
/*/
```

## New Features

Helper methods for WHERE clauses over Lists, Maps and Sets:

```java
/*/
static ArrayList<String> removeBadWords(List<String> list) {
  return Streams.where(list, s -> s.contains("fuck") == false);
}
/*/
```

Streams of RGB pixels for color manipulation using red, green, blue channels:

```java
/*/
static void example4() throws IOException {
	RGBStream stream = Streams.loadImageInRGB("pagoda.jpg");
	stream.swapRedAndGreen().save("pagoda_rg.jpg");
	stream.swapRedAndBlue().save("pagoda_rb.jpg");
	stream.swapAlphaAndBlue().save("pagoda_ab.png");
	stream.setRed(255).save("pagoda_bright_red.png");
}
/*/
```

Image color manipulation in HSB changing hue, saturation and brightness values:

```java
/*/
static void example5() throws IOException {
	HSBStream hsb = Streams.loadImageInHSB("pagoda.jpg");
	hsb.mapBrightness((h, s, b, a) -> b * 0.25).save("pagoda_dark.jpg");
	hsb.mapHue((h, s, b, a) -> h + 10).save("pagoda_altered_hue.jpg");
}
/*/
```

Gamma adjustments:

```java
/*/
static void example6(RGBStream stream) throws IOException {
	stream.gammaExpand(2.4).save("pagoda_texture.jpg");
	HSBStream bw = stream.toBlackAndWhite();
	bw.save("pagoda_bw.jpg");
	bw.gammaCompress(0.5).save("pagoda_bw_05.jpg");
}
/*/
```

## Superior Streams are still largely untested

Or rather, they are being gradually tested over time.

## License

Your choice of:
- [MIT](http://opensource.org/licenses/MIT)
- [BSD](http://opensource.org/licenses/bsd-license.php)
- [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
- [Eclipse Public License (EPL) v1.0](http://wiki.eclipse.org/EPL)

The image pagoda.jpg, by the author plusgood, is licensed under the Creative Commons Attribution 2.0 Generic license. Downloaded from the [Wikimedia Commons](http://en.wikipedia.org/wiki/File:Silverpagoda.jpg).
*/
}

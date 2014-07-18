package readme;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.jar.JarFile;
import utils.lists2.ArrayList;
import utils.lists2.Files;
import utils.lists2.HashSet;
import utils.lists2.List;
import utils.lists2.Set;
import utils.streams.functions.Function;
import utils.streams.functions.IOFunction;
import utils.streams.functions.Predicate;
import utils.streams.functions.Supplier;
import utils.streams2.HSBStream;
import utils.streams2.IOStream;
import utils.streams2.RGBStream;
import utils.streams2.Stream;
import utils.streams2.Streams;

public class ReadmeMd {
	public static void main(String[] args) throws IOException {
		example6();
		example7();
		example8();
	}
//*Q*
/*
SuperiorStreams
===============

It is possible to have your cake and eat it too.

The Streams API in Java 8 doesn't handle exceptions or resources properly. Superior Streams are a wrapper around the JDK Streams API that adds proper exception passing and resource management without complicating the API.

New: Experimental Collections API replacement featuring regular and immutable versions of Map, Set and List. This is integrated to a new version of the Stream API.

## Superior examples

Lambda expressions throwing IOExceptions are just as simple to use as those throwing unchecked exceptions:

```java
//*/
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

return new Object[] {s4,s43}; }/*/
```

Resources are released as soon as they are no longer needed, automatically and transparently:

```java
//*/
static void example2(Path install, Predicate<Path> isJdk) throws IOException {

	IOStream<Path> s1 = Files.list(install);
	IOStream<Path> s2 = s1.filter(isJdk);
	for(Path path : s1.iterable()) {
		System.out.println(path);
	}
	for(Path path : s2.iterable()) {
		System.out.println(path);
	}

	// JDK Streams need to be manually tracked at each use site:

	try(java.util.stream.Stream<Path> s11 = java.nio.file.Files.list(install);
		java.util.stream.Stream<Path> s12 = java.nio.file.Files.list(install);) {

		java.util.stream.Stream<Path> s22 = s12.filter(isJdk);
		for(Path path : (Iterable<Path>) () -> s11.iterator()) {
			System.out.println(path);
		}
		for(Path path : (Iterable<Path>) () -> s22.iterator()) {
			System.out.println(path);
		}
	}
}/*/
```

Superior Streams can be freely reused as many times as needed:

```java
//*/
static Object[] example3(List<String> JDK_PROJECTS1, java.util.List<String> JDK_PROJECTS2) {

	Stream<String> projects1 = JDK_PROJECTS1.stream();
	Predicate<String> isPrefix1 = (String path) -> projects1.anyMatch(path::startsWith);

	// Each Stream from JDK can only be used once or the result is an exception at runtime:

	Supplier<java.util.stream.Stream<String>> projects2 = () -> JDK_PROJECTS2.stream();
	Predicate<String> isPrefix2 = (String path) -> projects2.get().anyMatch(path::startsWith);

return new Object[] {isPrefix1,isPrefix2}; }/*/
```

## Extra Features

### Replacements for standard library ArrayList, HashSet and HashMap collections

```java
//*/
static Object[] example4() {
	ArrayList<String> list = new ArrayList<>();
	HashSet<String> set = list.add("1").add("2").toHashSet();

	// Immutable versions use the same API but return copies when changed:

	List<String> immutableList = list.toList();
	Set<String> immutableSet = set.toSet().replaceAll(s -> "log: " + s);

return new Object[] {immutableList,immutableSet}; }/*/
```

### Streams of RGB pixels for image manipulation

![Screenshot showing images produced by the examples](https://raw.github.com/Overruler/SuperiorStreams/master/Screenshot.png)

Image manipulation using red, green, blue channels:

```java
//*/
static void example6() throws IOException {
	RGBStream stream = Streams.loadImageInRGB("pagoda.jpg");
	stream.swapRedAndGreen().save("pagoda_rg.jpg");
	stream.swapRedAndBlue().save("pagoda_rb.jpg");
	stream.swapAlphaAndBlue().save("pagoda_ab.png");
	stream.setRed(255).save("pagoda_bright_red.png");
}/*/
```

Image color manipulation in HSB changing hue, saturation and brightness values:

```java
//*/
static void example7() throws IOException {
	HSBStream hsb = Streams.loadImageInHSB("pagoda.jpg");
	hsb.mapBrightness((h, s, b, a) -> b * 0.25).save("pagoda_dark.jpg");
	hsb.mapHue((h, s, b, a) -> h + 60).save("pagoda_altered_hue.jpg");
}/*/
```

Gamma adjustments:

```java
//*/
static void example8() throws IOException {
	RGBStream stream = Streams.loadImageInRGB("pagoda.jpg");
	stream.gammaExpand(2.4).save("pagoda_texture.jpg");
	HSBStream bw = stream.toBlackAndWhite();
	bw.save("pagoda_bw.jpg");
	bw.gammaCompress(0.5).save("pagoda_bw2.jpg");
}/*/
```

### Helper methods for WHERE clauses over Lists, Maps and Sets

```java
//*/
static ArrayList<String> removeBadWords(List<String> list) {
  return Streams.where(list, s -> s.contains("fuck") == false);
}/*/
```

## Superior Streams are still largely untested

Or rather, they are being gradually tested over time.

## License

Your choice of:
- [MIT](http://opensource.org/licenses/MIT)
- [BSD](http://opensource.org/licenses/bsd-license.php)
- [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
- [Eclipse Public License (EPL) v1.0](http://wiki.eclipse.org/EPL)

Several collections classes in the utils.lists2 package are based on classes from [gs-collections](https://github.com/goldmansachs/gs-collections) by Goldman Sachs and are licensed under the Apache License, Version 2.0.

The image pagoda.jpg, by the author plusgood, is licensed under the Creative Commons Attribution 2.0 Generic license. Downloaded from the [Wikimedia Commons](http://en.wikipedia.org/wiki/File:Silverpagoda.jpg).
*/
}

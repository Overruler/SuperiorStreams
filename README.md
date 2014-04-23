SuperiorStreams
===============

It is possible to have your cake and eat it too.

The Streams API in Java 8 doesn't handle exceptions or resources properly. Superior Streams are a wrapper around the JDK Streams API that adds proper exception passing and resource management without complicating the API.

## Superior examples

Lambda expressions throwing IOExceptions are just as simple to use as those throwing unchecked exceptions:

```java
IOFunction<File, JarFile> toJarFile = JarFile::new;
IOStream<JarFile> s4 = s3.map(toJarFile);

// Using JDK Streams requires twisting and turning:

Function<File, JarFile> toJarFile = (File file) -> {
  try {
    return new JarFile(file);
  } catch(IOException e) {
    throw new UncheckedIOException(e);
  }
};
Stream<JarFile> s43 = s33.map(toJarFile);
```

Resources are released as soon as they are no longer needed, automatically and transparently:

```java
IOStream<Path> s1 = Streams.filesList(install);
IOStream<Path> s2 = s1.filter(isJdk);
for(Path path : s1.iterate()) {
  System.out.println(path);
}
for(Path path : s2.iterate()) {
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
```

Superior Streams can be freely reused as many times as needed:

```java
UnStream<String> projects = Streams.from(JDK_PROJECTS);
Predicate<String> isPrefix = (String path) -> projects.anyMatch(path::startsWith);

// Each Stream from JDK can only be used once or the result is an exception at runtime: 

Supplier<Stream<String>> projects = () -> JDK_PROJECTS.stream();
Predicate<String> isPrefix = (String path) -> projects.get().anyMatch(path::startsWith);
```

## Anti-Idioms, or The Cost of Missing Features

Superior Streams code is full of Anti-Idioms, coding patterns that are needlessly complicated and would even be unnecessary if some of Java's language features were more fully-fledged. These missing or half-hearted features have a real cost in terms of code, demonstrated by this library.

### No parameterizable type parameters. Cost: 192 lines of code

This isn't possible:
```java
abstract class AbstractStream<T, SELF<T> extends AbstractStream<T, SELF<T>>> {
  public <R> SELF<R> map(Function<? super T, ? extends R> mapper) { ... }
}
```
Instead each concrete subclass requires one forwarding method per each such instance and one conversion method used by them all. 

### No exception transparency over functional interfaces. Cost: 85 generated classes, 4 generator classes

This isn't possible:
```java
public interface Function<T, U, throws E1> {
  U apply(T t) throws E1;

  default <V, throws E2> 
    Function<T, V, throws E1 | E2> 
      andThen(Function<? super U, ? extends V, throws E2> after) {

    Objects.requireNonNull(after);
    return (T t) -> after.apply(apply(t));
  }
}
```
Instead checked exceptions have to be declared even in code that only passes them through.

### Overloading thinks all exceptions are the same. Cost: 80 extra methods in the public API

This is ambiguous: 
```java
void forEach(Consumer<? super T> action) { ... }
void forEach(IOConsumer<? super T> action) throws IOexception { ... }
// ...
lines1.forEach(s -> Files.isHidden(Paths.get(s)));
lines1.forEach(s -> System.out.println(s));
```
There is no two ways the code can compile if it is to compile at all. Not even trying to but yelling "ambiguous" is lazy. Instead overload resolution needs to be tricked using an extra variable-arity method to do - not even the right thing - but the next best thing to the right thing. 

### No explicit singularity relationship between functional interfaces. Cost: another 80 extra methods in the public API

This is impossible: 
```java
public interface ExConsumer<T, E extends Exception> 
  super extends Consumer<T> extends this<T, RuntimeException> {

  void accept(T t) throws E;
}
// ...
void forEach(ExConsumer<? super T, E> action) throws E { ... }
// 
lines1.forEach((ExConsumer<String, IOException>) s -> Files.isHidden(Paths.get(s)));
lines1.forEach((Consumer<String>) s -> System.out.println(s));
```
Lambda expressions get freely turned into any compatible functional interfaces as if they all were of the same type. This isn't possible if the functional interface types are named. Instead method overloading is needed to allow compatible but named functional interfaces to be used in the same way that lambda expressions can be used.  

## Superior Streams are largely untested

Or rather, they will be gradually tested over time.

## License

Your choice of:
- [MIT](http://opensource.org/licenses/MIT) 
- [BSD](http://opensource.org/licenses/bsd-license.php) 
- [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0) 
- [Eclipse Public License (EPL) v1.0](http://wiki.eclipse.org/EPL)

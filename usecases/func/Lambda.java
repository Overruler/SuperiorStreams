package func;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import utils.streams.IOStream;
import utils.streams.Streams;
import utils.streams.UnStream;
import utils.streams.functions.IOBinaryOperator;
import utils.streams.functions.IOConsumer;
import utils.streams.functions.IOFunction;
import utils.streams.functions.IOPredicate;

public class Lambda {

	static String[] results = { "1_34-6", "1_-4--", "-_----", "-_----", "1__4-6", "1_34-6", "-__--6", "-_3--6", "___4-6",
	  "__-4--", "1_34-6", "-_----", "___4-6", "__34-6", "___4-6", "1_34-6", };

	private static void eat(Object... junk) {
		for(Object object : junk) {
			System.out.println(object);
		}
	}
	public static void main(String[] args) {
		UnStream<String> stream = Streams.from(results);
		ArrayList<String> list = stream.distinct().toList();
		System.out.println(list);
	}
	public static void tests() throws IOException {
		IOStream<String> lines1 = new IOStream<>(() -> Stream.of(results));
		UnStream<String> lines2 = Streams.from(results);
		Stream<String> lines3 = Stream.of(results);
		tests1(lines1, lines2, lines3);
		tests2(lines1, lines2, lines3);
		tests3(lines1, lines2, lines3);
		tests4(lines1, lines2, lines3);
		tests5(lines1, lines2);
	}
	private static void tests1(IOStream<String> lines1, UnStream<String> lines2, Stream<String> lines3)
	  throws IOException {
		IOConsumer<? super String> action1 = s -> Files.isHidden(Paths.get(s));
		Consumer<? super String> action2 = s -> System.out.println(s);
		lines1.peek(action1);
		lines1.peek(action2);
		lines1.peek(s -> Files.isHidden(Paths.get(s)));
		lines1.peek(s -> System.out.println(s));
		lines2.peek(action2);
		lines3.peek(action2);
		lines1.forEach(action1);
		lines1.forEach(action2);
		lines1.forEach(s -> Files.isHidden(Paths.get(s)));
		lines1.forEach(s -> System.out.println(s));
		lines2.forEach(action2);
		lines3.forEach(action2);
		lines1.forEachOrdered(action1);
		lines1.forEachOrdered(action2);
		lines1.forEachOrdered(s -> Files.isHidden(Paths.get(s)));
		lines1.forEachOrdered(s -> System.out.println(s));
		lines2.forEachOrdered(action2);
		lines3.forEachOrdered(action2);
	}
	private static void tests2(IOStream<String> lines1, UnStream<String> lines2, Stream<String> lines3)
	  throws IOException {
		IOPredicate<? super String> action1 = s -> Files.isHidden(Paths.get(s));
		Predicate<? super String> action2 = s -> s.isEmpty();
		lines1.anyMatch(action1);
		lines1.anyMatch(action2);
		lines1.anyMatch(s -> Files.isHidden(Paths.get(s)));
		lines1.anyMatch(s -> s.isEmpty());
		lines2.anyMatch(action2);
		lines3.anyMatch(action2);
		lines1.allMatch(action1);
		lines1.allMatch(action2);
		lines1.allMatch(s -> Files.isHidden(Paths.get(s)));
		lines1.allMatch(s -> s.isEmpty());
		lines2.allMatch(action2);
		lines3.allMatch(action2);
		lines1.noneMatch(action1);
		lines1.noneMatch(action2);
		lines1.noneMatch(s -> Files.isHidden(Paths.get(s)));
		lines1.noneMatch(s -> s.isEmpty());
		lines2.noneMatch(action2);
		lines3.noneMatch(action2);
		lines1.filter(action1);
		lines1.filter(action2);
		lines1.filter(s -> Files.isHidden(Paths.get(s)));
		lines1.filter(s -> s.isEmpty());
		lines2.filter(action2);
		lines3.filter(action2);
	}
	private static void tests3(IOStream<String> lines1, UnStream<String> lines2, Stream<String> lines3) {
		Function<String, Stream<Integer>> mapping1 = s2 -> s2.codePoints().boxed();
		IOFunction<String, Stream<String>> mapping2 = s3 -> Files.lines(Paths.get(s3));
		IOStream<Integer> result1 = lines1.flatMap(mapping1);
		IOStream<String> result2 = lines1.flatMap(mapping2);
		IOStream<Integer> result3 = lines1.flatMap(s4 -> s4.codePoints().boxed());
		IOStream<String> result4 = lines1.flatMap(s1 -> Files.lines(Paths.get(s1)));
		UnStream<Integer> result5 = lines2.flatMap(mapping1);
		Stream<Integer> result6 = lines3.flatMap(mapping1);
		IOStream<Stream<Integer>> map = lines1.map(mapping1);
		IOStream<Stream<String>> map2 = lines1.map(mapping2);
		IOStream<Stream<Integer>> map3 = lines1.map(s -> s.codePoints().boxed());
		IOStream<Stream<String>> map4 = lines1.map(s -> Files.lines(Paths.get(s)));
		UnStream<Stream<Integer>> map5 = lines2.map(mapping1);
		Stream<Stream<Integer>> map6 = lines3.map(mapping1);
		eat(result1, result2, result3, result4);
		eat(map, map2, map3, map4);
		eat(result5, result6, map5, map6);
	}
	private static void tests4(IOStream<String> lines1, UnStream<String> lines2, Stream<String> lines3)
	  throws IOException {
		BinaryOperator<String> mapping1 = (l, r) -> l.isEmpty() ? r : l;
		IOBinaryOperator<String> mapping2 = (l, r) -> Files.isHidden(Paths.get(l)) ? r : l;
		String resultA1 = lines1.reduce("", mapping1);
		String resultA2 = lines1.reduce("", mapping2);
		String resultA3 = lines1.reduce("", (l, r) -> l.isEmpty() ? r : l);
		String resultA4 = lines1.reduce("", (l, r) -> Files.isHidden(Paths.get(l)) ? r : l);
		String resultA5 = lines2.reduce("", mapping1);
		String resultA6 = lines3.reduce("", mapping1);
		Optional<String> resultB1 = lines1.reduce(mapping1);
		Optional<String> resultB2 = lines1.reduce(mapping2);
		Optional<String> resultB3 = lines1.reduce((l, r) -> l.isEmpty() ? r : l);
		Optional<String> resultB4 = lines1.reduce((l, r) -> Files.isHidden(Paths.get(l)) ? r : l);
		Optional<String> resultB5 = lines2.reduce(mapping1);
		Optional<String> resultB6 = lines3.reduce(mapping1);
		eat(resultA1, resultA2, resultA3, resultA4);
		eat(resultB1, resultB2, resultB3, resultB4);
		eat(resultA5, resultA6, resultB5, resultB6);
	}
	private static void tests5(IOStream<String> lines1, UnStream<String> lines2) throws IOException {
		IOFunction<String, Character> toKey1 = s -> key5(s);
		Function<String, Character> toKey2 = s -> s.charAt(0);
		Function<HashMap<Character, String>, TreeMap<Character, String>> toMap = s -> new TreeMap<>(s);
		Function<ArrayList<String>, String> toList = s -> list5(s);
		TreeMap<Character, String> multiMap1 = lines1.toMultiMap(toKey1, toMap, toList);
		TreeMap<Character, String> multiMap2 = lines1.toMultiMap(toKey2, toMap, toList);
		TreeMap<Character, String> multiMap3 = lines1.toMultiMap(s -> key5(s), toMap, toList);
		TreeMap<Character, String> multiMap4 = lines1.toMultiMap(s -> s.charAt(0), toMap, toList);
		TreeMap<Character, String> multiMap5 = lines2.toMultiMap(toKey2, toMap, toList);
		Map<Character, ArrayList<String>> map1 = lines1.toMap(toKey1);
		Map<Character, ArrayList<String>> map2 = lines1.toMap(toKey2);
		Map<Character, ArrayList<String>> map3 = lines1.toMap(s -> key5(s));
		Map<Character, ArrayList<String>> map4 = lines1.toMap(s -> s.charAt(0));
		Map<Character, ArrayList<String>> map5 = lines2.toMap(toKey2);
		eat(multiMap1, multiMap2, multiMap3, multiMap4, multiMap5);
		eat(map1, map2, map3, map4, map5);
	}
	private static char key5(String s) throws IOException {
		return Files.probeContentType(Paths.get(s)).charAt(0);
	}
	private static String list5(ArrayList<String> s) {
		return s.stream().collect(Collectors.joining());
	}
}

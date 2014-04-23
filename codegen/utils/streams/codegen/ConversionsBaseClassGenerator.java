package utils.streams.codegen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Stream;
import static java.nio.charset.StandardCharsets.*;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;
import utils.streams.Streams;
import utils.streams.SummaryStatistics;
import utils.streams.WrapperException;
import utils.streams.functions.Conversions;
import utils.streams.functions.ExIntBinaryOperator;
import static utils.streams.codegen.BaseType.*;
import static utils.streams.codegen.Conversions2TemplateContainer.*;

interface Conversions2TemplateContainer {

	static void foo() {
		System.out.println();
		/*Q*/
		System  .  out  .  println  (  )  ;
		//e/
		System.out.println();
		//*Q*/
		System.out.println();
		///e/
		System.out.println();
	}
	static final String STATIC_RECHECKED_METHOD =
	(""
	/**/+ "	public static <E extends Exception> IntBinaryOperatorish<E> rechecked(Class<E> classOfE, IntBinaryOperator it) {\n"
	/**/+ "		Objects.requireNonNull(classOfE);\n"
	/**/+ "		Objects.requireNonNull(it);\n"
	/**/+ "		return (int t1, int t2) -> {\n"
	/**/+ "			try {\n"
	/**/+ "				return it.applyAsInt(t1, t2);\n"
	/**/+ "			} catch(RuntimeException e) {\n"
	/**/+ "				throw WrapperException.show(e, classOfE);\n"
	/**/+ "			}\n"
	/**/+ "		};\n"
	/**/+ "	}\n"
	/**/+ "").replace("IntBinaryOperatorish<E>", "Retype<E>").replace("IntBinaryOperator", "Untype");

	static final String STATIC_UNCHECKED_METHOD =
	""
	/**/+ "	public static <E extends Exception> IntBinaryOperator unchecked(Class<E> classOfE, IntBinaryOperatorish<E> it) {\n"
	/**/+ "		Objects.requireNonNull(classOfE);\n"
	/**/+ "		Objects.requireNonNull(it);\n"
	/**/+ "		return (int t1, int t2) -> {\n"
	/**/+ "			try {\n"
	/**/+ "				return it.applyAsInt(t1, t2);\n"
	/**/+ "			} catch(Exception e) {\n"
	/**/+ "				throw WrapperException.hide(e, classOfE);\n"
	/**/+ "			}\n"
	/**/+ "		};\n"
	/**/+ "	}\n";

	public static <E extends Exception> IntBinaryOperator unchecked(Class<E> classOfE, ExIntBinaryOperator<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1, int t2) -> {
			try {
				return it.applyAsInt(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExIntBinaryOperator<E> rechecked(Class<E> classOfE, IntBinaryOperator it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1, int t2) -> {
			try {
				return it.applyAsInt(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	static final String BINARY_IO_OPERATOR =
	""
	/**/+ "package utils.streams.functions;\n"
	/**/+ "\n"
	/**/+ "import java.io.IOException;\n"
	/**/+ "\n"
	/**/+ "@FunctionalInterface\n"
	/**/+ "public interface IntBinaryOperator extends IntBinaryOperatorish<E extends Exception> {}\n"
	/**/+ "";
	static final String NON_IO_CLASS_MARKER = "IntBinaryOperatorish";
	static final String INTERFACE_MARKER = "interface ";
	static final String EXCEPTION_MARKER = "E extends Exception";
	static final String EXCEPTION_REPLACEMENT = "IOException";
	static final String BINARY_OPERATOR =
	""
	/**/+ "package utils.streams.functions;\n"
	/**/+ "\n"
	/**/+ "import java.util.Comparator;\n"
	/**/+ "import java.util.Objects;\n"
	/**/+ "import java.util.function.IntBinaryOperator;\n"
	/**/+ "import static utils.streams.functions.Conversions.*;\n"
	/**/+ "\n"
	/**/+ "@FunctionalInterface\n"
	/**/+ "public interface IntBinaryOperatorish<E extends Exception> {\n"
	/**/+ "\n"
	/**/+ "	int applyAsInt(int t1, int t2) throws E;\n"
	/**/+ "/*MORE*/\n"
	/**/+ "	static <E extends Exception> IntBinaryOperatorish<E> recheck(IntBinaryOperator unchecked, Class<E> classOfE) {\n"
	/**/+ "		return rechecked(classOfE, unchecked);\n"
	/**/+ "	}\n"
	/**/+ "\n"
	/**/+ "	default IntBinaryOperator uncheck(Class<E> classOfE) {\n"
	/**/+ "		return unchecked(classOfE, this);\n"
	/**/+ "	}\n"
	/**/+ "}\n"
	/**/+ "\n"
	/**/+ "";

	static final String MARKER_MORE = "/*MORE*/";

	static final String TO_DOUBLE_FUNCTION ="\n" + /*Q*/
	"	default <R> IntFunctionish<R, E> andThen(DoubleFunctionish<? extends R, E> after) {\n" +
	"		Objects.requireNonNull(after);\n" +
	"		return (int t1) -> after.apply(applyAsDouble(t1));\n" +
	"	}\n" +
	"\n" +
	"	default IntToDoubleFunctionish<E> andThenAsDouble(DoubleUnaryOperatorish<E> after) {\n" +
	"		Objects.requireNonNull(after);\n" +
	"		return (int t1) -> after.applyAsDouble(applyAsDouble(t1));\n" +
	"	}\n" +
	"\n" +
	"	default IntUnaryOperatorish<E> andThenAsInt(DoubleToIntFunctionish<E> after) {\n" +
	"		Objects.requireNonNull(after);\n" +
	"		return (int t1) -> after.applyAsInt(applyAsDouble(t1));\n" +
	"	}\n" +
	"\n" +
	"	default IntToLongFunctionish<E> andThenAsLong(DoubleToLongFunctionish<E> after) {\n" +
	"		Objects.requireNonNull(after);\n" +
	"		return (int t1) -> after.applyAsLong(applyAsDouble(t1));\n" +
	"	}\n" +
	"";
	/*E*/
	static final String FUNCTION_EXTRA = ("\n" +/*Q*/
	"	default <V> Functionish<V, R, E> compose(Functionish<? super V, ? extends T, E> before) {\n" +
	"		Objects.requireNonNull(before);\n" +
	"		return (V v) -> apply(before.apply(v));\n" +
	"	}\n" +
	"\n" +
	"	default <V> Functionish<T, V, E> andThen(Functionish<? super R, ? extends V, E> after) {\n" +
	"		Objects.requireNonNull(after);\n" +
	"		return (T t) -> after.apply(apply(t));\n" +
	"	}\n" +
	"\n" +
	"	static <T, E extends Exception> Functionish<T, T, E> identity() {\n" +
	"		return t -> t;\n" +
	"	}\n" +
	"")
	//	.replace("T", "TTT")
	//	.replace("R", "RRR")
	.replace("? super ", "SUPER_")
	.replace("? extends ", "EXTENDS_")
	.replace("Functionish", "Retype")
	.replace("apply", "applyAsInt")
	.replace("(T t)", "(TAIL t)")
	.replace("(V v)", "(HEAD v)")
	;	/*E*/
	static final String CONSUMER_EXTRA = ("\n" + /*Q*/
	"	default IntConsumerish<E> andThen(IntConsumerish<E> after) {\n" +
	"		Objects.requireNonNull(after);\n" +
	"		return (int t) -> {\n" +
	"			accept(t);\n" +
	"			after.accept(t);\n" +
	"		};\n" +
	"	}\n" +
	"")
	.replace("IntConsumerish", "IntBinaryOperatorish")
	.replace("(int t)", "(int t1, int t2)")
	.replace("accept", "applyAsInt")
	.replace("(t)", "(t1, t2)")
	; /*E*/
	static final String BIFUNCTION_EXTRA = ("\n" + /*Q*/
	"	default <V> BiFunctionish<T, U, V, E> andThen(Functionish<? super R, ? extends V, E> after) {\n" +
	"		Objects.requireNonNull(after);\n" +
	"		return (T t, U u) -> after.apply(apply(t, u));\n" +
	"	}\n" +
	"")
	.replace("BiFunctionish<T, U, V, E>", "IntBinaryOperatorish<V, E>")
	.replace("(T t, U u)", "(int t, int u)")
	.replace("(apply(", "(applyAsInt(")
	; /*E*/
	static final String PREDICATE_EXTRA = ("\n" + /*Q*/
	"	default BiPredicatish<T, U, E> and(BiPredicatish<? super T, ? super U, E> other) {\n" +
	"		Objects.requireNonNull(other);\n" +
	"		return (T t, U u) -> test(t, u) && other.test(t, u);\n" +
	"	}\n" +
	"\n" +
	"	default BiPredicatish<T, U, E> negate() {\n" +
	"		return (T t, U u) -> !test(t, u);\n" +
	"	}\n" +
	"\n" +
	"	default BiPredicatish<T, U, E> or(BiPredicatish<? super T, ? super U, E> other) {\n" +
	"		Objects.requireNonNull(other);\n" +
	"		return (T t, U u) -> test(t, u) || other.test(t, u);\n" +
	"	}\n" +
	"")
	.replace("BiPredicatish<T, U, E>", "IntBinaryOperatorish<E>")
	.replace("BiPredicatish<? super T, ? super U, E>", "Retype<SUPER_T, SUPER_U, E>")
	.replace("(T t, U u)", "(int t, int u)")
	.replace("test(", "applyAsInt(")
	; /*E*/
	static final String BINARY_OPERATOR_EXTRA = "/*BINARY_OPERATOR_MARKER*/";
	static final String BINARY_OPERATOR_MARKER ="\n" + /*Q*/
	"	public static <T, E extends Exception> BinaryOperatorish<T, E> minBy(Comparator<? super T> comparator) {\n" +
	"		Objects.requireNonNull(comparator);\n" +
	"		return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;\n" +
	"	}\n" +
	"\n" +
	"	public static <T, E extends Exception> BinaryOperatorish<T, E> maxBy(Comparator<? super T> comparator) {\n" +
	"		Objects.requireNonNull(comparator);\n" +
	"		return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;\n" +
	"	}\n" +
	""
	; /*E*/
}
public class ConversionsBaseClassGenerator {

	static enum Replacer {//*Q*/
		BINARY_OPERATOR_CUSTOM      (BINARY_OPERATOR_EXTRA,                        s -> BINARY_OPERATOR_MARKER),
		TYPE_SUPER_T_SUPER_U_USED   ("Retype<SUPER_T, SUPER_U, E> ",               s -> s.checkedVariant + s.getArgumentTypes(true, null, super_T, super_U)),
		TYPE_SUPER_R_EXTENDS_V_USED ("Retype<SUPER_R, EXTENDS_V, E> ",             s -> s.checkedVariant + s.getArgumentTypes(true, extends_V, super_R, extends_TAIL)),
		TYPE_SUPER_V_EXTENDS_T_USED ("Retype<SUPER_V, EXTENDS_T, E> ",             s -> s.checkedVariant + s.getArgumentTypes(true, super_V, super_HEAD, extends_T)),
		TYPE_T_T_USED               ("<T, E extends Exception> Retype<T, T, E> ",  s -> s.getVariableTypes(true, null, T) + " " + s.checkedVariant + s.getArgumentTypes(true, null, T, T)),
		TYPE_T_V_USED               ("<V> Retype<T, V, E> ",                       s -> s.getVariableTypes(false, V, TAIL) + " " + s.checkedVariant + s.getArgumentTypes(true, V, T, TAIL)),
		TYPE_V_R_USED               ("<V> Retype<V, R, E> ",                       s -> s.getVariableTypes(false, V, HEAD) + " " + s.checkedVariant + s.getArgumentTypes(true, V, HEAD, R)),
		CLASS_DECLARED              ("Retype<E extends Exception> ",               s -> s.checkedVariant + s.getTypeVariables(true)),
		CALL_VARIABLES_WITH_TAIL    ("(TAIL ",                                     s -> "(" + s.getArgumentType(T, TAIL) + " "),
		CALL_VARIABLES_WITH_HEAD    ("(HEAD ",                                     s -> "(" + s.getArgumentType(V, HEAD) + " "),
		IMPORTED_TYPE               (".Untype;",                                   s -> "." + s.uncheckVariant + ";"),
		RECHECKED_TYPE_WITH_V       ("Retype<V, E> ",                              s -> s.checkedVariant + s.getTypeArguments(true, V)),
		RECHECKED_TYPE              ("Retype<E>",                                  s -> s.classTypeGenericRechecked.trim()),
		UNCHECKED_TYPE              ("Untype ",                                    s -> s.classTypeGenericUnchecked.trim() + " "),
		CLASS_DECLARED1             ("IntBinaryOperatorish<E extends Exception> ", s -> s.checkedVariant + s.getTypeVariables(true)),
		IMPORTED_TYPE1              (".IntBinaryOperator;",                        s -> "." + s.uncheckVariant + ";"),
		RECHECKED_TYPE_WITH_V1      ("IntBinaryOperatorish<V, E> ",                s -> s.checkedVariant + s.getTypeArguments(true, V)),
		RECHECKED_TYPE1             ("IntBinaryOperatorish<E>",                    s -> s.classTypeGenericRechecked.trim()),
		UNCHECKED_TYPE1             ("IntBinaryOperator ",                         s -> s.classTypeGenericUnchecked.trim() + " "),
		CALL_VARIABLES_v1           ("(int t1, int t2)",                           s -> s.getMethodVariables("t1", "t2", "t3", "t4")),
		CALL_ARGUMENTS_v1           ("(t1, t2)",                                   s -> s.getMethodArguments("t1", "t2", "t3", "t4")),
		CALL_VARIABLES_v2           ("(int t, int u)",                             s -> s.getMethodVariables("t", "u", "v", "w")),
		CALL_ARGUMENTS_v2           ("(t, u)",                                     s -> s.getMethodArguments("t", "u", "v", "w")),
		TYPE_VARIABLES              ("<E extends Exception> ",                     s -> s.getTypeVariables(true)),
		METHOD_NAME                 ("applyAsInt",                                 s -> s.method),
		RETURN_TYPE                 ("int ",                                       s -> s.returns.asReturn() + " "),
		RETURN                      ("return it.",                                 s -> s.returns == VOID ? "it." : "return it.");
		/*E*/
		private String marker;
		private Function<Signature, String> replacer;

		private Replacer(String marker, Function<Signature, String> replacer) {
			this.marker = marker;
			this.replacer = replacer;
		}
		String replace1(String string) {
			return string.replace(marker, "#" + ordinal() + "#");
		}
		String replace2(String string, Signature signature) {
			String target = "#" + ordinal() + "#";
			if(string.contains(target) == false) {
				return string;
			}
			return string.replace(target, replacer.apply(signature));
		}
		static String applyAll(String template, Signature signature, List<Replacer> replacers) {
			for(Replacer replacer : replacers) {
				template = replacer.replace1(template);
			}
			for(Replacer replacer : replacers) {
				template = replacer.replace2(template, signature);
			}
			return template;
		}
		static String applyAll(String template, Signature signature) {
			return applyAll(template, signature, asList(values()));
		}
	}

	private static final boolean DEBUG = false;
	private static final String EXT_CLASS = ".class";
	private static final String EXT_JAVA = ".java";
	private static final String LINEFEED = "\n";
	private static final String CONVERSIONS_PROLOGUE =
	/**/""
	/**/+ packageOfConversions()
	/**/+ LINEFEED
	/**/+ importAll(Function.class)
	/**/+ importAll(Objects.class)
	/**/+ importAll(WrapperException.class)
	/**/+ importAll(SummaryStatistics.class)
	/**/+ importAll(Streams.class)
	/**/+ importAll(IntSupplier.class)
	/**/+ LINEFEED
	/**/+ startConversionsInterface()
	/**/+ LINEFEED
	/**/+ "";
	private static final String CONVERSIONS_EPILOGUE = "\n\n} //" + conversionsClassData() + "\n";
	private static final String START_BINARY_FILENAME = startingBinaryClass().getSimpleName() + EXT_CLASS;
	private static final String SOURCE_CODE_CLASS_FILENAME = conversionsClassData() + EXT_JAVA;
	private static final Collector<CharSequence, ?, String> CONVERSIONS_JOINER = joining(
	  LINEFEED,
	  CONVERSIONS_PROLOGUE,
	  CONVERSIONS_EPILOGUE);
	private static final boolean IS_OLD = true;

	public static void main(String[] args) {
		try {
			Path folder = findTarget();
			generateIOClasses(BaseType.generated(), folder);
			generateResults(BaseType.generated(), folder, s -> generateOtherClassName(s));
		} catch(UncheckedIOException | URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}
	private static String generateOtherClassName(String name) {
		return IS_OLD ? (name + "ish").replace("eish", "ish") : "Ex"+name;
	}
	private static void generateResults(List<List<BaseType>> strings, Path folder, UnaryOperator<String> naming)
	  throws IOException {
		if(DEBUG) {
			debugMaybe(strings, naming);
		}
		List<Signature> list = toSignatures(strings, naming);
		writeIndividualClasses(folder, list);
		String template = STATIC_UNCHECKED_METHOD + LINEFEED + STATIC_RECHECKED_METHOD;
		String body = toStrings(template, list).collect(CONVERSIONS_JOINER);
		saveResults(body, folder);
	}
	private static void generateIOClasses(List<List<BaseType>> strings, Path folder) throws IOException {
		for(List<BaseType> path : strings) {
			Signature signature = BaseType.unpacked(path, s -> generateOtherClassName(s));
			String io = "IO" + signature.uncheckVariant;
			String body = applyOne(BINARY_IO_OPERATOR, signature);
			String body2 = body.replace(INTERFACE_MARKER + signature.uncheckVariant, INTERFACE_MARKER + io);
			String body3 = body2.replace(NON_IO_CLASS_MARKER, signature.checkedVariant);
			String body4 = body3.replace(EXCEPTION_MARKER, EXCEPTION_REPLACEMENT);
			String other = io + EXT_JAVA;
			Path path1 = folder.resolveSibling(other);
			writeResults(body4, path1);
		}
	}
	private static void debugMaybe(List<List<BaseType>> strings, UnaryOperator<String> naming) {
		String debugTypes = debug(strings, naming);
		System.out.println("debug types\n" + debugTypes);
	}
	private static void writeIndividualClasses(Path folder, List<Signature> list) throws IOException {
		list = replaceAllOfOne(folder, list, CONSUMER_EXTRA, s -> s.contains("Consumer"));
		list = replaceAllOfOne(folder, list, "", s -> s.equals(generateOtherClassName("UnaryOperator")));
		list = replaceAllOfOne(folder, list, FUNCTION_EXTRA, s -> s.equals(generateOtherClassName("Function")));
		list = replaceAllOfOne(folder, list, FUNCTION_EXTRA, s -> s.contains("UnaryOperator"));
		list = replaceAllOfOne(folder, list, BINARY_OPERATOR_EXTRA, s -> s.equals(generateOtherClassName("BinaryOperator")));
		list = replaceAllOfOne(folder, list, PREDICATE_EXTRA, s -> s.contains("Predicat"));
		System.out.println("default generated left: " + list);
		list = replaceAllOfOne(folder, list, BIFUNCTION_EXTRA, (s) -> allButSome(s));
		Set<String> list2 = list.stream().map(s -> s.checkedVariant).collect(toSet());
		Set<String> asList =
		  new HashSet<>(asList(
		    generateOtherClassName("DoubleBinaryOperator"),
		    generateOtherClassName("DoubleSupplier"      ),
		    generateOtherClassName("DoubleToIntFunction" ),
		    generateOtherClassName("DoubleToLongFunction"),
		    generateOtherClassName("IntBinaryOperator"   ),
		    generateOtherClassName("IntSupplier"         ),
		    generateOtherClassName("IntToDoubleFunction" ),
		    generateOtherClassName("IntToLongFunction"   ),
		    generateOtherClassName("LongBinaryOperator"  ),
		    generateOtherClassName("LongSupplier"        ),
		    generateOtherClassName("LongToDoubleFunction"),
		    generateOtherClassName("LongToIntFunction"   ),
		    generateOtherClassName("Supplier"            ),
		    generateOtherClassName("ToDoubleBiFunction"  ),
		    generateOtherClassName("ToDoubleFunction"    ),
		    generateOtherClassName("ToIntBiFunction"     ),
		    generateOtherClassName("ToIntFunction"       ),
		    generateOtherClassName("ToLongBiFunction"    ),
		    generateOtherClassName("ToLongFunction"      )));
		if(asList.equals(list2) == false) {
			throw new IllegalStateException("list2 has " +
			removeAll(asList, list2) +
			", list has " +
			removeAll(list2, asList));
		}
		list = replaceAllOfOne(folder, list, "", s -> true);
		System.out.println("should be no more classes left: " + list);
	}
	private static List<String> removeAll(Set<String> asList, Set<String> list) {
		ArrayList<String> arrayList = new ArrayList<>(asList);
		arrayList.removeAll(list);
		return arrayList;
	}
	private static boolean allButSome(String s) {
		return s.contains("Operator") == false && s.contains("Supplier") == false && s.contains("To") == false;
	}
	private static List<Signature> replaceAllOfOne(
	  Path folder,
	  List<Signature> list,
	  String extraMethods,
	  Predicate<String> tester) throws IOException {
		Map<Boolean, List<Signature>> partition = list.stream().collect(partitioningBy(s -> tester.test(s.checkedVariant)));
		replaceInTemplate(folder, extraMethods, partition.get(true));
		return partition.get(false);
	}
	private static void replaceInTemplate(Path folder, String extraMethods, List<Signature> binaryOperators)
	  throws IOException {
		String template = BINARY_OPERATOR.replace(MARKER_MORE, extraMethods);
		for(Signature signature : binaryOperators) {
			String body = applyOne(template, signature);
			String other = signature.checkedVariant + EXT_JAVA;
			System.out.println("doing " + other);
			Path path1 = folder.resolveSibling(other);
			writeResults(body, path1);
		}
	}
	private static List<Signature> toSignatures(List<List<BaseType>> strings, UnaryOperator<String> ishher) {
		Function<List<BaseType>, ? extends Signature> mapper = (List<BaseType> t) -> BaseType.unpacked(t, ishher);
		return strings.stream().map(mapper).collect(toList());
	}
	private static Stream<String> toStrings(String template, List<Signature> list) {
		return list.stream().map((Signature t) -> applyOne(template, t));
	}
	private static String applyOne(String template, Signature t) {
		return Replacer.applyAll(template, t);
	}
	private static void saveResults(String body, Path targetFolder) throws IOException {
		Path path1 = targetFolder.resolveSibling(SOURCE_CODE_CLASS_FILENAME);
		writeResults(body, path1);
	}
	private static Path findTarget() throws URISyntaxException, FileNotFoundException, IOException {
		Class<?> source = startingBinaryClass();
		URI url = source.getResource(START_BINARY_FILENAME).toURI();
		Path path = Paths.get(url);
		String pathName = source.getPackage().getName();
		while(path != null && pathName.contains(path.getFileName().toString()) == false) {
			path = path.getParent();
		}
		while(path != null && pathName.contains(path.getFileName().toString())) {
			path = path.getParent();
		}
		if(path != null) {
			path = path.getParent();
		}
		if(path == null) {
			throw new FileNotFoundException("Root folder not found starting with " + url);
		}
		Stream<Path> stream = Files.walk(path).filter(p -> pointsToFile(p));
		Path found = stream.findFirst().orElseThrow(() -> notFoundWith(url));
		System.out.println("URL is " + url + " , found path is " + found);
		return found;
	}
	private static String packageOfConversions() {
		return "package " + Conversions.class.getPackage().getName() + ";\n";
	}
	private static String startConversionsInterface() {
		return "public interface " + conversionsClassData() + " {\n";
	}
	private static String conversionsClassData() {
		return Conversions.class.getSimpleName().replace("2", "");
	}
	private static Class<?> startingBinaryClass() {
		return ConversionsBaseClassGenerator.class;
	}
	private static <E> String importAll(Class<E> class1) {
		String name = class1.getPackage().getName();
		return "import " + name + ".*;\nimport " + name + "." + class1.getSimpleName() + ";\n";
	}
	private static String debug(List<List<BaseType>> strings, UnaryOperator<String> ishher) {
		return strings.stream().map((List<BaseType> t) -> debugText(t, ishher)).collect(joining());
	}
	private static String debugText(List<BaseType> t, UnaryOperator<String> ishher) {
		Signature unpack = BaseType.unpacked(t, ishher);
		String checkedConversion = Replacer.applyAll(STATIC_RECHECKED_METHOD, unpack);
		String uncheckConversion = Replacer.applyAll(STATIC_UNCHECKED_METHOD, unpack);
		String replace1 = uncheckConversion.replace('\n', ' ');
		String replace2 = checkedConversion.replace('\n', ' ');
		String sig = unpack.toString().replace("\n", "//" + replace1 + "\n/*" + t + "*/\t");
		String methodPair = "/*" + t + "E*/\t" + sig + "//" + replace2 + "\n";
		return methodPair;
	}
	private static FileNotFoundException notFoundWith(URI url) {
		return new FileNotFoundException("Source not found starting from: " + url);
	}
	private static boolean pointsToFile(Path p) {
		return p.getFileName().toString().equalsIgnoreCase(SOURCE_CODE_CLASS_FILENAME);
	}
	private static void writeResults(String body, Path path1) throws IOException {
		byte[] bytes = body.getBytes(UTF_8);
		if(DEBUG) {
			System.out.println("Was about to write to " + path1 + " data: " + bytes.length + " bytes:");
			System.out.println(body);
		} else {
			Files.write(path1, bytes);
		}
	}
}
package utils.streams.codegen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static java.nio.charset.StandardCharsets.*;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;
import utils.streams.functions.Conversions;
import static utils.streams.codegen.BaseType.*;

public class ConversionsBaseClassGenerator {
	static enum Replacer {//*Q*/
		TYPE_SUPER_T_SUPER_U_USED1  ("ExIntBinaryOperator<SUPER_T, SUPER_U, E> ",              s -> "Ex" + s.uncheckVariant + s.getArgumentTypes(true, null, super_T, super_U)),
		TYPE_SUPER_T_SUPER_U_USED2  ("IOIntBinaryOperator<SUPER_T, SUPER_U> ",                 s -> "IO" + s.uncheckVariant + s.getArgumentTypes(false, null, super_T, super_U)),
		TYPE_SUPER_T_SUPER_U_USED   ("Retype<SUPER_T, SUPER_U, E> ",                           s -> s.checkedVariant + s.getArgumentTypes(true, null, super_T, super_U)),
		TYPE_SUPER_R_EXTENDS_V_USED1("ExIntBinaryOperator<SUPER_R, EXTENDS_V, E> ",            s -> "Ex" + s.uncheckVariant + s.getArgumentTypes(true, extends_V, super_R, extends_TAIL)),
		TYPE_SUPER_R_EXTENDS_V_USED2("IOIntBinaryOperator<SUPER_R, EXTENDS_V> ",               s -> "IO" + s.uncheckVariant + s.getArgumentTypes(false, extends_V, super_R, extends_TAIL)),
		TYPE_SUPER_R_EXTENDS_V_USED ("Retype<SUPER_R, EXTENDS_V, E> ",                         s -> s.checkedVariant + s.getArgumentTypes(true, extends_V, super_R, extends_TAIL)),
		TYPE_SUPER_V_EXTENDS_T_USED1("ExIntBinaryOperator<SUPER_V, EXTENDS_T, E> ",            s -> "Ex" + s.uncheckVariant + s.getArgumentTypes(true, super_V, super_HEAD, extends_T)),
		TYPE_SUPER_V_EXTENDS_T_USED2("IOIntBinaryOperator<SUPER_V, EXTENDS_T> ",               s -> "IO" + s.uncheckVariant + s.getArgumentTypes(false, super_V, super_HEAD, extends_T)),
		TYPE_SUPER_V_EXTENDS_T_USED ("Retype<SUPER_V, EXTENDS_T, E> ",                         s -> s.checkedVariant + s.getArgumentTypes(true, super_V, super_HEAD, extends_T)),
		TYPE_T_T_USED1              ("<T, E extends Exception> ExIntBinaryOperator<T, T, E> ", s -> (s.getVariableTypes(true, null, T) + "Ex" + s.uncheckVariant ).trim() + s.getArgumentTypes(true, null, T, T)),
		TYPE_T_T_USED2              ("<T> IOIntBinaryOperator<T, T> ",                         s -> (s.getVariableTypes(false, null, T) + "IO" + s.uncheckVariant).trim() + s.getArgumentTypes(false, null, T, T)),
		TYPE_T_T_USED               ("<T, E extends Exception> Retype<T, T, E> ",              s -> (s.getVariableTypes(true, null, T) + s.checkedVariant        ).trim() + s.getArgumentTypes(true, null, T, T)),
		TYPE_T_V_USED1              ("<V> ExIntBinaryOperator<T, V, E> ",                      s -> (s.getVariableTypes(false, V, TAIL) + "Ex" + s.uncheckVariant).trim() + s.getArgumentTypes(true, V, T, U, TAIL)),
		TYPE_T_V_USED2              ("<V> IOIntBinaryOperator<T, V> ",                         s -> (s.getVariableTypes(false, V, TAIL) + "IO" + s.uncheckVariant).trim() + s.getArgumentTypes(false, V, T, U, TAIL)),
		TYPE_T_V_USED               ("<V> Retype<T, V, E> ",                                   s -> (s.getVariableTypes(false, V, TAIL) + s.checkedVariant       ).trim() + s.getArgumentTypes(true, V, T, U, TAIL)),
		TYPE_T_R_USED1              ("<V> ExIntBinaryOperator<T, R, E> ",                      s -> (s.getVariableTypes(false, V, RTRN) + "Ex" + s.uncheckVariant).trim() + s.getArgumentTypes(true, V, T, U, RTRN)),
		TYPE_T_R_USED2              ("<V> IOIntBinaryOperator<T, R> ",                         s -> (s.getVariableTypes(false, V, RTRN) + "IO" + s.uncheckVariant).trim() + s.getArgumentTypes(false, V, T, U, RTRN)),
		TYPE_T_R_USED               ("<V> Retype<T, R, E> ",                                   s -> (s.getVariableTypes(false, V, RTRN) + s.checkedVariant       ).trim() + s.getArgumentTypes(true, V, T, U, RTRN)),
		TYPE_V_R_USED1              ("<V> ExIntBinaryOperator<V, R, E> ",                      s -> (s.getVariableTypes(false, V, HEAD) + "Ex" + s.uncheckVariant).trim() + s.getArgumentTypes(true, V, HEAD, R)),
		TYPE_V_R_USED2              ("<V> IOIntBinaryOperator<V, R> ",                         s -> (s.getVariableTypes(false, V, HEAD) + "IO" + s.uncheckVariant).trim() + s.getArgumentTypes(false, V, HEAD, R)),
		TYPE_V_R_USED               ("<V> Retype<V, R, E> ",                                   s -> (s.getVariableTypes(false, V, HEAD) + s.checkedVariant       ).trim() + s.getArgumentTypes(true, V, HEAD, R)),
		CLASS_DECLARED1             ("ExIntBinaryOperator<E extends Exception> ",              s -> "Ex" + s.uncheckVariant + s.getTypeVariables(true)),
		CLASS_DECLARED2             ("interface IOIntBinaryOperator ",                         s -> "interface IO" + s.uncheckVariant + s.getTypeVariables(false)),
		CLASS_DECLARED              ("Retype<E extends Exception> ",                           s -> s.checkedVariant + s.getTypeVariables(true)),
		CALL_VARIABLES_WITH_TAIL    ("(TAIL ",                                                 s -> "(" + s.getArgumentType(T, TAIL) + " "),
		CALL_VARIABLES_WITH_HEAD    ("(HEAD ",                                                 s -> "(" + s.getArgumentType(V, HEAD) + " "),
		IMPORTED_TYPE1              ("java.util.function.IntBinaryOperator;",                  s -> "java.util.function." + s.uncheckVariant + ";"),
		IMPORTED_TYPE               (".Untype;",                                               s -> "." + s.uncheckVariant + ";"),
		RECHECKED_TYPE_WITH_V1      ("ExIntBinaryOperator<V, E> ",                             s -> "Ex" + s.uncheckVariant + s.getTypeArguments(true, V)),
		RECHECKED_TYPE_WITH_V2      ("IOIntBinaryOperator<V> ",                                s -> "IO" + s.uncheckVariant + s.getTypeArguments(false, V)),
		RECHECKED_TYPE_WITH_V       ("Retype<V, E> ",                                          s -> s.checkedVariant + s.getTypeArguments(true, V)),
		RECHECKED_TYPE1             ("ExIntBinaryOperator<E>",                                 s -> s.classTypeGenericRechecked.trim()),
		RECHECKED_TYPE3             ("ExIntBinaryOperator<IOException>",                       s -> s.classTypeGenericRechecked.trim().replace("E>", "IOException>")),
		RECHECKED_TYPE2             ("IOIntBinaryOperator",                                    s -> "IO" + s.classTypeGenericUnchecked.trim()),
		RECHECKED_TYPE              ("Retype<E>",                                              s -> s.classTypeGenericRechecked.trim()),
		UNCHECKED_TYPE1             ("java.util.function.IntBinaryOperator ",                  s -> "java.util.function." + s.classTypeGenericUnchecked.trim() + " "),
		UNCHECKED_TYPE              ("Untype ",                                                s -> s.classTypeGenericUnchecked.trim() + " "),
		CALL_VARIABLES_v1           ("(int t1, int t2)",                                       s -> s.getMethodVariables("t1", "t2", "t3", "t4")),
		CALL_ARGUMENTS_v1           ("(t1, t2)",                                               s -> s.getMethodArguments("t1", "t2", "t3", "t4")),
		CALL_VARIABLES_v2           ("(int t, int u)",                                         s -> s.getMethodVariables("t", "u", "v", "w")),
		CALL_ARGUMENTS_v2           ("(t, u)",                                                 s -> s.getMethodArguments("t", "u", "v", "w")),
		TYPE_VARIABLES              ("<E extends Exception> ",                                 s -> s.getTypeVariables(true).trim().replace(">", "> ")),
		TYPE_VARIABLES1             ("<E extends IOException> ",                               s -> s.getTypeVariables(false).trim().replace(">", "> ")),
		METHOD_NAME                 ("applyAsInt",                                             s -> s.method),
		RETURN_TYPE                 ("int ",                                                   s -> s.returns.asReturn() + " "),
		RETURN1                     ("return this.",                                           s -> s.returns == VOID ? "" : "return "),
		RETURN2                     ("return unchecked.",                                      s -> s.returns == VOID ? "unchecked." : "return unchecked.");
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
	private static final String START_BINARY_FILENAME = startingBinaryClass().getSimpleName() + EXT_CLASS;
	private static final String SOURCE_CODE_CLASS_FILENAME = conversionsClassData() + EXT_JAVA;
	private static final boolean IS_OLD = false;

	public static void main(String[] args) {
		try {
			Path folder = findTarget();
			generateResults(Templates.forEx(), BaseType.generated(), folder, sig -> sig.checkedVariant);
			generateResults(Templates.forIO(), BaseType.generated(), folder, sig -> "IO" + sig.uncheckVariant);
		} catch(UncheckedIOException | URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}
	private static String generateOtherClassName(String name) {
		return IS_OLD ? (name + "ish").replace("eish", "ish") : "Ex" + name;
	}
	private static void generateResults(
		Templates templates,
		List<List<BaseType>> strings,
		Path folder,
		Function<Signature, String> naming) throws IOException {
		List<Signature> list = toSignatures(strings);
		writeIndividualClasses(templates, naming, folder, list);
	}
	private static void writeIndividualClasses(
		Templates templates,
		Function<Signature, String> naming,
		Path folder,
		List<Signature> list) throws IOException {
		String theUnaryOperator = generateOtherClassName("UnaryOperator");
		String theFunction = generateOtherClassName("Function");
		String theBinaryOperator = generateOtherClassName("BinaryOperator");
		String consumerLike = templates.consumerLike();
		String functionLike = templates.functionLike();
		String unaryOpeLike = templates.unaryOperatorLike();
		String binaryOpLike = templates.binaryOperatorLike();
		String predicatLike = templates.predicateLike();
		String supplierLike = templates.supplierLike();
		String biFunctiLike = templates.biFunctionLike();
		list = replaceAllOfOne(naming, folder, list, consumerLike, s -> s.contains("Consumer"));
		list = replaceAllOfOne(naming, folder, list, unaryOpeLike, s -> s.equals(theUnaryOperator));
		list = replaceAllOfOne(naming, folder, list, functionLike, s -> s.equals(theFunction));
		list = replaceAllOfOne(naming, folder, list, functionLike, s -> s.contains("UnaryOperator"));
		list = replaceAllOfOne(naming, folder, list, binaryOpLike, s -> s.equals(theBinaryOperator));
		list = replaceAllOfOne(naming, folder, list, predicatLike, s -> s.contains("Predicate"));
		list = replaceAllOfOne(naming, folder, list, supplierLike, s -> s.contains("Operator"));
		list = replaceAllOfOne(naming, folder, list, supplierLike, s -> s.contains("Supplier"));
		list = replaceAllOfOne(naming, folder, list, supplierLike, s -> s.contains("To"));
		list = replaceAllOfOne(naming, folder, list, biFunctiLike, s -> true);
		System.out.println("individual classes remaining: " + list);
	}
	private static List<Signature> replaceAllOfOne(
		Function<Signature, String> naming,
		Path folder,
		List<Signature> list,
		String template,
		Predicate<String> tester) throws IOException {
		Map<Boolean, List<Signature>> partition =
			list.stream().collect(partitioningBy(s -> tester.test(s.checkedVariant)));
		for(Signature signature : partition.get(true)) {
			String body = applyOne(template, signature);
			String other = naming.apply(signature) + EXT_JAVA;
			System.out.println("doing " + other);
			Path path1 = folder.resolveSibling(other);
			writeResults(body, path1);
		}
		return partition.get(false);
	}
	private static List<Signature> toSignatures(List<List<BaseType>> strings) {
		Function<List<BaseType>, ? extends Signature> mapper = (List<BaseType> t) -> BaseType.unpacked(t);
		return strings.stream().map(mapper).collect(toList());
	}
	private static String applyOne(String template, Signature t) {
		return Replacer.applyAll(template, t);
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
	private static String conversionsClassData() {
		return Conversions.class.getSimpleName().replace("2", "");
	}
	private static Class<?> startingBinaryClass() {
		return ConversionsBaseClassGenerator.class;
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
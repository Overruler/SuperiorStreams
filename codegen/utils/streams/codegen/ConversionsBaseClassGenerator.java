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
import utils.streams.WrapperException;
import static utils.streams.codegen.BaseType.*;

public class ConversionsBaseClassGenerator {
	static enum Replacer {//*Q*/
		CLASS_JAVADOC_COMMENT1      ("/** class-ex */",                                        s -> s.javadocForClass(true)),
		CLASS_JAVADOC_COMMENT2      ("/** class */",                                           s -> s.javadocForClass(false)),
		METHOD_JAVADOC_COMMENT      ("/** method-ex */",                                       s -> s.javadocForMethod("E")),
		TYPE_SUPER_T_SUPER_U_USED1  ("ExIntBinaryOperator<SUPER_T, SUPER_U, E> ",              s -> "Ex" + s.uncheckVariant + s.getArgumentTypes(true, null, super_T, super_U)),
		TYPE_SUPER_T_SUPER_U_USED2  ("IOIntBinaryOperator<SUPER_T, SUPER_U> ",                 s -> "IO" + s.uncheckVariant + s.getArgumentTypes(false, null, super_T, super_U)),
		TYPE_SUPER_T_SUPER_U_USED3  ("IntBinaryOperator<SUPER_T, SUPER_U> ",                   s ->        s.uncheckVariant + s.getArgumentTypes(false, null, super_T, super_U)),
		TYPE_SUPER_R_EXTENDS_V_USED1("ExIntBinaryOperator<SUPER_R, EXTENDS_V, E> ",            s -> "Ex" + s.uncheckVariant + s.getArgumentTypes(true, extends_V, super_R, extends_TAIL)),
		TYPE_SUPER_R_EXTENDS_V_USED2("IOIntBinaryOperator<SUPER_R, EXTENDS_V> ",               s -> "IO" + s.uncheckVariant + s.getArgumentTypes(false, extends_V, super_R, extends_TAIL)),
		TYPE_SUPER_R_EXTENDS_V_USED3("IntBinaryOperator<SUPER_R, EXTENDS_V> ",                 s ->        s.uncheckVariant + s.getArgumentTypes(false, extends_V, super_R, extends_TAIL)),
		TYPE_SUPER_V_EXTENDS_T_USED1("ExIntBinaryOperator<SUPER_V, EXTENDS_T, E> ",            s -> "Ex" + s.uncheckVariant + s.getArgumentTypes(true, super_V, super_HEAD, extends_T)),
		TYPE_SUPER_V_EXTENDS_T_USED2("IOIntBinaryOperator<SUPER_V, EXTENDS_T> ",               s -> "IO" + s.uncheckVariant + s.getArgumentTypes(false, super_V, super_HEAD, extends_T)),
		TYPE_SUPER_V_EXTENDS_T_USED3("IntBinaryOperator<SUPER_V, EXTENDS_T> ",                 s ->        s.uncheckVariant + s.getArgumentTypes(false, super_V, super_HEAD, extends_T)),
		TYPE_T_T_USED1              ("<T, E extends Exception> ExIntBinaryOperator<T, T, E> ", s -> (s.getVariableTypes(true, null, T)  + "Ex" + s.uncheckVariant).trim() + s.getArgumentTypes(true, null, T, T)),
		TYPE_T_T_USED2              ("<T> IOIntBinaryOperator<T, T> ",                         s -> (s.getVariableTypes(false, null, T) + "IO" + s.uncheckVariant).trim() + s.getArgumentTypes(false, null, T, T)),
		TYPE_T_T_USED3              ("<T> IntBinaryOperator<T, T> ",                           s -> (s.getVariableTypes(false, null, T)        + s.uncheckVariant).trim() + s.getArgumentTypes(false, null, T, T)),
		TYPE_T_V_USED1              ("<V> ExIntBinaryOperator<T, V, E> ",                      s -> (s.getVariableTypes(false, V, TAIL) + "Ex" + s.uncheckVariant).trim() + s.getArgumentTypes(true, V, T, U, TAIL)),
		TYPE_T_V_USED2              ("<V> IOIntBinaryOperator<T, V> ",                         s -> (s.getVariableTypes(false, V, TAIL) + "IO" + s.uncheckVariant).trim() + s.getArgumentTypes(false, V, T, U, TAIL)),
		TYPE_T_V_USED3              ("<V> IntBinaryOperator<T, V> ",                           s -> (s.getVariableTypes(false, V, TAIL)        + s.uncheckVariant).trim() + s.getArgumentTypes(false, V, T, U, TAIL)),
		TYPE_T_R_USED1              ("<V> ExIntBinaryOperator<T, R, E> ",                      s -> (s.getVariableTypes(false, V, RTRN) + "Ex" + s.uncheckVariant).trim() + s.getArgumentTypes(true, V, T, U, RTRN)),
		TYPE_T_R_USED2              ("<V> IOIntBinaryOperator<T, R> ",                         s -> (s.getVariableTypes(false, V, RTRN) + "IO" + s.uncheckVariant).trim() + s.getArgumentTypes(false, V, T, U, RTRN)),
		TYPE_T_R_USED3              ("<V> IntBinaryOperator<T, R> ",                           s -> (s.getVariableTypes(false, V, RTRN)        + s.uncheckVariant).trim() + s.getArgumentTypes(false, V, T, U, RTRN)),
		TYPE_V_R_USED1              ("<V> ExIntBinaryOperator<V, R, E> ",                      s -> (s.getVariableTypes(false, V, HEAD) + "Ex" + s.uncheckVariant).trim() + s.getArgumentTypes(true, V, HEAD, R)),
		TYPE_V_R_USED2              ("<V> IOIntBinaryOperator<V, R> ",                         s -> (s.getVariableTypes(false, V, HEAD) + "IO" + s.uncheckVariant).trim() + s.getArgumentTypes(false, V, HEAD, R)),
		TYPE_V_R_USED3              ("<V> IntBinaryOperator<V, R> ",                           s -> (s.getVariableTypes(false, V, HEAD)        + s.uncheckVariant).trim() + s.getArgumentTypes(false, V, HEAD, R)),
		CLASS_DECLARED1             ("ExIntBinaryOperator<E extends Exception> ",              s -> "Ex" + s.uncheckVariant + s.getTypeVariables(true)),
		CLASS_DECLARED2             ("interface IOIntBinaryOperator ",                         s -> "interface IO" + s.uncheckVariant + s.getTypeVariables(false)),
		CLASS_DECLARED3             ("interface IntBinaryOperator ",                           s -> "interface " + s.uncheckVariant + s.getTypeVariables(false)),
		CALL_VARIABLES_WITH_TAIL    ("(TAIL ",                                                 s -> "(" + s.getArgumentType(T, TAIL) + " "),
		CALL_VARIABLES_WITH_HEAD    ("(HEAD ",                                                 s -> "(" + s.getArgumentType(V, HEAD) + " "),
		IMPORTED_TYPE1              ("java.util.function.IntBinaryOperator;",                  s -> "java.util.function." + s.uncheckVariant + ";"),
		UNCHECKED_TYPE1             ("java.util.function.IntBinaryOperator ",                  s -> "java.util.function." + s.classTypeGenericUnchecked.trim() + " "),
		RECHECKED_TYPE_WITH_V1      ("ExIntBinaryOperator<V, E> ",                             s -> "Ex" + s.uncheckVariant + s.getTypeArguments(true, V)),
		RECHECKED_TYPE_WITH_V2      ("IOIntBinaryOperator<V> ",                                s -> "IO" + s.uncheckVariant + s.getTypeArguments(false, V)),
		RECHECKED_TYPE_WITH_V3      ("IntBinaryOperator<V> ",                                  s ->        s.uncheckVariant + s.getTypeArguments(false, V)),
		SUPER_TYPE1                 ("ExIntBinaryOperator<IOException>",                       s -> s.classTypeGenericRechecked.trim().replace("E>", "IOException>")),
		SUPER_TYPE2                 ("ExIntBinaryOperator<RuntimeException>",                  s -> s.classTypeGenericRechecked.trim().replace("E>", "RuntimeException>")),
		RECHECKED_TYPE1             ("ExIntBinaryOperator<E>",                                 s ->        s.classTypeGenericRechecked.trim()),
		RECHECKED_TYPE2             ("IOIntBinaryOperator",                                    s -> "IO" + s.classTypeGenericUnchecked.trim()),
		RECHECKED_TYPE3             ("IntBinaryOperator",                                      s ->        s.classTypeGenericUnchecked.trim()),
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

	public static void main(String[] args) {
		try {
			Path folder = findTarget();
			generateResults(Templates.forException(), BaseType.generated(), folder, sig -> sig.checkedVariant);
			generateResults(Templates.forIOException(), BaseType.generated(), folder, sig -> "IO" + sig.uncheckVariant);
			generateResults(Templates.forRuntimeException(), BaseType.generated(), folder, sig -> sig.uncheckVariant);
		} catch(UncheckedIOException | URISyntaxException | IOException e) {
			e.printStackTrace();
		}
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
		String consumerLike = templates.consumerLike();
		String functionLike = templates.functionLike();
		String unaryOpeLike = templates.unaryOperatorLike();
		String binaryOpLike = templates.binaryOperatorLike();
		String predicatLike = templates.predicateLike();
		String supplierLike = templates.supplierLike();
		String biFunctiLike = templates.biFunctionLike();
		list = replaceAllOfOne(naming, folder, list, consumerLike, s -> s.contains("Consumer"));
		list = replaceAllOfOne(naming, folder, list, unaryOpeLike, s -> s.equals("ExUnaryOperator"));
		list = replaceAllOfOne(naming, folder, list, functionLike, s -> s.equals("ExFunction"));
		list = replaceAllOfOne(naming, folder, list, functionLike, s -> s.contains("UnaryOperator"));
		list = replaceAllOfOne(naming, folder, list, binaryOpLike, s -> s.equals("ExBinaryOperator"));
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
			String other = naming.apply(signature) + ".java";
			System.out.println("doing " + other);
			Files.write(folder.resolve(other), body.getBytes(UTF_8));
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
		Class<WrapperException> startingClass = WrapperException.class;
		String startingClassName = startingClass.getSimpleName();
		String classBinaryFilename = startingClassName + ".class";
		URI url = startingClass.getResource(classBinaryFilename).toURI();
		Path path = Paths.get(url);
		String pathName = startingClass.getPackage().getName();
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
		String classCodeFilename = startingClassName + ".java";
		Stream<Path> stream = Files.walk(path).filter(p -> pointsToFile(p, classCodeFilename));
		Path sourceCodeFile = stream.findFirst().orElseThrow(() -> notFoundWith(url));
		Path found = sourceCodeFile.resolveSibling("functions");
		if(Files.notExists(found)) {
			Files.createDirectories(found);
		}
		System.out.println("URL is " + url + " , found path is " + found);
		return found;
	}
	private static FileNotFoundException notFoundWith(URI url) {
		return new FileNotFoundException("Source not found starting from: " + url);
	}
	private static boolean pointsToFile(Path p, String filename) {
		return p.getFileName().toString().equalsIgnoreCase(filename);
	}
}
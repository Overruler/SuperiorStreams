package utils.streams.codegen;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.*;
import static utils.streams.codegen.BaseType.*;

class Signature {
	public final String checkedVariant;
	public final String uncheckVariant;
	public final String method;
	public final List<BaseType> types;
	public final BaseType returns;

	public final String methodArgumentsText;
	public final String methodReturnText;
	public final String typeArgumentsUnchecked;
	public final String typeArgumentsRechecked;
	public final String classNameGenericRechecked;
	public final String classNameGenericUnchecked;
	public final String classTypeGenericRechecked;
	public final String classTypeGenericUnchecked;

	public Signature(String method, List<BaseType> types, BaseType returns, String checkedVariant, String uncheckVariant) {
		this.method = method;
		this.types = Collections.unmodifiableList(types);
		this.returns = returns;
		this.checkedVariant = checkedVariant;
		this.uncheckVariant = uncheckVariant;

		methodArgumentsText = methodArgumentsText();
		methodReturnText = returns.asReturn();
		typeArgumentsUnchecked = get00(true, toArguments());
		typeArgumentsRechecked = get00(false, toArguments());
		classNameGenericRechecked = get(checkedVariant, true, toVariables());
		classNameGenericUnchecked = get(uncheckVariant, false, toVariables());
		classTypeGenericRechecked = get(checkedVariant, true, toArguments());
		classTypeGenericUnchecked = get(uncheckVariant, false, toArguments());

	}

	private String get(String variant, boolean exceptionStatus, Function<? super BaseType, ? extends String> variables) {
		return variant +
		cleanedTypes("<" + asTypeArguments(exceptionStatus).stream().map(variables).collect(joining(", ")) + "> ");
	}

	private String get00(boolean exceptionStatus, Function<? super BaseType, ? extends String> arguments) {
		return cleanedTypes("<" + asTypeArguments(exceptionStatus).stream().map(arguments).collect(joining(", ")) + "> ");
	}

	private static Function<? super BaseType, ? extends String> toArguments() {
		return base -> base.argumentVariant;
	}

	private static Function<? super BaseType, ? extends String> toVariables() {
		return base -> base.variableVariant;
	}

	private String methodArgumentsText() {
		return "(" + asCallVariables() + ")";
	}

	private List<BaseType> asTypeVariables(boolean includeException, BaseType returnType) {
		Stream<BaseType> stream = gatherTypes(includeException, returnType);
		Stream<BaseType> filter = stream.filter((BaseType current) -> BaseType.isPrimitive(current) == false);
		return filter.collect(toList());
	}

	private List<BaseType> asTypeArguments(boolean includeException) {
		Stream<BaseType> stream = gatherTypes(includeException, returns);
		Stream<BaseType> filter = stream.filter((BaseType current) -> BaseType.isPrimitive(current) == false);
		return filter.collect(toList());
	}

	private List<BaseType> asTypeArguments(boolean includeException, BaseType returnType) {
		Stream<BaseType> stream = gatherTypes(includeException, returnType);
		Stream<BaseType> filter = stream.filter((BaseType current) -> BaseType.isPrimitive(current) == false);
		return filter.collect(toList());
	}

	private Stream<BaseType> gatherTypes(boolean includeException, BaseType returnType) {
		Stream<BaseType> stream = concat(types.stream(), of(returnType)).distinct();
		return includeException ? concat(stream, of(E)) : stream;
	}

	private String asCallVariables() {
		int[] indexer = { 1 };
		return types.stream().sequential().map((BaseType type) -> type.asCallVariable(indexer[0]++)).collect(joining(", "));
	}

	private String asCallVariables(String... names) {
		IntFunction<String> mapper = index -> types.get(index).asCallVariable(names[index]);
		return IntStream.range(0, types.size()).mapToObj(mapper).collect(joining(", "));
	}

	private String asCallArguments() {
		return IntStream.range(1, types.size() + 1).mapToObj(index -> "t" + index).collect(joining(", "));
	}

	private String asCallArguments(String... names) {
		return Stream.of(names).limit(types.size()).collect(joining(", "));
	}

	@Override
	public String toString() {
		return recheckDebug() + "\n" + uncheckDebug();
	}

	private String recheckDebug() {
		return debugCheck(true);
	}

	private String uncheckDebug() {
		return debugCheck(false);
	}

	private String debugCheck(boolean includeException) {
		String string =
		  "class " +
		  getClassNameGeneric(includeException) +
		  "{ " +
		  getTypeArguments(includeException) +
		  getReturnType() +
		  " " +
		  method +
		  getMethodArguments() +
		  getReturnStatementBlock() +
		  "}";
		return string;
	}

	private String getClassNameGeneric(boolean includeException) {
		return (includeException ? checkedVariant : uncheckVariant) + getTypeVariables(includeException);
	}

	String getArgumentType(BaseType replacement, BaseType baseType) {
		return reinterpret(baseType, replacement).argumentVariant;
	}

	String getArgumentTypes(boolean includeException, BaseType replacement, BaseType... baseTypes) {
		Stream<BaseType> stream1 = of(baseTypes).map(type -> reinterpret(type, replacement));
		Stream<BaseType> stream = includeException ? concat(stream1, of(E)) : stream1;
		Predicate<BaseType> keepObjects = (BaseType current) -> current != null && BaseType.isPrimitive(current) == false;
		return cleanedTypes("<" + stream.filter(keepObjects).map(toArguments()).collect(joining(", ")) + "> ");
	}

	String getVariableTypes(boolean includeException, BaseType replacement, BaseType... baseTypes) {
		Stream<BaseType> stream1 = of(baseTypes).map(type -> reinterpret(type, replacement));
		Stream<BaseType> stream = includeException ? concat(stream1, of(E)) : stream1;
		Predicate<BaseType> keepObjects = (BaseType current) -> BaseType.isPrimitive(current) == false;
		return cleanedTypes("<" + stream.filter(keepObjects).map(toVariables()).collect(joining(", ")) + "> ");
	}

	private BaseType reinterpret(BaseType type, BaseType replacement) {
		BaseType result = getTypeAt(type);
		if(result == null) {
	    return null;
    }
		if(primitives.contains(result)) {
			return result;
		}
		if(metatypes.contains(type)) {
			return replacement;
		}
		return type;
	}

	private BaseType getTypeAt(BaseType type) {
		switch(type) {/*Q*/
			         case extends_R: case super_R: case R: return   returns;
			         case extends_T: case super_T: case T: return safeGet(0);
			         case extends_V: case super_V: case V: return safeGet(3);
			         case extends_U: case super_U: case U: return safeGet(1);
			         case extends_S: case super_S: case S: return safeGet(2);
			case extends_HEAD: case super_HEAD: case HEAD: return safeGet(0);
			case extends_TAIL: case super_TAIL: case TAIL: return safeGet(types.size() - 1);
			                                       case W: return safeGet(4);
			                                      default: return safeGet(0);
		} /*E*/
	}

	private BaseType safeGet(int index) {
		return index < types.size() ? types.get(index) : null;
	}

	String getTypeVariables(boolean includeException) {
		return cleanedTypes("<" +
		asTypeArguments(includeException).stream().map(toVariables()).collect(joining(", ")) +
		"> ");
	}

	String getTypeVariables(boolean includeException, BaseType alternateReturn) {
		return cleanedTypes("<" +
		asTypeVariables(includeException, alternateReturn).stream().map(toVariables()).collect(joining(", ")) +
		"> ");
	}

	String getTypeArguments(boolean includeException) {
		return cleanedTypes("<" +
		asTypeArguments(includeException).stream().map(toArguments()).collect(joining(", ")) +
		"> ");
	}

	String getTypeArguments(boolean includeException, BaseType alternateReturn) {
		return cleanedTypes("<" +
		asTypeArguments(includeException, alternateReturn).stream().map(toArguments()).collect(joining(", ")) +
		"> ");
	}

	String getReturnType() {
		return returns.asReturn();
	}

	String getMethodVariables() {
		return "(" + asCallVariables() + ")";
	}

	String getMethodVariables(String... names) {
		return "(" + asCallVariables(names) + ")";
	}

	String getMethodArguments() {
		return "(" + asCallArguments() + ")";
	}

	String getMethodArguments(String... names) {
		return "(" + asCallArguments(names) + ")";
	}

	String getReturnStatementBlock() {
		return "{" + returns.asDefaultReturn() + "}";
	}

	private static String cleanedTypes(String string) {
		return string.replace("<> ", "").replace("<>", "");
	}
}

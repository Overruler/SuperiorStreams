package utils.streams.codegen;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

enum BaseType implements Supplier<String> {
	i("int"),
	l("long"),
	d("double"),
	b("boolean"),
	VOID("void"),
	HEAD("!head!"),
	TAIL("!tail!"),
	R("R"),
	S("S"),
	T("T"),
	U("U"),
	V("V"),
	W("W"),
	E("E", "E extends Exception"),
	A("A", "A extends AutoCloseable"),
	extends_R("? extends R"),
	extends_S("? extends S"),
	extends_T("? extends T"),
	extends_U("? extends U"),
	extends_V("? extends V"),
	extends_HEAD("? extends !head!"),
	extends_TAIL("? extends !tail!"),
	super_R("? super R"),
	super_S("? super S"),
	super_T("? super T"),
	super_U("? super U"),
	super_V("? super V"),
	super_HEAD("? super !head!"),
	super_TAIL("? super !tail!");
	String variableVariant;
	String argumentVariant;
	static EnumSet<BaseType> primitives = EnumSet.of(i, l, d, b, VOID);
	static EnumSet<BaseType> metatypes = EnumSet.of(HEAD, TAIL, extends_HEAD, extends_TAIL, super_HEAD, super_TAIL);
	static EnumSet<BaseType> objects = retainingAll(EnumSet.complementOf(primitives), EnumSet.complementOf(metatypes));

	BaseType(String type) {
		variableVariant = type;
		argumentVariant = type;
	}

	private static <E extends Enum<E>> EnumSet<E> retainingAll(EnumSet<E> set1, EnumSet<E> set2) {
		EnumSet<E> copyOf = EnumSet.copyOf(set1);
		copyOf.retainAll(set2);
		return copyOf;
	}

	BaseType(String callerArgumentType, String calleeVariableType) {
		variableVariant = calleeVariableType;
		argumentVariant = callerArgumentType;
	}

	@Override
	public String get() {
		return variableVariant;
	}

	static Signature unpacked(List<BaseType> t, UnaryOperator<String> ishher) {
		ArrayList<BaseType> list = new ArrayList<>(t);
		BaseType returns = list.remove(list.size() - 1);
		String uncheck = className(returns, list);
		String methodName = methodName(returns, list);
		String checked = ishher.apply(uncheck);
		return new Signature(methodName, list, returns, checked, uncheck);
	}

	static List<List<BaseType>> generated() {
		return asList(//
		  asList(T, b), ///// predicate

		  asList(T, U, VOID), // BI consumer
		  asList(T, U, R), // BI function
		  asList(T, T, T), // BI operator
		  asList(T, U, b), // BI predicate
		  asList(T, VOID), ///// consumer
		  asList(T, R), ///// function

		  asList(T), //////// supplier
		  asList(T, T), ///// unary operator
		  asList(d, d, d), // double binary operator
		  asList(d, VOID), ///// double consumer
		  asList(d, R), ///// double function
		  asList(d, b), ///// double predicate
		  asList(d), //////// double supplier
		  asList(d, i), ///// double to int function
		  asList(d, l), ///// double to long function
		  asList(d, d), ///// double unary operator
		  asList(T, d, VOID), // OBJ double consumer
		  asList(T, U, d), // to double BI function
		  asList(T, d), ///// to double function
		  asList(l, l, l), // long binary operator
		  asList(l, VOID), ///// long consumer
		  asList(l, R), ///// long function
		  asList(l, b), ///// long predicate
		  asList(l), //////// long supplier
		  asList(l, d), ///// long to double function
		  asList(l, i), ///// long to int function
		  asList(l, l), ///// long unary operator
		  asList(T, l, VOID), // OBJ long consumer
		  asList(T, U, l), // to long BI function
		  asList(T, l), ///// to long function
		  asList(i, i, i), // int binary operator
		  asList(i, VOID), ///// int consumer
		  asList(i, R), ///// int function
		  asList(i, b), ///// int predicate
		  asList(i), //////// int supplier
		  asList(i, d), ///// int to double function
		  asList(i, l), ///// int to long function
		  asList(i, i), ///// int unary operator
		  asList(T, i, VOID), // OBJ int consumer
		  asList(T, U, i), // to int BI function
		  asList(T, i) ////// to int function

		);
	}

	private static String methodName(BaseType returnValue, ArrayList<BaseType> list) {
		String part2;
		String part1;
		if(isSupplier(list)) {
			part2 = overload(returnValue, objects);
			part1 = "get";
		} else if(isConsumer(returnValue)) {
			part2 = "";//overload(list.get(0), objects);
			part1 = "accept";
		} else if(isPredicate(returnValue)) {
			part2 = "";
			part1 = "test";
		} else if(isOperator(returnValue, list)) {
			part2 = overload(returnValue, objects);
			part1 = "apply";
		} else {
			part2 = overload(returnValue, objects);
			part1 = "apply";
		}

		return part1 + part2;
	}

	private static BaseType firstType(ArrayList<BaseType> list) {
		return list.get(0);
	}

	private static boolean isSupplier(ArrayList<BaseType> list) {
		return list.size() == 0;
	}

	static String className(BaseType returnValue, ArrayList<BaseType> list) {
		if(isSupplier(list)) {
			String part1 = abbreviation(returnValue, objects);
			return part1 + "Supplier";
		} else if(isOperator(returnValue, list)) {
			String part1 = abbreviation(firstType(list), objects);
			String part2 = plurality(list, "Unary", "Binary", "Ternary", "Quaternary");
			return part1 + part2 + "Operator";
		} else if(isSingleTypeConsumer(returnValue, list)) {
			String part1 = plurality(list, "", "Bi", "Tri", "Quad");
			String part2 = abbreviation(firstType(list), objects);
			return part1 + part2 + "Consumer";
		} else if(isConsumer(returnValue)) {
			String part1 = abbreviations(list);
			return part1 + "Consumer";
		} else if(isPredicate(returnValue)) {
			String part1 = plurality(list, "", "Bi", "Tri", "Quad");
			String part2 = abbreviation(firstType(list), objects);
			return part1 + part2 + "Predicate";
		} else {
			String part1 = abbreviation(firstType(list), objects);
			String part2 = target(returnValue, "To");
			String part3 = plurality(list, "", "Bi", "Tri", "Quad");
			return part1 + part2 + part3 + "Function";
		}
	}

	private static boolean isSingleTypeConsumer(BaseType returnValue, ArrayList<BaseType> list) {
		return isConsumer(returnValue) &&
		list.stream().map(t -> objects.contains(t) ? T : t).distinct().skip(1).count() == 0;
	}

	private static boolean isPredicate(BaseType returnValue) {
		return returnValue == b;
	}

	private static boolean isConsumer(BaseType returnValue) {
		return returnValue == VOID;
	}

	private static String target(BaseType returnValue, String prefix) {
		switch(returnValue) {/*Q*/
			case  i: return prefix +     "Int";
			case  l: return prefix +    "Long";
			case  d: return prefix +  "Double";
			case  b: return prefix + "Boolean";
	  	default: return                 "";
	  }/*E*/
	}

	private static String plurality(ArrayList<BaseType> list, String... names) {
		int exact = list.size() - 1;
		if(exact >= 0 && exact < names.length) {
			return names[exact];
		}
		return "";
	}

	private static String overload(BaseType baseType, EnumSet<BaseType> defaults) {
		String abbreviation = abbreviation(baseType, defaults);
		if(abbreviation.isEmpty()) {
			return "";
		}
		return "As" + abbreviation;
	}

	private static String abbreviations(ArrayList<BaseType> list) {
		EnumSet<BaseType> none = EnumSet.noneOf(BaseType.class);
		return list.stream().map(t -> abbreviation(t, none)).collect(joining());
	}

	private static String abbreviation(BaseType baseType, EnumSet<BaseType> defaults) {
		String specialization;
		if(defaults.contains(baseType)) {
			specialization = "";
		} else {
			switch(baseType) {/*Q*/
        case  i: specialization = "Int"    ; break;
        case  l: specialization = "Long"   ; break;
        case  d: specialization = "Double" ; break;
        case  b: specialization = "Boolean"; break;
        default: specialization = "Obj"    ; break;
        /*E*/
			}
		}
		return specialization;
	}

	private static boolean isOperator(BaseType returnValue, ArrayList<BaseType> list) {
		return list.stream().allMatch(t -> t == returnValue);
	}

	public static boolean isPrimitive(BaseType current) {
		return primitives.contains(current);
	}

	public String asCallVariable(int k) {
		return variableVariant + " t" + k;
	}

	public String asCallVariable(String name) {
		return variableVariant + " " + name;
	}

	public static String asCallArgument(int k) {
		return "t" + k;
	}

	public String asReturn() {
		return argumentVariant;
	}

	public String asDefaultReturn() {
		switch(this) {/*Q*/
      case  i: return "return -0x1;";
      case  l: return "return 100l;";
      case  d: return "return 1.2d;";
      case  b: return "return true;";
      case  VOID: return ";;;; return;";
      default: return "return null;";
      /*E*/
		}
	}
}

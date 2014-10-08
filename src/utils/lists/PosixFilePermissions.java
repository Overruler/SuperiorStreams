package utils.lists;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;

public class PosixFilePermissions {
	public static String toString(Set<PosixFilePermission> permissions) {
		return java.nio.file.attribute.PosixFilePermissions.toString(permissions.toJavaUtilCollection());
	}
	public static Set<PosixFilePermission> fromString(String permissions) {
		return Set.from(java.nio.file.attribute.PosixFilePermissions.fromString(permissions));
	}
	public static FileAttribute<java.util.Set<PosixFilePermission>>
		asFileAttribute(Set<PosixFilePermission> permissions) {
		return java.nio.file.attribute.PosixFilePermissions.asFileAttribute(permissions.toJavaUtilCollection());
	}
}

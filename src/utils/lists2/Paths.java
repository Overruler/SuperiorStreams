package utils.lists2;

import java.net.URI;
import java.nio.file.Path;

public final class Paths {
	public static Path get(String first) {
		return java.nio.file.Paths.get(first);
	}
	public static Path get(String first, String... more) {
		return java.nio.file.Paths.get(first, more);
	}
	public static Path get(URI uri) {
		return java.nio.file.Paths.get(uri);
	}
}

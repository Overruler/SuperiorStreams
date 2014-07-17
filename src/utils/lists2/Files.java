package utils.lists2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitor;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.EnumSet;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import utils.streams2.IOStream;
import utils.streams.functions.IOSupplier;

public class Files {//*Q*
	public static void delete(Path path) throws IOException {java.nio.file.Files.delete(path);}
	public static InputStream 							newInputStream(				Path 			path	, OpenOption... 							options			) throws IOException                        	            	                                	        	                         	{return java.nio.file.Files.newInputStream(				path	,	options			)	            	 	        	 ;}
	public static OutputStream 							newOutputStream(			Path 			path	, OpenOption... 							options			) throws IOException                        	            	                                	        	                         	{return java.nio.file.Files.newOutputStream(			path	,	options			)	            	 	        	 ;}
	public static SeekableByteChannel 					newByteChannel(				Path 			path	, OpenOption... 							options			) throws IOException                        	        		                                	        	                         	{return java.nio.file.Files.newByteChannel(				path	,	options			)	        		 	        	 ;}
	public static DirectoryStream<Path> 				newDirectoryStream(			Path 			dir		) throws IOException                    	            	                                            	        		                                	        	                         	{return java.nio.file.Files.newDirectoryStream(			dir		)	            	 	        		 	        	 ;}
	public static DirectoryStream<Path> 				newDirectoryStream(			Path 			dir		, String 									glob			) throws IOException                        	        		                                	        	                         	{return java.nio.file.Files.newDirectoryStream(			dir		,	glob			)	        		 	        	 ;}
	public static DirectoryStream<Path> 				newDirectoryStream(			Path 			dir		, DirectoryStream.Filter<? super Path> 		filter			) throws IOException                        	        		                                	        	                         	{return java.nio.file.Files.newDirectoryStream(			dir		,	filter			)	        		 	        	 ;}
	public static Path 									createFile(					Path 			path	, FileAttribute<?>... 						attrs			) throws IOException                        	        		                                	        	                         	{return java.nio.file.Files.createFile(					path	,	attrs			)	        		 	        	 ;}
	public static Path 									createDirectory(			Path 			dir		, FileAttribute<?>... 						attrs			) throws IOException                        	        		                                	        	                         	{return java.nio.file.Files.createDirectory(			dir		,	attrs			)	        		 	        	 ;}
	public static Path 									createDirectories(			Path 			dir		, FileAttribute<?>... 						attrs			) throws IOException                        	        		                                	        	                         	{return java.nio.file.Files.createDirectories(			dir		,	attrs			)	        		 	        	 ;}
	public static Path 									createTempFile(				Path 			dir		, String 									prefix			, String 										suffix			, FileAttribute<?>... 				attrs		) throws IOException 		{return java.nio.file.Files.createTempFile(				dir		,	prefix			,	suffix			,	attrs		);}
	public static Path 									createTempFile(				String 			prefix	, String 									suffix			, FileAttribute<?>... 							attrs			) throws IOException            	        	                         	{return java.nio.file.Files.createTempFile(				prefix	,	suffix			,	attrs			)	        	 ;}
	public static Path 									createTempDirectory(		Path 			dir		, String 									prefix			, FileAttribute<?>... 							attrs			) throws IOException            	        	                         	{return java.nio.file.Files.createTempDirectory(		dir		,	prefix			,	attrs			)	        	 ;}
	public static Path 									createTempDirectory(		String 			prefix	, FileAttribute<?>... 						attrs			) throws IOException  							     			                                	        	                         	{return java.nio.file.Files.createTempDirectory(		prefix	,	attrs			)	     			 	        	 ;}
	public static Path 									createSymbolicLink(			Path 			link	, Path 										target			, FileAttribute<?>... 							attrs			) throws IOException            	        	                         	{return java.nio.file.Files.createSymbolicLink(			link	,	target			,	attrs			)	        	 ;}
	public static Path 									createLink(					Path 			link	, Path 										existing		) throws IOException                        	        		                                	        	                         	{return java.nio.file.Files.createLink(					link	,	existing		)	        		 	        	 ;}
	public static boolean								deleteIfExists(				Path 			path	) throws IOException                		            	                                            	        		                                	        	                         	{return java.nio.file.Files.deleteIfExists(				path	)	            	 	        		 	        	 ;}
	public static Path									copy(						Path 			source	, Path 										target			, CopyOption... 								options			) throws IOException            	        	                         	{return java.nio.file.Files.copy(						source	,	target			,	options			)	        	 ;}
	public static Path									move(						Path 			source	, Path 										target			, CopyOption... 								options			) throws IOException            	        	                         	{return java.nio.file.Files.move(						source	,	target			,	options			)	        	 ;}
	public static Path									readSymbolicLink(			Path 			link	) throws IOException                		            	                                            	        		                                	        	                         	{return java.nio.file.Files.readSymbolicLink(			link	)	            	 	        		 	        	 ;}
	public static FileStore								getFileStore(				Path 			path	) throws IOException                		            	                                            	        		                                	        	                         	{return java.nio.file.Files.getFileStore(				path	)	            	 	        		 	        	 ;}
	public static boolean								isSameFile(					Path 			path	, Path 										path2			) throws IOException                        	        		                                	        	                         	{return java.nio.file.Files.isSameFile(					path	,	path2			)	        		 	        	 ;}
	public static boolean								isHidden(					Path 			path	) throws IOException                		            	                                            	        		                                	        	                         	{return java.nio.file.Files.isHidden(					path	)	            	 	        		 	        	 ;}
	public static String								probeContentType(			Path 			path	) throws IOException                		            	                                            	        		                                	        	                         	{return java.nio.file.Files.probeContentType(			path	)	            	 	        		 	        	 ;}
	public static <V extends FileAttributeView> V 		getFileAttributeView(		Path 			path	, Class<V> 									type			, LinkOption... 								options			)                               	        	                         	{return java.nio.file.Files.getFileAttributeView(		path	,	type			,	options			)	        	 ;}
	public static <A extends BasicFileAttributes> A 	readAttributes(				Path 			path	, Class<A> 									type			, LinkOption... 								options			) throws IOException            	        	                         	{return java.nio.file.Files.readAttributes(				path	,	type			,	options			)	        	 ;}
	public static Path 									setAttribute(				Path 			path	, String 									attribute		, Object 										value			, LinkOption... 					options		) throws IOException 		{return java.nio.file.Files.setAttribute(				path	,	attribute		,	value			,	options		);}
	public static Object 								getAttribute(				Path 			path	, String 									attribute		, LinkOption... 								options			) throws IOException            	        	                         	{return java.nio.file.Files.getAttribute(				path	,	attribute		,	options			)	        	 ;}
	public static UserPrincipal 						getOwner(					Path 			path	, LinkOption... 							options			) throws IOException                        	            	                                	        	                         	{return java.nio.file.Files.getOwner(					path	,	options			)	            	 	        	 ;}
	public static Path 									setOwner(					Path 			path	, UserPrincipal 							owner			) throws IOException                        	            	                                	        	                         	{return java.nio.file.Files.setOwner(					path	,	owner			)	            	 	        	 ;}
	public static boolean 								isSymbolicLink(				Path 			path	)               							            	                                            	            	                                	        	                         	{return java.nio.file.Files.isSymbolicLink(				path	)	            	 	            	 	        	 ;}
	public static boolean 								isDirectory(				Path 			path	, LinkOption... 							options			)                                           	            	                                	        	                         	{return java.nio.file.Files.isDirectory(				path	,	options			)	            	 	        	 ;}
	public static boolean 								isRegularFile(				Path 			path	, LinkOption... 							options			)                                           	            	                                	        	                         	{return java.nio.file.Files.isRegularFile(				path	,	options			)	            	 	        	 ;}
	public static FileTime 								getLastModifiedTime(		Path 			path	, LinkOption... 							options			) throws IOException                        	            	                                	        	                         	{return java.nio.file.Files.getLastModifiedTime(		path	,	options			)	            	 	        	 ;}
	public static Path 									setLastModifiedTime(		Path 			path	, FileTime 									time			) throws IOException                        	            	                                	        	                         	{return java.nio.file.Files.setLastModifiedTime(		path	,	time			)	            	 	        	 ;}
	public static long 									size(						Path 			path	) throws IOException                		            	                                            	            	                                	        	                         	{return java.nio.file.Files.size(						path	)	            	 	            	 	        	 ;}
	public static boolean 								exists(						Path 			path	, LinkOption... 							options			)                                           	            	                                	        	                         	{return java.nio.file.Files.exists(						path	,	options			)	            	 	        	 ;}
	public static boolean 								notExists(					Path 			path	, LinkOption... 							options			)                                           	            	                                	        	                         	{return java.nio.file.Files.notExists(					path	,	options			)	            	 	        	 ;}
	public static boolean 								isReadable(					Path 			path	)                                   		            	                                            	            	                                	        	                         	{return java.nio.file.Files.isReadable(					path	)	            	 	            	 	        	 ;}
	public static boolean 								isWritable(					Path 			path	)                                   		            	                                            	            	                                	        	                         	{return java.nio.file.Files.isWritable(					path	)	            	 	            	 	        	 ;}
	public static boolean 								isExecutable(				Path 			path	)                                   		            	                                            	            	                                	        	                         	{return java.nio.file.Files.isExecutable(				path	)	            	 	            	 	        	 ;}
	public static Path 									walkFileTree(				Path 			start	, FileVisitor<? super Path> 				visitor			) throws IOException                        	            	                                	        	                         	{return java.nio.file.Files.walkFileTree(				start	,	visitor			)	            	 	        	 ;}
	public static BufferedReader 						newBufferedReader(			Path 			path	, Charset 									cs				) throws IOException                        	            	                                	        	                         	{return java.nio.file.Files.newBufferedReader(			path	,	cs				)	            	 	        	 ;}
	public static BufferedReader 						newBufferedReader(			Path 			path	) throws IOException            			            	                                            	            	                                	        	                         	{return java.nio.file.Files.newBufferedReader(			path	)	            	 	            	 	        	 ;}
	public static BufferedWriter 						newBufferedWriter(			Path 			path	, Charset 									cs				, OpenOption... 								options			) throws IOException            	        	                         	{return java.nio.file.Files.newBufferedWriter(			path	,	cs				,	options			)	        	 ;}
	public static BufferedWriter 						newBufferedWriter(			Path 			path	, OpenOption... 							options			) throws IOException                        	        		                                	        	                         	{return java.nio.file.Files.newBufferedWriter(			path	,	options			)	        		 	        	 ;}
	public static long 									copy(						InputStream 	in		, Path 										target			, CopyOption... 								options			) throws IOException            	        	                         	{return java.nio.file.Files.copy(						in		,	target			,	options			)	        	 ;}
	public static long 									copy(						Path 			source	, OutputStream 								out				) throws IOException                        	        		                                	        	                         	{return java.nio.file.Files.copy(						source	,	out				)	        		 	        	 ;}
	public static byte[] 								readAllBytes(				Path 			path	) throws IOException            			            	                                            	        		                                	        	                         	{return java.nio.file.Files.readAllBytes(				path	)	            	 	        		 	        	 ;}
	public static Path 									write(						Path 			path	, byte[] 									bytes			, OpenOption... 								options			) throws IOException            	        	                         	{return java.nio.file.Files.write(						path	,	bytes			,	options			)	        	 ;}
	public static Path 									write(						Path 			path	, Iterable<? extends CharSequence> 			lines			, Charset 										cs				, OpenOption... 					options		) throws IOException     	{return java.nio.file.Files.write(						path	,	lines			,	cs				,	options		);}
	public static Path 									write(						Path 			path	, Iterable<? extends CharSequence> 			lines			, OpenOption... 								options			) throws IOException            	        	                         	{return java.nio.file.Files.write(						path	,	lines			,	options			)	        	 ;}//*E*
	public static <O extends Enum<O> & OpenOption> SeekableByteChannel newByteChannel(
		Path path,
		EnumSet<O> options,
		FileAttribute<?>... attrs) throws IOException {
		return java.nio.file.Files.newByteChannel(path, options, attrs);
	}
	public static ArrayList<String> readAllLines(Path path, Charset cs) throws IOException {
		return new ArrayList<>(java.nio.file.Files.readAllLines(path, cs));
	}
	public static HashMap<String, Object> readAttributes(Path path, String attributes, LinkOption... options)
		throws IOException {
		return new HashMap<>(java.nio.file.Files.readAttributes(path, attributes, options));
	}
	public static HashSet<PosixFilePermission> getPosixFilePermissions(Path path, LinkOption... options)
		throws IOException {
		return new HashSet<>(java.nio.file.Files.getPosixFilePermissions(path, options));
	}
	public static Path setPosixFilePermissions(Path path, Set<PosixFilePermission> perms) throws IOException {
		return java.nio.file.Files.setPosixFilePermissions(path, perms.toJavaSet());
	}
	public static Path walkFileTree(Path start, Set<FileVisitOption> options, int maxDepth, FileVisitor<Path> visitor)
		throws IOException {
		return java.nio.file.Files.walkFileTree(start, options.toJavaSet(), maxDepth, visitor);
	}
	public static ArrayList<String> readAllLines(Path path) throws IOException {
		return new ArrayList<>(java.nio.file.Files.readAllLines(path));
	}
	public static IOStream<Path> list(Path dir) {
		IOSupplier<Stream<Path>> maker = () -> java.nio.file.Files.list(dir);
		return new IOStream<>(maker.uncheck(IOException.class));
	}
	public static IOStream<Path> walk(Path start, int maxDepth, FileVisitOption... options) {
		IOSupplier<Stream<Path>> maker = () -> java.nio.file.Files.walk(start, maxDepth, options);
		return new IOStream<>(maker.uncheck(IOException.class));
	}
	public static IOStream<Path> walk(Path start, FileVisitOption... options) {
		IOSupplier<Stream<Path>> maker = () -> java.nio.file.Files.walk(start, options);
		return new IOStream<>(maker.uncheck(IOException.class));
	}
	public static IOStream<Path> find(
		Path start,
		int maxDepth,
		BiPredicate<Path, BasicFileAttributes> matcher,
		FileVisitOption... options) {
		IOSupplier<Stream<Path>> maker = () -> java.nio.file.Files.find(start, maxDepth, matcher, options);
		return new IOStream<>(maker.uncheck(IOException.class));
	}
	public static IOStream<String> lines(Path path, Charset cs) {
		IOSupplier<Stream<String>> maker = () -> java.nio.file.Files.lines(path, cs);
		return new IOStream<>(maker.uncheck(IOException.class));
	}
	public static IOStream<String> lines(Path path) {
		IOSupplier<Stream<String>> maker = () -> java.nio.file.Files.lines(path);
		return new IOStream<>(maker.uncheck(IOException.class));
	}
}
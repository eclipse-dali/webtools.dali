/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * <code>Classpath</code> models a Java classpath, which consists of a list of
 * {@link Entry}s, each of which contain Java classes. The classpath can return
 * the names of classes found in it etc. There are a number of static
 * convenience methods that can be use to construct <code>Classpath</code>s
 * corresponding to the Java classpath etc.
 */
public class Classpath
	implements Serializable
{
	/** The entries in the classpath */
	private final Entry[] entries;

	private static final long serialVersionUID = 1L;


	// ********** static methods **********

	// ***** factory methods for "standard" classpaths *****

	/**
	 * Return the Java "boot" classpath. This includes <code>rt.jar</code>.
	 */
	public static Classpath bootClasspath() {
		return new Classpath(System.getProperty("sun.boot.class.path")); //$NON-NLS-1$
	}
	
	/**
	 * Return a "virtual classpath" that contains all the jars
	 * that would be used by the Java Extension Mechanism.
	 */
	public static Classpath javaExtensionClasspath() {
		File[] dirs = javaExtensionDirectories();
		List<String> jarFileNames = new ArrayList<String>();
		for (File dir : dirs) {
			if (dir.isDirectory()) {
				addJarFileNamesTo(dir, jarFileNames);
			}
		}
		return new Classpath(jarFileNames);
	}

	/**
	 * Return the Java "system" classpath.
	 */
	public static Classpath javaClasspath() {
		return new Classpath(System.getProperty("java.class.path")); //$NON-NLS-1$
	}

	/**
	 * Return the unretouched "complete" classpath.
	 * This includes the boot classpath, the Java Extension
	 * Mechanism classpath, and the normal "system" classpath.
	 */
	public static Classpath completeClasspath() {
		return new Classpath(new Classpath[] {
				bootClasspath(),
				javaExtensionClasspath(),
				javaClasspath()
		});
	}

	/**
	 * Return a classpath that contains the location of the specified class.
	 */
	public static Classpath classpathFor(Class<?> javaClass) {
		return new Classpath(locationFor(javaClass));
	}


	// ***** file => class *****

	/**
	 * Convert a relative file name to a class name; this will work for
	 * any file that has a single extension beyond the base
	 * class name:<ul>
	 * <li><code>"java/lang/String.class"</code> is converted to <code>"java.lang.String"</code>
	 * <li><code>"java/lang/String.java"</code> is converted to <code>"java.lang.String"</code>
	 * </ul>
	 */
	public static String convertToClassName(String classFileName) {
		String className = FileTools.stripExtension(classFileName);
		// do this for archive entry names
		className = className.replace('/', '.');
		// do this for O/S-specific file names
		if (File.separatorChar != '/') {
			className = className.replace(File.separatorChar, '.');
		}
		return className;
	}

	/**
	 * Convert a file to a class name;
	 * e.g. <code>File(java/lang/String.class)</code> is converted to
	 * <code>"java.lang.String"</code>.
	 */
	public static String convertToClassName(File classFile) {
		return convertToClassName(classFile.getPath());
	}

	/**
	 * Convert a relative file name to a class;
	 * e.g. <code>"java/lang/String.class"</code> is converted to
	 * <code>java.lang.String.class</code>.
	 */
	public static Class<?> convertToClass(String classFileName) throws ClassNotFoundException {
		return Class.forName(convertToClassName(classFileName));
	}

	/**
	 * Convert a relative file to a class;
	 * e.g. <code>File(java/lang/String.class)</code> is converted to
	 * <code>java.lang.String.class</code>.
	 */
	public static Class<?> convertToClass(File classFile) throws ClassNotFoundException {
		return convertToClass(classFile.getPath());
	}


	// ***** class => JAR entry *****

	/**
	 * Convert a class name to an archive entry name base;
	 * e.g. <code>"java.lang.String"</code> is converted to
	 * <code>"java/lang/String"</code>.
	 */
	public static String convertToArchiveEntryNameBase(String className) {
		return className.replace('.', '/');
	}
	
	/**
	 * Convert a class to an archive entry name base;
	 * e.g. <code>java.lang.String.class</code> is converted to
	 * <code>"java/lang/String"</code>.
	 */
	public static String convertToArchiveEntryNameBase(Class<?> javaClass) {
		return convertToArchiveEntryNameBase(javaClass.getName());
	}
	
	/**
	 * Convert a class name to an archive class file entry name;
	 * e.g. <code>"java.lang.String"</code> is converted to
	 * <code>"java/lang/String.class"</code>.
	 */
	public static String convertToArchiveClassFileEntryName(String className) {
		return convertToArchiveEntryNameBase(className) + ".class"; //$NON-NLS-1$
	}
	
	/**
	 * Convert a class to an archive class file entry name;
	 * e.g. <code>java.lang.String.class</code> is converted to
	 * <code>"java/lang/String.class"</code>.
	 */
	public static String convertToArchiveClassFileEntryName(Class<?> javaClass) {
		return convertToArchiveClassFileEntryName(javaClass.getName());
	}
	

	// ***** class => file (.class or .java) *****

	/**
	 * Convert a class name to a file name base for the current O/S;
	 * e.g. <code>"java.lang.String"</code> is converted to
	 * <code>"java/lang/String"</code> on Unix and
	 * <code>"java\\lang\\String"</code> on Windows.
	 */
	public static String convertToFileNameBase(String className) {
		return className.replace('.', File.separatorChar);
	}
	
	/**
	 * Convert a class to a file name base for the current O/S;
	 * e.g. <code>java.lang.String.class</code> is converted to
	 * <code>"java/lang/String"</code> on Unix and
	 * <code>"java\\lang\\String"</code> on Windows.
	 */
	public static String convertToFileNameBase(Class<?> javaClass) {
		return convertToFileNameBase(javaClass.getName());
	}
	
	/**
	 * Convert a class name to a class file name for the current O/S;
	 * e.g. <code>"java.lang.String"</code> is converted to
	 * <code>"java/lang/String.class"</code> on Unix and
	 * <code>"java\\lang\\String.class"</code> on Windows.
	 */
	public static String convertToClassFileName(String className) {
		return convertToFileNameBase(className) + ".class"; //$NON-NLS-1$
	}
	
	/**
	 * Convert a class to a class file name for the current O/S;
	 * e.g. <code>java.lang.String.class</code> is converted to
	 * <code>"java/lang/String.class"</code> on Unix and
	 * <code>"java\\lang\\String.class"</code> on Windows.
	 */
	public static String convertToClassFileName(Class<?> javaClass) {
		return convertToClassFileName(javaClass.getName());
	}
	
	/**
	 * Convert a class name to a class file for the current O/S;
	 * e.g. <code>"java.lang.String"</code> is converted to
	 * <code>File(java/lang/String.class)</code>.
	 */
	public static File convertToClassFile(String className) {
		return new File(convertToClassFileName(className));
	}
	
	/**
	 * Convert a class to a class file for the current O/S;
	 * e.g. <code>java.lang.String.class</code> is converted to
	 * <code>File(java/lang/String.class)</code>.
	 */
	public static File convertToClassFile(Class<?> javaClass) {
		return convertToClassFile(javaClass.getName());
	}
	
	/**
	 * Convert a class name to a java file name for the current O/S;
	 * e.g. <code>"java.lang.String"</code> is converted to
	 * <code>"java/lang/String.java"</code> on Unixl and
	 * <code>"java\\lang\\String.java"</code> on Windows.
	 */
	public static String convertToJavaFileName(String className) {
		return convertToFileNameBase(className) + ".java"; //$NON-NLS-1$
	}

	/**
	 * Convert a class to a java file name for the current O/S;
	 * e.g. <code>java.lang.String.class</code> is converted to
	 * <code>"java/lang/String.java"</code> on Unix and
	 * <code>"java\\lang\\String.java"</code> on Windows.
	 */
	public static String convertToJavaFileName(Class<?> javaClass) {
		return convertToJavaFileName(javaClass.getName());
	}

	/**
	 * Convert a class name to a java file for the current O/S;
	 * e.g. <code>"java.lang.String"</code> is converted to
	 * <code>File(java/lang/String.java)</code>.
	 */
	public static File convertToJavaFile(String className) {
		return new File(convertToJavaFileName(className));
	}

	/**
	 * Convert a class to a java file for the current O/S;
	 * e.g. <code>java.lang.String.class</code> is converted to
	 * <code>File(java/lang/String.java)</code>.
	 */
	public static File convertToJavaFile(Class<?> javaClass) {
		return convertToJavaFile(javaClass.getName());
	}


	// ***** class => resource *****

	/**
	 * Convert a class to a resource name;
	 * e.g. <code>java.lang.String.class</code> is converted to
	 * <code>"/java/lang/String.class"</code>.
	 */
	public static String convertToResourceName(Class<?> javaClass) {
		return '/' + convertToArchiveClassFileEntryName(javaClass);
	}

	/**
	 * Convert a class to a resource;
	 * e.g. <code>java.lang.String.class</code> is converted to
	 * <code>URL(jar:file:/C:/jdk/1.4.2_04/jre/lib/rt.jar!/java/lang/String.class)</code>.
	 */
	public static URL convertToResource(Class<?> javaClass) {
		return javaClass.getResource(convertToResourceName(javaClass));
	}


	// ***** utilities *****

	/**
	 * Return whether the specified file is an archive file;
	 * i.e. its name ends with <code>".zip"</code> or <code>".jar"</code>.
	 */
	public static boolean fileNameIsArchive(String fileName) {
		String ext = FileTools.extension(fileName).toLowerCase();
		return ext.equals(".jar") || ext.equals(".zip"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * Return whether the specified file is an archive file;
	 * i.e. its name ends with <code>".zip"</code> or <code>".jar"</code>.
	 */
	public static boolean fileIsArchive(File file) {
		return fileNameIsArchive(file.getName());
	}
	
	/**
	 * Return what should be the fully-qualified file name
	 * for the JRE runtime JAR;
	 * e.g. <code>"C:\jdk1.4.2_04\jre\lib\rt.jar"</code>.
	 */
	public static String rtJarName() {
		return locationFor(java.lang.Object.class);
	}
	
	/**
	 * Return the location from where the specified class was loaded.
	 */
	public static String locationFor(Class<?> javaClass) {
		URL url = convertToResource(javaClass);
		String path;
		try {
			path = FileTools.buildFile(url).getPath();
		} catch (URISyntaxException ex) {
			throw new RuntimeException(ex);
		}
		String protocol = url.getProtocol().toLowerCase();
		if (protocol.equals("jar")) { //$NON-NLS-1$
			// if the class is in a JAR, the URL will look something like this:
			//     jar:file:/C:/jdk/1.4.2_04/jre/lib/rt.jar!/java/lang/String.class
			return path.substring(0, path.indexOf('!'));
		} else if (protocol.equals("file")) { //$NON-NLS-1$
			// if the class is in a directory, the URL will look something like this:
			//     file:/C:/dev/main/mwdev/class/org/eclipse/dali/utility/Classpath.class
			return path.substring(0, path.length() - convertToClassFileName(javaClass).length() - 1);
		} else if (protocol.equals("bundleresource")) { //$NON-NLS-1$
			// if the class is in a bundle resource (Eclipse?), the URL will look something like this:
			//     bundleresource://43/org/eclipse/dali/utility/Classpath.class
			return path.substring(0, path.length() - convertToClassFileName(javaClass).length() - 1);
		}

		throw new IllegalStateException(url.toString());
	}
	
	/**
	 * Return the directories used by the Java Extension Mechanism.
	 */
	public static File[] javaExtensionDirectories() {
		return convertToFiles(javaExtensionDirectoryNames());
	}

	/**
	 * Return the directory names used by the Java Extension Mechanism.
	 */
	public static String[] javaExtensionDirectoryNames() {
		return System.getProperty("java.ext.dirs").split(File.pathSeparator); //$NON-NLS-1$
	}


	// ***** internal *****

	private static File[] convertToFiles(String[] fileNames) {
		File[] files = new File[fileNames.length];
		for (int i = fileNames.length; i-- > 0; ) {
			files[i] = new File(fileNames[i]);
		}
		return files;
	}

	private static void addJarFileNamesTo(File dir, List<String> jarFileNames) {
		File[] jarFiles = jarFilesIn(dir);
		for (File jarFile : jarFiles) {
			jarFileNames.add(FileTools.canonicalFile(jarFile).getPath());
		}
	}

	private static File[] jarFilesIn(File directory) {
		return directory.listFiles(jarFileFilter());
	}

	private static FileFilter jarFileFilter() {
		return new FileFilter() {
			public boolean accept(File file) {
				return FileTools.extension(file.getName()).toLowerCase().equals(".jar"); //$NON-NLS-1$
			}
		};
	}


	// ********** constructors **********

	/**
	 * Construct a classpath with the specified entries.
	 */
	private Classpath(Entry[] entries) {
		super();
		this.entries = entries;
	}

	/**
	 * Construct a classpath with the specified entries.
	 */
	public Classpath(String... fileNames) {
		this(buildEntries(fileNames));
	}

	/**
	 * Skip empty file names because they will end up expanding to the current
	 * working directory, which is not what we want. Empty file names actually
	 * occur with some frequency; such as when the classpath has been built up
	 * dynamically with too many separators. For example:<pre>
	 *     "C:\dev\foo.jar;;C:\dev\bar.jar"
	 * </pre>will be parsed into three file names:<pre>
	 *     { "C:\dev\foo.jar", "", "C:\dev\bar.jar" }
	 * </pre>
	 */
	private static Entry[] buildEntries(String[] fileNames) {
		List<Entry> entries = new ArrayList<Entry>();
		for (String fileName : fileNames) {
			if ((fileName != null) && (fileName.length() != 0)) {
				entries.add(new Entry(fileName));
			}
		}
		return entries.toArray(new Entry[entries.size()]);
	}

	/**
	 * Construct a classpath with the specified path.
	 */
	public Classpath(String path) {
		this(path.split(File.pathSeparator));
	}

	/**
	 * Construct a classpath with the specified entries.
	 */
	public Classpath(Iterable<String> fileNames) {
		this(ArrayTools.array(fileNames, EMPTY_STRING_ARRAY));
	}
	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * Consolidate the specified classpaths into a single classpath.
	 */
	public Classpath(Classpath... classpaths) {
		this(consolidateEntries(classpaths));
	}

	private static Entry[] consolidateEntries(Classpath[] classpaths) {
		List<Entry> entries = new ArrayList<Entry>();
		for (Classpath classpath : classpaths) {
			CollectionTools.addAll(entries, classpath.getEntries());
		}
		return entries.toArray(new Entry[entries.size()]);
	}


	// ********** public API **********

	/**
	 * Return the classpath's entries.
	 */
	public Iterable<Entry> getEntries() {
		return new ArrayIterable<Entry>(this.entries);
	}

	/**
	 * Return the classpath's path.
	 */
	public String getPath() {
		int max = this.entries.length - 1;
		if (max == -1) {
			return ""; //$NON-NLS-1$
		}
		StringBuilder sb = new StringBuilder(2000);
		// stop one short of the end of the array
		for (int i = 0; i < max; i++) {
			sb.append(this.entries[i].getFileName());
			sb.append(File.pathSeparatorChar);
		}
		sb.append(this.entries[max].getFileName());
		return sb.toString();
	}

	/**
	 * Search the classpath for the specified (unqualified) file
	 * and return its entry. Return null if an entry is not found.
	 * For example, you could use this method to find the entry
	 * for <code>"rt.jar"</code> or <code>"toplink.jar"</code>.
	 */
	public Entry getEntryForFileNamed(String shortFileName) {
		for (Entry entry : this.entries) {
			if (entry.getFile().getName().equals(shortFileName)) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * Return the first entry file in the classpath
	 * that contains the specified class.
	 * Return null if an entry is not found.
	 */
	public Entry getEntryForClassNamed(String className) {
		String relativeClassFileName = convertToClassFileName(className);
		String archiveEntryName = convertToArchiveClassFileEntryName(className);
		for (Entry entry : this.entries) {
			if (entry.contains(relativeClassFileName, archiveEntryName)) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * Return the names of all the classes discovered on the classpath,
	 * with duplicates removed.
	 * @see #classNames()
	 */
	public Iterable<String> getClassNames() {
		return this.getClassNames(Filter.Null.<String>instance());
	}

	/**
	 * Return the names of all the classes discovered on the classpath
	 * and accepted by the specified filter, with duplicates removed.
	 * @see #classNames(Filter)
	 */
	public Iterable<String> getClassNames(Filter<String> filter) {
		Collection<String> classNames = new HashSet<String>(10000);
		this.addClassNamesTo(classNames, filter);
		return classNames;
	}

	/**
	 * Add the names of all the classes discovered on the classpath
	 * to the specified collection.
	 */
	public void addClassNamesTo(Collection<String> classNames) {
		this.addClassNamesTo(classNames, Filter.Null.<String>instance());
	}

	/**
	 * Add the names of all the classes discovered on the classpath
	 * and accepted by the specified filter to the specified collection.
	 */
	public void addClassNamesTo(Collection<String> classNames, Filter<String> filter) {
		for (Entry entry : this.entries) {
			entry.addClassNamesTo(classNames, filter);
		}
	}

	/**
	 * Return the names of all the classes discovered on the classpath.
	 * Just a bit more performant than {@link #getClassNames()}.
	 */
	public Iterator<String> classNames() {
		return this.classNames(Filter.Null.<String>instance());
	}

	/**
	 * Return the names of all the classes discovered on the classpath
	 * that are accepted by the specified filter.
	 * Just a bit more performant than {@link #getClassNames(Filter)}.
	 */
	public Iterator<String> classNames(Filter<String> filter) {
		return new CompositeIterator<String>(this.entryClassNamesIterators(filter));
	}

	private Iterator<Iterator<String>> entryClassNamesIterators(final Filter<String> filter) {
		return new TransformationIterator<Entry, Iterator<String>>(new ArrayIterator<Entry>(this.entries)) {
			@Override
			protected Iterator<String> transform(Entry entry) {
				return entry.classNames(filter);
			}
		};
	}

	/**
	 * Return a "compressed" version of the classpath with its
	 * duplicate entries eliminated.
	 */
	public Classpath compressed() {
		return new Classpath(ArrayTools.removeDuplicateElements(this.entries));
	}

	/**
	 * Convert the classpath to an array of URLs
	 * (that can be used to instantiate a {@link java.net.URLClassLoader}).
	 */
	public Iterable<URL> getURLs() {
		int len = this.entries.length;
		URL[] urls = new URL[len];
		for (int i = 0; i < len; i++) {
			urls[i] = this.entries[i].getURL();
		}
		return new ArrayIterable<URL>(urls);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getPath());
	}


	// ********** inner class **********

	/**
	 * <code>Entry</code> models a Java classpath entry, which can be either a
	 * directory containing <code>.class</code> files or a JAR file (or,
	 * similarly, a <code>.zip</code> file). The entry can return the names of
	 * classes found in it etc.
	 */
	public static class Entry implements Serializable {
		private final String fileName;
		private final File file;
		private final File canonicalFile;

		private static final long serialVersionUID = 1L;

		Entry(String fileName) {
			super();
			if ((fileName == null) || (fileName.length() == 0)) {
				throw new IllegalArgumentException("'fileName' must be non-empty"); //$NON-NLS-1$
			}
			this.fileName = fileName;
			this.file = new File(fileName);
			this.canonicalFile = FileTools.canonicalFile(this.file);
		}

		public String getFileName() {
			return this.fileName;
		}

		public File getFile() {
			return this.file;
		}

		public File getCanonicalFile() {
			return this.canonicalFile;
		}

		public String getCanonicalFileName() {
			return this.canonicalFile.getAbsolutePath();
		}

		@Override
		public boolean equals(Object o) {
			if ( ! (o instanceof Entry)) {
				return false;
			}
			return ((Entry) o).canonicalFile.equals(this.canonicalFile);
		}

		@Override
		public int hashCode() {
			return this.canonicalFile.hashCode();
		}

		/**
		 * Return the entry's "canonical" URL.
		 */
		public URL getURL() {
			try {
				return this.canonicalFile.toURI().toURL();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}

		/**
		 * Return whether the entry contains the specified class.
		 */
		public boolean contains(Class<?> javaClass) {
			return this.contains(javaClass.getName());
		}

		/**
		 * Return whether the entry contains the specified class.
		 */
		public boolean contains(String className) {
			return this.contains(convertToClassFileName(className), convertToArchiveClassFileEntryName(className));
		}

		/**
		 * Return whether the entry contains either the specified relative
		 * class file or the specified archive entry.
		 * Not the prettiest signature, but it's internal....
		 */
		boolean contains(String relativeClassFileName, String archiveEntryName) {
			if ( ! this.canonicalFile.exists()) {
				return false;
			}
			if (this.canonicalFile.isDirectory() && (new File(this.canonicalFile, relativeClassFileName)).exists()) {
				return true;
			}
			return (fileIsArchive(this.canonicalFile) && this.archiveContainsEntry(archiveEntryName));
		}

		/**
		 * Return whether the entry's archive contains the specified entry.
		 */
		private boolean archiveContainsEntry(String zipEntryName) {
			ZipFile zipFile = null;
			ZipEntry zipEntry = null;
			try {
				zipFile = new ZipFile(this.canonicalFile);
				zipEntry = zipFile.getEntry(zipEntryName);
			} catch (IOException ex) {
				// something is wrong, leave the entry null
			} finally {
				try {
					if (zipFile != null) {
						zipFile.close();
					}
				} catch (IOException ex) {
					zipEntry = null;	// something is wrong, clear out the entry
				}
			}
			return zipEntry != null;
		}

		/**
		 * Return the names of all the classes discovered in the entry.
		 * @see #classNames()
		 */
		public Iterable<String> getClassNames() {
			return this.getClassNames(Filter.Null.<String>instance());
		}

		/**
		 * Return the names of all the classes discovered in the entry
		 * and accepted by the specified filter.
		 * @see #classNames(Filter)
		 */
		public Iterable<String> getClassNames(Filter<String> filter) {
			Collection<String> classNames = new ArrayList<String>(2000);
			this.addClassNamesTo(classNames, filter);
			return classNames;
		}

		/**
		 * Add the names of all the classes discovered in the entry
		 * to the specified collection.
		 */
		public void addClassNamesTo(Collection<String> classNames) {
			this.addClassNamesTo(classNames, Filter.Null.<String>instance());
		}

		/**
		 * Add the names of all the classes discovered in the entry
		 * and accepted by the specified filter to the specified collection.
		 */
		public void addClassNamesTo(Collection<String> classNames, Filter<String> filter) {
			if (this.canonicalFile.exists()) {
				if (this.canonicalFile.isDirectory()) {
					this.addClassNamesForDirectoryTo(classNames, filter);
				} else if (fileIsArchive(this.canonicalFile)) {
					this.addClassNamesForArchiveTo(classNames, filter);
				}
			}
		}

		/**
		 * Add the names of all the classes discovered
		 * under the entry's directory and accepted by
		 * the specified filter to the specified collection.
		 */
		private void addClassNamesForDirectoryTo(Collection<String> classNames, Filter<String> filter) {
			int start = this.canonicalFile.getAbsolutePath().length() + 1;
			for (Iterator<File> stream = this.classFilesForDirectory(); stream.hasNext(); ) {
				String className = convertToClassName(stream.next().getAbsolutePath().substring(start));
				if (filter.accept(className)) {
					classNames.add(className);
				}
			}
		}

		/**
		 * Return an iterator on all the class files discovered
		 * under the entry's directory.
		 */
		private Iterator<File> classFilesForDirectory() {
			return new FilteringIterator<File>(FileTools.filesInTree(this.canonicalFile)) {
				@Override
				protected boolean accept(File next) {
					return Entry.this.fileNameMightBeForClassFile(next.getName());
				}
			};
		}

		/**
		 * Add the names of all the classes discovered
		 * in the entry's archive file and accepted by the
		 * specified filter to the specified collection.
		 */
		private void addClassNamesForArchiveTo(Collection<String> classNames, Filter<String> filter) {
			ZipFile zipFile = null;
			try {
				zipFile = new ZipFile(this.canonicalFile);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			for (Enumeration<? extends ZipEntry> stream = zipFile.entries(); stream.hasMoreElements(); ) {
				ZipEntry zipEntry = stream.nextElement();
				String zipEntryName = zipEntry.getName();
				if (this.fileNameMightBeForClassFile(zipEntryName)) {
					String className = convertToClassName(zipEntryName);
					if (filter.accept(className)) {
						classNames.add(className);
					}
				}
			}
			try {
				zipFile.close();
			} catch (IOException ex) {
				return;
			}
		}

		/**
		 * Return whether the specified file might be a Java class file.
		 * The file name must at least end with <code>".class"</code> and contain no spaces.
		 * (Neither class names nor package names may contain spaces.)
		 * Whether it actually is a class file will need to be determined by
		 * a class loader.
		 */
		boolean fileNameMightBeForClassFile(String name) {
			return FileTools.extension(name).toLowerCase().equals(".class") //$NON-NLS-1$
					&& (name.indexOf(' ') == -1);
		}

		/**
		 * Return the names of all the classes discovered on the classpath.
		 * Just a bit more performant than {@link #getClassNames()}.
		 */
		public Iterator<String> classNames() {
			return this.classNames(Filter.Null.<String>instance());
		}

		/**
		 * Return the names of all the classes discovered on the classpath
		 * that are accepted by the specified filter.
		 * Just a bit more performant than {@link #getClassNames(Filter)}.
		 */
		public Iterator<String> classNames(Filter<String> filter) {
			if (this.canonicalFile.exists()) {
				if (this.canonicalFile.isDirectory()) {
					return this.classNamesForDirectory(filter);
				}
				if (fileIsArchive(this.canonicalFile)) {
					return this.classNamesForArchive(filter);
				}
			}
			return EmptyIterator.instance();
		}

		/**
		 * Return the names of all the classes discovered
		 * under the entry's directory and accepted by
		 * the specified filter.
		 */
		private Iterator<String> classNamesForDirectory(Filter<String> filter) {
			return new FilteringIterator<String>(this.classNamesForDirectory(), filter);
		}

		/**
		 * Transform the class files to class names.
		 */
		private Iterator<String> classNamesForDirectory() {
			final int start = this.canonicalFile.getAbsolutePath().length() + 1;
			return new TransformationIterator<File, String>(this.classFilesForDirectory()) {
				@Override
				protected String transform(File f) {
					return convertToClassName(f.getAbsolutePath().substring(start));
				}
			};
		}

		/**
		 * Return the names of all the classes discovered
		 * in the entry's archive file and accepted by the
		 * specified filter.
		 */
		private Iterator<String> classNamesForArchive(Filter<String> filter) {
			// we can't simply wrap iterators here because we need to close the archive file...
			ZipFile zipFile = null;
			try {
				zipFile = new ZipFile(this.canonicalFile);
			} catch (IOException ex) {
				return EmptyIterator.instance();
			}
			Collection<String> classNames = new HashSet<String>(zipFile.size());
			for (Enumeration<? extends ZipEntry> stream = zipFile.entries(); stream.hasMoreElements(); ) {
				ZipEntry zipEntry = stream.nextElement();
				String zipEntryName = zipEntry.getName();
				if (this.fileNameMightBeForClassFile(zipEntryName)) {
					String className = convertToClassName(zipEntryName);
					if (filter.accept(className)) {
						classNames.add(className);
					}
				}
			}
			try {
				zipFile.close();
			} catch (IOException ex) {
				return EmptyIterator.instance();
			}
			return classNames.iterator();
		}

	}

}

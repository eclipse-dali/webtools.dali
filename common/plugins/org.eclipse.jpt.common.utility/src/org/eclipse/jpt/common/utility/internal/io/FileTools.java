/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.SystemTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.XMLStringEncoder;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link File} utility methods:<ul>
 * <li>delete entire trees of directories and files
 * <li>build iterators on entire trees of directories and files
 * <li>build a temporary directory
 * <li>"canonize" files
 * </ul>
 */
public final class FileTools {

	public static final String USER_HOME_DIRECTORY_NAME = System.getProperty("user.home"); //$NON-NLS-1$

	public static final String USER_TEMPORARY_DIRECTORY_NAME = System.getProperty("java.io.tmpdir"); //$NON-NLS-1$

	public static String DEFAULT_TEMPORARY_DIRECTORY_NAME = "tmpdir"; //$NON-NLS-1$

	public static final String CURRENT_WORKING_DIRECTORY_NAME = System.getProperty("user.dir"); //$NON-NLS-1$

    /** A list of some invalid file name characters:<ul>
     * <li><code>:</code> - Windows drive indicator; MacOS filename separator
     * <li><code>*</code> - Windows wildcard character
     * <li><code>|</code> - Windows redirection character
     * <li><code>&</code> - escape character
     * <li><code>/</code> - Windows command option tag; Unix/Linux filename separator
     * <li><code>\</code> - Windows filename separator; Unix/Linux escape character
     * <li><code>;</code> - ???
     * <li><code>?</code> - Windows wildcard character
     * <li><code>[</code> - ???
     * <li><code>]</code> - ???
     * <li><code>=</code> - ???
     * <li><code>+</code> - ???
     * <li><code>&lt;</code> - Windows redirection character
     * <li><code>></code> - Windows redirection character
     * <li><code>"</code> - Windows filename delimiter
     * <li><code>,</code> - ???
     * </ul>
     */
	public static final char[] INVALID_FILENAME_CHARACTERS = { ':', '*', '|', '&', '/', '\\', ';', '?', '[', ']', '=', '+', '<', '>', '"', ',' };

	/** This transformer will convert strings into valid file names. */
	public static final Transformer<String, String> FILE_NAME_ENCODER = new XMLStringEncoder(INVALID_FILENAME_CHARACTERS);

	/** Windows files that are redirected to devices etc. */
	@SuppressWarnings("nls")
	private static final String[] WINDOWS_RESERVED_FILE_NAMES = {
		"con",
		"aux",
		"com1", "com2", "com3", "com4", "com5", "com6", "com7", "com8", "com9",
		"lpt1", "lpt2", "lpt3", "lpt4", "lpt5", "lpt6", "lpt7", "lpt8", "lpt9",
		"prn",
		"nul"
	};

	/** The default length of a shortened file name. */
	public static final int MAXIMUM_SHORTENED_FILE_NAME_LENGTH = 60;


	// ********** deleting directories **********

	/**
	 * Delete the specified directory and all of its contents.
	 * <em>USE WITH CARE.</em>
	 */
	public static void deleteDirectory(String directoryName) {
		deleteDirectory(new File(directoryName));
	}
	
	/**
	 * Delete the specified directory and all of its contents.
	 * <em>USE WITH CARE.</em>
	 */
	public static void deleteDirectory(File directory) {
		deleteDirectoryContents(directory);
		if ( ! directory.delete()) {
			throw new RuntimeException("unable to delete directory: " + directory.getAbsolutePath()); //$NON-NLS-1$
		}
	}
	
	/**
	 * Delete the contents of the specified directory
	 * (but not the directory itself).
	 * <em>USE WITH CARE.</em>
	 */
	public static void deleteDirectoryContents(String directoryName) {
		deleteDirectoryContents(new File(directoryName));
	}
	
	/**
	 * Delete the contents of the specified directory
	 * (but not the directory itself).
	 * <em>USE WITH CARE.</em>
	 */
	public static void deleteDirectoryContents(File directory) {
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				deleteDirectory(file);	// recurse
			} else {
				if ( ! file.delete()) {
					throw new RuntimeException("unable to delete file: " + file.getAbsolutePath()); //$NON-NLS-1$
				}
			}
		}
	}
	

	// ********** copying files **********

	/**
	 * Copies the content of the source file to the destination file.
	 */
	@SuppressWarnings("resource") // false positive?
	public static void copyToFile(File sourceFile, File destinationFile)
		throws IOException
	{
		FileChannel sourceChannel = null;
		FileChannel destinationChannel = null;
		try {
			sourceChannel = new FileInputStream(sourceFile).getChannel();
			destinationChannel = new FileOutputStream(destinationFile).getChannel();
			destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		} finally {
			try {
				if (sourceChannel != null) {
					sourceChannel.close();
				}
			} finally {
				if (destinationChannel != null) {
					destinationChannel.close();
				}
			}
		}
	}
	
	/**
	 * Copies the content of the source file to a file by
	 * the same name in the destination directory.
	 */
	public static void copyToDirectory(File sourceFile, File destinationDirectory)
		throws IOException
	{
		File destinationFile = new File(destinationDirectory, sourceFile.getName());
		if ( ! destinationFile.exists() && ! destinationFile.createNewFile()) {
			throw new RuntimeException("File.createNewFile() failed: " + destinationFile); //$NON-NLS-1$
		}
		copyToFile(sourceFile, destinationFile);
	}
	

	// ********** files and directories **********

	/**
	 * Return all the normal files in the specified directory,
	 * skipping subdirectories.
	 * @see File#isFile()
	 */
	public static Iterator<File> files(String directoryName) {
		return getFiles(directoryName).iterator();
	}
	
	/**
	 * Return all the normal files in the specified directory,
	 * skipping subdirectories.
	 * @see File#isFile()
	 */
	public static Iterable<File> getFiles(String directoryName) {
		return getFiles(new File(directoryName));
	}
	
	/**
	 * Return all the normal files in the specified directory,
	 * skipping subdirectories.
	 * @see File#isFile()
	 */
	public static Iterator<File> files(File directory) {
		return getFiles(directory).iterator();
	}

	/**
	 * Return all the normal files in the specified directory,
	 * skipping subdirectories.
	 * @see File#isFile()
	 */
	public static Iterable<File> getFiles(File directory) {
		return IterableTools.filter(IterableTools.iterable(directory.listFiles()), IS_NORMAL_FILE);
	}

	/**
	 * @see File#isFile()
	 */
	public static final Predicate<File> IS_NORMAL_FILE = new IsNormalFile();
	/* CU private */ static class IsNormalFile
		extends PredicateAdapter<File>
	{
		@Override
		public boolean evaluate(File file) {
			return file.isFile();
		}
	}
	
	/**
	 * Return all the subdirectories in the specified directory.
	 * @see File#isDirectory()
	 */
	public static Iterator<File> directories(String directoryName) {
		return getDirectories(directoryName).iterator();
	}
	
	/**
	 * Return all the subdirectories in the specified directory.
	 * @see File#isDirectory()
	 */
	public static Iterable<File> getDirectories(String directoryName) {
		return getDirectories(new File(directoryName));
	}
	
	/**
	 * Return all the subdirectories in the specified directory.
	 * @see File#isDirectory()
	 */
	public static Iterator<File> directories(File directory) {
		return getDirectories(directory).iterator();
	}
	
	/**
	 * Return all the subdirectories in the specified directory.
	 * @see File#isDirectory()
	 */
	public static Iterable<File> getDirectories(File directory) {
		return IterableTools.filter(IterableTools.iterable(directory.listFiles()), IS_DIRECTORY);
	}
	
	/**
	 * @see File#isDirectory()
	 */
	public static final Predicate<File> IS_DIRECTORY = new IsDirectory();
	/* CU private */ static class IsDirectory
		extends PredicateAdapter<File>
	{
		@Override
		public boolean evaluate(File file) {
			return file.isDirectory();
		}
	}

	/**
	 * Return all the normal files in the specified directory,
	 * recursing into subdirectories but skipping the subdirectories themselves.
	 * @see File#isFile()
	 */
	public static Iterator<File> allFiles(String directoryName) {
		return getAllFiles(directoryName).iterator();
	}
	
	/**
	 * Return all the normal files in the specified directory,
	 * recursing into subdirectories but skipping the subdirectories themselves.
	 * @see File#isFile()
	 */
	public static Iterable<File> getAllFiles(String directoryName) {
		return getAllFiles(new File(directoryName));
	}
	
	/**
	 * Return all the normal files in the specified directory,
	 * recursing into subdirectories but skipping the subdirectories themselves.
	 * @see File#isFile()
	 */
	public static Iterator<File> allFiles(File directory) {
		return getAllFiles(directory).iterator();
	}

	/**
	 * Return all the normal files in the specified directory,
	 * recursing into subdirectories but skipping the subdirectories themselves.
	 * @see File#isFile()
	 */
	public static Iterable<File> getAllFiles(File directory) {
		ArrayList<File> files = new ArrayList<File>(10000);
		addAllFilesTo(directory, files);
		return files;
	}

	/**
	 * <strong>NB:</strong> Looping offers much better performance than a true
	 * recursion when dealing with large directory trees.
	 */
	private static void addAllFilesTo(File directory, ArrayList<File> files) {
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				files.add(file);
			} else if (file.isDirectory()) {
				addAllFilesTo(file, files); // recurse
			}
		}
	}

	/**
	 * Return all the directories in the specified directory,
	 * recursing into subdirectories.
	 * @see File#isDirectory()
	 */
	public static Iterator<File> allDirectories(String directoryName) {
		return getAllDirectories(directoryName).iterator();
	}
	
	/**
	 * Return all the directories in the specified directory,
	 * recursing into subdirectories.
	 * @see File#isDirectory()
	 */
	public static Iterable<File> getAllDirectories(String directoryName) {
		return getAllDirectories(new File(directoryName));
	}
	
	/**
	 * Return all the directories in the specified directory,
	 * recursing into subdirectories.
	 * @see File#isDirectory()
	 */
	public static Iterator<File> allDirectories(File directory) {
		return getAllDirectories(directory).iterator();
	}
	
	/**
	 * Return all the directories in the specified directory,
	 * recursing into subdirectories.
	 * @see File#isDirectory()
	 */
	public static Iterable<File> getAllDirectories(File directory) {
		ArrayList<File> files = new ArrayList<File>(10000);
		addAllDirectoriesTo(directory, files);
		return files;
	}
	
	/**
	 * <strong>NB:</strong> Looping offers much better performance than a true
	 * recursion when dealing with large directory trees.
	 */
	private static void addAllDirectoriesTo(File directory, ArrayList<File> directories) {
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				directories.add(file);
				addAllDirectoriesTo(file, directories); // recurse
			}
		}
	}


	// ********** short file name manipulation **********

	/**
	 * Strip the extension from the specified file name
	 * and return the result. If the file name has no
	 * extension, it is returned unchanged
	 */
	public static String stripExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		return (index == -1) ? fileName : fileName.substring(0, index);
	}
	
	/**
	 * Strip the extension from the specified file's name
	 * and return the result. If the file's name has no
	 * extension, it is returned unchanged
	 */
	public static String stripExtension(File file) {
		return stripExtension(file.getPath());
	}

	/**
	 * Return the extension, including the dot, of the specified file name.
	 * If the file name has no extension, return an empty string.
	 */
	public static String extension(String fileName) {
		int index = fileName.lastIndexOf('.');
		return (index == -1) ? StringTools.EMPTY_STRING : fileName.substring(index);
	}
	
	/**
	 * Return the extension, including the dot, of the specified file's name.
	 * If the file's name has no extension, return an empty string.
	 */
	public static String extension(File file) {
		return extension(file.getPath());
	}


	// ********** temporary directories **********

	/**
	 * Build and return an empty temporary directory with the specified
	 * name. If the directory already exists, it will be cleared out.
	 * This directory will be a subdirectory of the Java temporary directory,
	 * as indicated by the System property <code>"java.io.tmpdir"</code>.
	 * @see #DEFAULT_TEMPORARY_DIRECTORY_NAME
	 */
	public static File emptyTemporaryDirectory(String name) {
		File dir = new File(userTemporaryDirectory(), name);
		if (dir.exists()) {
			deleteDirectoryContents(dir);
		} else {
			mkdirs(dir);
		}
		return dir;
	}

	private static void mkdirs(File dir) {
		if ( ! dir.mkdirs()) {
			throw new RuntimeException("File.mkdirs() failed: " + dir); //$NON-NLS-1$
		}
	}
	
	/**
	 * Build and return an empty temporary directory with a
	 * name of <code>"tmpdir"</code>. If the directory already exists, it will be cleared out.
	 * This directory will be a subdirectory of the Java temporary directory,
	 * as indicated by the System property <code>"java.io.tmpdir"</code>.
	 * @see #DEFAULT_TEMPORARY_DIRECTORY_NAME
	 */
	public static File emptyTemporaryDirectory() {
		return emptyTemporaryDirectory(DEFAULT_TEMPORARY_DIRECTORY_NAME);
	}
	
	/**
	 * Build and return a temporary directory with the specified
	 * name. If the directory already exists, it will be left unchanged;
	 * if it does not already exist, it will be created.
	 * This directory will be a subdirectory of the Java temporary directory,
	 * as indicated by the System property <code>"java.io.tmpdir"</code>.
	 * @see #DEFAULT_TEMPORARY_DIRECTORY_NAME
	 */
	public static File temporaryDirectory(String name) {
		File dir = new File(userTemporaryDirectory(), name);
		if ( ! dir.exists()) {
			mkdirs(dir);
		}
		return dir;
	}
	
	/**
	 * Build and return a temporary directory with a name of
	 * <code>"tmpdir"</code>. If the directory already exists, it will be left unchanged;
	 * if it does not already exist, it will be created.
	 * This directory will be a subdirectory of the Java temporary directory,
	 * as indicated by the System property <code>"java.io.tmpdir"</code>.
	 * @see #DEFAULT_TEMPORARY_DIRECTORY_NAME
	 */
	public static File temporaryDirectory() {
		return temporaryDirectory(DEFAULT_TEMPORARY_DIRECTORY_NAME);
	}
	
	/**
	 * Build and return a <em>new</em> temporary directory with the specified
	 * prefix. The prefix will be appended with a number that
	 * is incremented, starting with 1, until a non-pre-existing directory
	 * is found and successfully created. This directory will be a
	 * subdirectory of the Java temporary directory, as indicated by
	 * the System property <code>"java.io.tmpdir"</code>.
	 * @see #DEFAULT_TEMPORARY_DIRECTORY_NAME
	 */
	public static File newTemporaryDirectory(String prefix) {
		if ( ! prefix.endsWith(".")) { //$NON-NLS-1$
			prefix = prefix + '.';
		}
		File dir;
		int i = 0;
		do {
			i++;
			dir = new File(userTemporaryDirectory(), prefix + i);
		} while ( ! dir.mkdirs());
		return dir;
	}
	
	/**
	 * Build and return a *new* temporary directory with a
	 * prefix of "tmpdir". This prefix will be appended with a number that
	 * is incremented, starting with 1, until a non-pre-existing directory
	 * is found and successfully created. This directory will be a
	 * subdirectory of the Java temporary directory, as indicated by
	 * the System property "java.io.tmpdir".
	 */
	public static File newTemporaryDirectory() {
		return newTemporaryDirectory(DEFAULT_TEMPORARY_DIRECTORY_NAME);
	}
	

	// ********** resource files **********

	/**
	 * Build and return a file for the specified resource.
	 * The resource name must be fully-qualified, i.e. it cannot be relative
	 * to the package name/directory.
	 * <p>
	 * <strong>NB:</strong> There is a bug in jdk1.4.x the prevents us from getting
	 * a resource that has spaces (or other special characters) in
	 * its name.... (Java bug 4466485)
	 */
	public static File resourceFile(String resourceName) throws URISyntaxException {
		if ( ! resourceName.startsWith("/")) { //$NON-NLS-1$
			throw new IllegalArgumentException(resourceName);
		}
		return resourceFile(resourceName, FileTools.class);
	}
	
	/**
	 * Build and return a file for the specified resource.
	 * <p>
	 * <strong>NB:</strong> There is a bug in jdk1.4.x the prevents us from getting
	 * a resource that has spaces (or other special characters) in
	 * its name.... (Java bug 4466485)
	 */
	public static File resourceFile(String resourceName, Class<?> javaClass) throws URISyntaxException {
		URL url = javaClass.getResource(resourceName);
		return buildFile(url);
	}
	
	/**
	 * Build and return a file for the specified URL.
	 * <p>
	 * <strong>NB:</strong> There is a bug in jdk1.4.x the prevents us from getting
	 * a resource that has spaces (or other special characters) in
	 * its name.... (Java bug 4466485)
	 */
	public static File buildFile(URL url) throws URISyntaxException {
		return buildFile(url.getFile());
	}
	
	/**
	 * Build and return a file for the specified file name.
	 * <p>
	 * <strong>NB:</strong> There is a bug in jdk1.4.x the prevents us from getting
	 * a resource that has spaces (or other special characters) in
	 * its name.... (Java bug 4466485)
	 */
	public static File buildFile(String fileName) throws URISyntaxException {
		URI uri = new URI(fileName);
		File file = new File(uri.getPath());
		return file;
	}
	

	// ********** "canonical" files **********

	/**
	 * Convert the specified file into a "canonical" file.
	 * If getting the file's "canonical" form fails, return the file's
	 * "absolute" form.
	 * @see File#getCanonicalFile()
	 */
	public static File canonicalFile(File file) {
		try {
			return file.getCanonicalFile();
		} catch (IOException ioexception) {
			// settle for the absolute file
			return file.getAbsoluteFile();
		}
	}
	
	public static Transformer<File, File> CANONICAL_FILE_TRANSFORMER = new CanonicalFileTransformer();
	/* CU private */ static class CanonicalFileTransformer
		implements Transformer<File, File>
	{
		public File transform(File file) {
			return canonicalFile(file);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	}

	/**
	 * Convert the specified file name into a "canonical" file name.
	 */
	public static String canonicalFileName(String fileName) {
		return canonicalFile(new File(fileName)).getAbsolutePath();
	}
	
	public static Transformer<String, String> CANONICAL_FILE_NAME_TRANSFORMER = new CanonicalFileNameTransformer();
	/* CU private */ static class CanonicalFileNameTransformer
		implements Transformer<String, String>
	{
		public String transform(String fileName) {
			return canonicalFileName(fileName);
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	}


	// ********** file name validation **********

	/**
	 * Return whether the specified file name is invalid.
	 * @see #INVALID_FILENAME_CHARACTERS
	 */
	public static boolean fileNameIsInvalid(String filename) {
		return ! fileNameIsValid(filename);
	}

	/**
	 * Return whether the specified file name is valid.
	 * @see #INVALID_FILENAME_CHARACTERS
	 */
	public static boolean fileNameIsValid(String filename) {
		int len = filename.length();
		for (int i = 0; i < len; i++) {
			char filenameChar = filename.charAt(i);
			if (ArrayTools.contains(INVALID_FILENAME_CHARACTERS, filenameChar)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Convert the illegal characters in the specified file name to
	 * the specified character and return the result.
	 * @see #INVALID_FILENAME_CHARACTERS
	 */
	public static String convertToValidFileName(String filename, char replacementChar) {
		int len = filename.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char filenameChar = filename.charAt(i);
			if (ArrayTools.contains(INVALID_FILENAME_CHARACTERS, filenameChar)) {
				sb.append(replacementChar);
			} else {
				sb.append(filenameChar);
			}
		}
		return sb.toString();
	}

	/**
	 * Convert the illegal characters in the specified file name to
	 * periods (<code>'.'</code>) and return the result.
	 */
	public static String convertToValidFileName(String filename) {
		return convertToValidFileName(filename, '.');
	}

	/**
	 * Return whether the specified file name is "reserved"
	 * (i.e. it cannot be used for "user" files). Windows reserves
	 * a number of file names (e.g. CON, AUX, PRN).
	 * @see #WINDOWS_RESERVED_FILE_NAMES
	 */
	public static boolean fileNameIsReserved(String fileName) {
		// Unix/Linux does not have any "reserved" file names (I think...)
		return SystemTools.osIsWindows() && ArrayTools.contains(WINDOWS_RESERVED_FILE_NAMES, fileName.toLowerCase());
	}

	/**
	 * Return whether the specified file contains any "reserved"
	 * components.
	 * Windows reserves a number of file names (e.g. CON, AUX, PRN);
	 * and these file names cannot be used for either the names of
	 * files or directories.
	 * @see #WINDOWS_RESERVED_FILE_NAMES
	 */
	public static boolean fileHasAnyReservedComponents(File file) {
		File temp = file;
		while (temp != null) {
			if (fileNameIsReserved(temp.getName())) {
				return true;
			}
			temp = temp.getParentFile();
		}
		return false;
	}


	// ********** shortened file names **********

	/**
	 * Return a shorter version of the absolute file name for the specified file.
	 * The shorter version will not be longer than the maximum length.
	 * The first directory (usually the drive letter) and the file name or the
	 * last directory will always be added to the generated string regardless of
	 * the maximum length allowed.
	 */
	public static String shortenFileName(URL url) {
		return shortenFileName(url, MAXIMUM_SHORTENED_FILE_NAME_LENGTH);
	}

	/**
	 * Return a shorter version of the absolute file name for the specified file.
	 * The shorter version will not be longer than the maximum length.
	 * The first directory (usually the drive letter) and the file name or the
	 * last directory will always be added to the generated string regardless of
	 * the maximum length allowed.
	 */
	public static String shortenFileName(URL url, int maxLength) {
		File file;
		try {
			file = buildFile(url);
		} catch (URISyntaxException e) {
			file = new File(url.getFile());
		}
		return shortenFileName(file, maxLength);
	}

	/**
	 * Return a shorter version of the absolute file name for the specified file.
	 * The shorter version will not be longer than the maximum length.
	 * The first directory (usually the drive letter) and the file name or the
	 * last directory will always be added to the generated string regardless of
	 * the maximum length allowed.
	 */
	public static String shortenFileName(File file) {
		return shortenFileName(file, MAXIMUM_SHORTENED_FILE_NAME_LENGTH);
	}

	/**
	 * Return a shorter version of the absolute file name for the specified file.
	 * The shorter version will not be longer than the maximum length.
	 * The first directory (usually the drive letter) and the file name or the
	 * last directory will always be added to the generated string regardless of
	 * the maximum length allowed.
	 */
	public static String shortenFileName(File file, int maxLength) {
		String absoluteFileName = canonicalFile(file).getAbsolutePath();
		if (absoluteFileName.length() <= maxLength) {
			// no need to shorten
			return absoluteFileName;
		}

		// break down the path into its components
		String fs = File.separator;
		String[] paths = absoluteFileName.split('\\' + fs);

		if (paths.length <= 1) {
			// e.g. "C:\"
			return paths[0];
		}

		if (paths.length == 2) {
			// e.g. "C:\MyReallyLongFileName.ext" or "C:\MyReallyLongDirectoryName"
			// return the complete file name since this is a minimum requirement,
			// regardless of the maximum length allowed
			return absoluteFileName;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(paths[0]);		// always add the first directory, which is usually the drive letter

		// Keep the index of insertion into the string buffer
		int insertIndex = sb.length();

		sb.append(fs);
		sb.append(paths[paths.length - 1]);		// append the file name or the last directory

		maxLength -= 4;                      // -4 for "/..."

		int currentLength = sb.length() - 4; // -4 for "/..."
		int leftIndex = 1;                   //  1 to skip the root directory
		int rightIndex = paths.length - 2;   // -1 for the file name or the last directory

		boolean canAddFromLeft = true;
		boolean canAddFromRight = true;

		// Add each directory, the insertion is going in both direction: left and
		// right, once a side can't be added, the other side is still continuing
		// until both can't add anymore
		while (true) {
			if (!canAddFromLeft && !canAddFromRight)
				break;

			if (canAddFromRight) {
				String rightDirectory = paths[rightIndex];
				int rightLength = rightDirectory.length();

				// Add the directory on the right side of the loop
				if (currentLength + rightLength + 1 <= maxLength) {
					sb.insert(insertIndex,     fs);
					sb.insert(insertIndex + 1, rightDirectory);

					currentLength += rightLength + 1;
					rightIndex--;

					// The right side is now overlapping the left side, that means
					// we can't add from the right side anymore
					if (leftIndex >= rightIndex) {
						canAddFromRight = false;
					}
				} else {
					canAddFromRight = false;
				}
			}

			if (canAddFromLeft) {
				String leftDirectory = paths[leftIndex];
				int leftLength = leftDirectory.length();

				// Add the directory on the left side of the loop
				if (currentLength + leftLength + 1 <= maxLength) {
					sb.insert(insertIndex,     fs);
					sb.insert(insertIndex + 1, leftDirectory);

					insertIndex += leftLength + 1;
					currentLength += leftLength + 1;
					leftIndex++;

					// The left side is now overlapping the right side, that means
					// we can't add from the left side anymore
					if (leftIndex >= rightIndex) {
						canAddFromLeft = false;
					}
				} else {
					canAddFromLeft = false;
				}
			}
		}

		if (leftIndex <= rightIndex) {
			sb.insert(insertIndex, fs);
			sb.insert(insertIndex + 1, "..."); //$NON-NLS-1$
		}

		return sb.toString();
	}


	// ********** system properties **********

	/**
	 * Return a file representing the user's home directory.
	 */
	public static File userHomeDirectory() {
		return new File(USER_HOME_DIRECTORY_NAME);
	}
	
	/**
	 * Return a file representing the user's temporary directory.
	 */
	public static File userTemporaryDirectory() {
		return new File(USER_TEMPORARY_DIRECTORY_NAME);
	}
	
	/**
	 * Return a file representing the current working directory.
	 */
	public static File currentWorkingDirectory() {
		return new File(CURRENT_WORKING_DIRECTORY_NAME);
	}
	

	// ********** miscellaneous **********

	/**
	 * Return only the files that fit the specified filter.
	 */
	public static Iterable<File> filter(Iterable<File> files, FileFilter fileFilter) {
		return IterableTools.filter(files, new FileFilterPredicateAdapter(fileFilter));
	}

	/**
	 * Return only the files that fit the specified filter.
	 */
	public static Iterator<File> filter(Iterator<File> files, FileFilter fileFilter) {
		return IteratorTools.filter(files, new FileFilterPredicateAdapter(fileFilter));
	}

	/**
	 * Adapt a {@link FileFilter} to the {@link Predicate} interface.
	 */
	public static class FileFilterPredicateAdapter
		extends CriterionPredicate<File, FileFilter>
	{
		public FileFilterPredicateAdapter(FileFilter fileFilter) {
			super(fileFilter);
		}
		public boolean evaluate(File file) {
			return this.criterion.accept(file);
		}
	}

	/**
	 * Return a file that is a re-specification of the specified
	 * file, relative to the specified directory.<ul>
	 * <li>Linux/Unix/Mac:<br>
	 *     <code>convertToRelativeFile(/foo/bar/baz.java, /foo) => bar/baz.java</code>
	 * <li>Windows:<br>
	 *     <code>convertToRelativeFile(C:\foo\bar\baz.java, C:\foo) => bar/baz.java</code>
	 * </ul>
	 * The file can be either a file or a directory; the directory
	 * <em>should</em> be a directory.
	 * If the file is already relative or it cannot be made relative
	 * to the directory, it will be returned unchanged.
	 * <p>
	 * <strong>NB:</strong> This method has been tested on Windows and Linux,
	 * but not Mac (but the Mac is Unix-based these days, so
	 * it shouldn't be a problem...).
	 */
	public static File convertToRelativeFile(final File file, final File dir) {
		// check whether the file is already relative
		if ( ! file.isAbsolute()) {
			return file;		// return unchanged
		}

		File cFile = canonicalFile(file);
		File cDir = canonicalFile(dir);

		// the two are the same directory
		if (cFile.equals(cDir)) {
			return new File("."); //$NON-NLS-1$
		}

		File[] filePathFiles = pathFiles(cFile);
		File[] dirPathFiles = pathFiles(cDir);

		// Windows only (?): the roots are different - e.g. D:\ vs. C:\
		if ( ! dirPathFiles[0].equals(filePathFiles[0])) {
			return file;		// return unchanged
		}

		// at this point we know the root is the same, now find how much is in common
		int i = 0;		// this will point at the first miscompare
		while ((i < dirPathFiles.length) && (i < filePathFiles.length)) {
			if (dirPathFiles[i].equals(filePathFiles[i])) {
				i++;
			} else {
				break;
			}
		}
		// save our current position
		int firstMismatch = i;

		// check whether the file is ABOVE the directory: ../..
		if (firstMismatch == filePathFiles.length) {
			return relativeParentFile(dirPathFiles.length - firstMismatch);
		}

		// build a new file from the path beyond the matching portions
		File diff = new File(filePathFiles[i].getName());
		while (++i < filePathFiles.length) {
			diff = new File(diff, filePathFiles[i].getName());
		}

		// check whether the file is BELOW the directory: subdir1/subdir2/file.ext
		if (firstMismatch == dirPathFiles.length) {
			return diff;
		}

		// the file must be a PEER of the directory: ../../subdir1/subdir2/file.ext
		return new File(relativeParentFile(dirPathFiles.length - firstMismatch), diff.getPath());
	}

	/**
	 * Return a file that is a re-specification of the specified
	 * file, relative to the current working directory.
	 * @see #convertToRelativeFile(File, File)
	 */
	public static File convertToRelativeFile(final File file) {
		return convertToRelativeFile(file, currentWorkingDirectory());
	}

	/**
	 * Return an array of files representing the path to the specified
	 * file. For example:<br>
	 * <code>
	 *     C:/foo/bar/baz.txt => { C:/, C:/foo, C:/foo/bar, C:/foo/bar/baz.txt }
	 * </code>
	 */
	private static File[] pathFiles(File file) {
		List<File> path = new ArrayList<File>();
		for (File f = file; f != null; f = f.getParentFile()) {
			path.add(f);
		}
		Collections.reverse(path);
		return path.toArray(new File[path.size()]);
	}

	/**
	 * Return a file with the specified (non-zero) number of relative
	 * file names, e.g. <code>relativeParentFile(3) => ../../..</code>.
	 */
	private static File relativeParentFile(int len) {
		if (len <= 0) {
			throw new IllegalArgumentException("length must be greater than zero: " + len); //$NON-NLS-1$
		}
		File result = new File(".."); //$NON-NLS-1$
		for (int i = len - 1; i-- > 0; ) {
			result = new File(result, ".."); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Return a file that is a re-specification of the specified
	 * file, absolute to the specified directory.<ul>
	 * <li>Linux/Unix/Mac:<br>
	 *     <code>convertToAbsoluteFile(bar/baz.java, /foo) => /foo/bar/baz.java</code>
	 * <li>Windows:<br>
	 *     <code>convertToAbsoluteFile(bar/baz.java, C:\foo) => C:\foo\bar\baz.java</code>
	 * </ul>
	 * The file can be either a file or a directory; the directory
	 * <em>should</em> be a directory.
	 * If the file is already absolute, it will be returned unchanged.
	 * <p>
	 * <strong>NB:</strong> This method has been tested on Windows and Linux,
	 * but not Mac (but the Mac is Unix-based these days, so
	 * it shouldn't be a problem...).
	 */
	public static File convertToAbsoluteFile(final File file, final File dir) {
		return (file.isAbsolute()) ? file : canonicalFile(new File(dir, file.getPath()));
	}

	/**
	 * Return a file that is a re-specification of the specified
	 * file, absolute to the current working directory.
	 * @see #convertToAbsoluteFile(File, File)
	 */
	public static File convertToAbsoluteFile(final File file) {
		return convertToAbsoluteFile(file, currentWorkingDirectory());
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private FileTools() {
		super();
		throw new UnsupportedOperationException();
	}
}

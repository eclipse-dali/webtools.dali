/*******************************************************************************
 * Copyright (c) 1998, 2015 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License 2.0 and Eclipse Distribution License
 * v1.0, both of which accompany this distribution.
 * The Eclipse Public License is available at
 * http://www.eclipse.org/org/documents/epl-2.0/.
 * The Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.eol;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.io.FileTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * List any files that have any EOLs that are not the standard
 * DOS/Windows CR-LF (<code>0x0D0A</code>).
 */
public class CheckWinEOL {

	private static final String DEFAULT_ROOT_DIR_NAME = "C:/dev/main"; //$NON-NLS-1$
	public static final int CR = 0x0D;
	public static final int LF = 0x0A;

	public static void main(String[] args) {
		main((args.length == 0) ? DEFAULT_ROOT_DIR_NAME : args[0]);
		System.exit(0);
	}

	@SuppressWarnings("nls")
	public static void main(String rootDirectoryName) {
		Iterable<File> invalidJavaFiles = getAllJavaFilesWithInvalidWinEOL(rootDirectoryName);
		int count = 0;
		System.out.println("Java files with bogus EOL:");
		for (String invalidFileName : CollectionTools.treeSet(IterableTools.transform(invalidJavaFiles, FILE_ABSOLUTE_PATH_TRANSFORMER))) {
			count++;
			System.out.print('\t');
			System.out.println(invalidFileName);
		}
		System.out.println("*** total = " + count + " ***");
	}

	/**
	 * Return all the <code>.java</code> files under the specified
	 * directory that have invalid EOL character combinations
	 * for DOS/Windows, recursing into subdirectories.
	 */
	public static Iterable<File> getAllJavaFilesWithInvalidWinEOL(String directoryName) {
		return getAllJavaFilesWithInvalidWinEOL(new File(directoryName));
	}

	/**
	 * @see #getAllJavaFilesWithInvalidWinEOL(String)
	 */
	public static Iterable<File> getAllJavaFilesWithInvalidWinEOL(File directory) {
		return extractFilesWithInvalidWinEOL(javaFiles(FileTools.getAllFiles(directory)));
	}

	/**
	 * Return all the <code>.java</code> files among the specified files.
	 */
	private static Iterable<File> javaFiles(Iterable<File> files) {
		return IterableTools.filter(files, FILE_IS_JAVA_FILE);
	}

	/**
	 * This file predicate returns whether a file is a Java file
	 * (i.e. it has a <code>.java</code> extension).
	 */
	private static final Predicate<File> FILE_IS_JAVA_FILE = new FileIsJavaFile();
	/* CU private */ static class FileIsJavaFile
		extends PredicateAdapter<File>
	{
		@Override
		public boolean evaluate(File file) {
			return file.getName().toLowerCase().endsWith(".java"); //$NON-NLS-1$
		}
	}

	/**
	 * Return all the files under the specified
	 * directory that have invalid EOL character combinations
	 * for DOS/Windows, recursing into subdirectories.
	 * <p>
	 * <strong>NB:</strong> Assume all the files are <em>text</em> files.
	 */
	public static Iterable<File> getAllFilesWithInvalidWinEOL(String directoryName) {
		return getAllFilesWithInvalidWinEOL(new File(directoryName));
	}

	/**
	 * @see #getAllFilesWithInvalidWinEOL(String)
	 */
	public static Iterable<File> getAllFilesWithInvalidWinEOL(File directory) {
		return extractFilesWithInvalidWinEOL(FileTools.getAllFiles(directory));
	}
	
	/**
	 * Extract from the specified files all the files
	 * that have invalid EOL character combinations.
	 * <p>
	 * <strong>NB:</strong> Assume all the files are <em>text</em> files.
	 */
	public static Iterable<File> extractFilesWithInvalidWinEOL(Iterable<File> files) {
		return IterableTools.filter(files, FILE_HAS_INVALID_WIN_EOL) ;
	}

	/**
	 * This file predicate returns whether a file contains any invalid
	 * EOL character combinations for DOS/Windows.
	 * Every EOL must be a CR-LF (<code>0x0D0A</code>). Any standalone
	 * CR or LF is invalid.
	 */
	private static final Predicate<File> FILE_HAS_INVALID_WIN_EOL = new FileHasInvalidWinEOL();
	/* CU private */ static class FileHasInvalidWinEOL
		extends PredicateAdapter<File>
	{
		@Override
		public boolean evaluate(File file) {
			try {
				return this.fileHasInvalidWinEOL(file);
			} catch (IOException ex) {
				ex.printStackTrace();
				return false;
			}
		}

		private boolean fileHasInvalidWinEOL(File file) throws FileNotFoundException, IOException {
			InputStream stream = new BufferedInputStream(new FileInputStream(file), 262144); // 256K
			try {
				return streamHasInvalidWinEOL(stream);
			} finally {
				stream.close();
			}
		}
	}

	/**
	 * <strong>NB:</strong> The caller of this method is responsible for
	 * closing the specified stream.
	 */
	public static boolean streamHasInvalidWinEOL(InputStream stream) throws IOException {
		// prime the characters
		int previous = -1;
		int current = stream.read();
		int next = stream.read();
		while (current != -1) {		// empty stream is OK
			if (charsAreInvalidWinEOL(previous, current, next)) {
				return true;
			}
			previous = current;
			current = next;
			next = stream.read();
		}
		return false;
	}

	public static boolean charsAreInvalidWinEOL(int previous, int current, int next) {
		if (current == CR) {
			return next != LF;
		}
		if (current == LF) {
			return previous != CR;
		}
		return false;
	}

	private static final Transformer<File, String> FILE_ABSOLUTE_PATH_TRANSFORMER = new FileAbsolutePathTransformer();
	/* CU private */ static class FileAbsolutePathTransformer
		extends TransformerAdapter<File, String>
	{
		@Override
		public String transform(File file) {
			return file.getAbsolutePath();
		}
	}
}

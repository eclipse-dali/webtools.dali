/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.io.NullOutputStream;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Some tools for executing, compiling, JARing, etc.
 * Possibly obviously, this stuff might not work on all platforms. It seems
 * to work OK on Windows and Linux.
 */
@SuppressWarnings("nls")
public class JDKTools {
	private static final String CR = StringTools.CR;
	private static final String FS = System.getProperty("file.separator");
	private static final String JAVA_HOME = System.getProperty("java.home");
	private static final String JAVA_CLASSPATH = System.getProperty("java.class.path");


	/**
	 * Compile the specified source file, using the current system classpath.
	 */
	public static void compile(File sourceFile) throws IOException, InterruptedException {
		compile(sourceFile, JAVA_CLASSPATH);
	}

	/**
	 * Compile the specified source file with the specified classpath.
	 * The resulting class file will be put in the same directory as the
	 * source file.
	 * @exception RuntimeException for any non-zero exit value.
	 */
	public static void compile(File sourceFile, String classpath) throws IOException, InterruptedException {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(javaCompiler());
		if ((classpath != null) && (classpath.length() != 0)) {
			cmd.add("-classpath");
			cmd.add(classpath);
		}
		cmd.add(sourceFile.getAbsolutePath());
		exec(cmd.toArray(new String[cmd.size()]));
	}

	/**
	 * Compile all the specified source files,
	 * using the current system classpath.
	 */
	public static void compile(Iterable<File> sourceFiles) throws IOException, InterruptedException {
		compile(sourceFiles, JAVA_CLASSPATH);
	}

	/**
	 * Compile all the specified source files with
	 * the specified classpath. The resulting class files will be put in
	 * the same directories as the source files.
	 * @exception RuntimeException for any non-zero exit value.
	 */
	public static void compile(Iterable<File> sourceFiles, String classpath) throws IOException, InterruptedException {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(javaCompiler());
		if ((classpath != null) && (classpath.length() != 0)) {
			cmd.add("-classpath");
			cmd.add(classpath);
		}
		CollectionTools.addAll(cmd, javaFileNames(sourceFiles));
		exec(cmd.toArray(new String[cmd.size()]));
	}

	/**
	 * Return the names of the files in the collection that end with
	 * <code>".java"</code>.
	 */
	private static Iterable<String> javaFileNames(Iterable<File> files) {
		return new TransformationIterable<File, String>(filterJavaFiles(files), FILE_NAME_TRANSFORMER);
	}

	private static final Transformer<File, String> FILE_NAME_TRANSFORMER = new FileNameTransformer();
	/* CU private */ static class FileNameTransformer
		extends TransformerAdapter<File, String>
	{
		@Override
		public String transform(File file) {
			return file.getAbsolutePath();
		}
	}

	/**
	 * Return the files in the collection whose names that end with
	 * <code>".java"</code>.
	 */
	private static Iterable<File> filterJavaFiles(Iterable<File> files) {
		return IterableTools.filter(files, JAVA_FILE_FILTER);
	}

	private static final Predicate<File> JAVA_FILE_FILTER = new JavaFileFilter();
	/* CU private */ static class JavaFileFilter
		extends PredicateAdapter<File>
	{
		@Override
		public boolean evaluate(File file) {
			return file.isFile() && file.getPath().endsWith(".java");
		}
	}

	/**
	 * Add to the specified archive all the files in and below
	 * the specified directory.
	 * Jar program options:<ul>
	 * <li>c = create
	 * <li>f = file
	 * </ul>
	 * @exception RuntimeException for any non-zero exit value.
	 */
	public static void jar(File jarFile, File directory) throws IOException, InterruptedException {
		jar("cf", jarFile, directory);
	}

	/**
	 * Add to the specified zip file all the files in and below
	 * the specified directory.
	 * Jar program options:<ul>
	 * <li>c = create
	 * <li>M = no manifest
	 * <li>f = file
	 * </ul>
	 * @exception RuntimeException for any non-zero exit value.
	 */
	public static void zip(File zipFile, File directory) throws IOException, InterruptedException {
		jar("cMf", zipFile, directory);
	}

	private static void jar(String jarOptions, File jarFile, File directory) throws IOException, InterruptedException {
		exec(
			new String[] {
				jarUtility(),
				jarOptions,
				jarFile.getAbsolutePath(),
				"-C",
				directory.getAbsolutePath(),
				"."
			}
		);
	}

	/**
	 * Execute the specified class's main method, using the current system classpath.
	 */
	public static void java(String className) throws IOException, InterruptedException {
		java(className, JAVA_CLASSPATH);
	}

	/**
	 * Execute the specified class's main method with the specified classpath.
	 * @exception RuntimeException for any non-zero exit value.
	 */
	public static void java(String className, String classpath) throws IOException, InterruptedException {
		java(className, classpath, StringTools.EMPTY_STRING_ARRAY);
	}

	/**
	 * Execute the specified class's main method with the specified classpath.
	 * @exception RuntimeException for any non-zero exit value.
	 */
	public static void java(String className, String classpath, String[] args) throws IOException, InterruptedException {
		java(className, classpath, StringTools.EMPTY_STRING_ARRAY, args);
	}

	/**
	 * Execute the specified class's main method with the specified classpath.
	 * @exception RuntimeException for any non-zero exit value.
	 */
	public static void java(String className, String classpath, String[] javaOptions, String[] args) throws IOException, InterruptedException {
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(javaVM());
		if ((classpath != null) && (classpath.length() != 0)) {
			cmd.add("-classpath");
			cmd.add(classpath);
		}
		CollectionTools.addAll(cmd, javaOptions);
		cmd.add(className);
		CollectionTools.addAll(cmd, args);
		exec(cmd.toArray(new String[cmd.size()]));
	}

	/**
	 * Execute the specified command line.
	 * @exception RuntimeException for any non-zero exit value.
	 */
	public static void exec(String[] cmd) throws IOException, InterruptedException {
		// dump(cmd);
		Process process = Runtime.getRuntime().exec(cmd);

		// fork a thread to consume stderr
		ByteArrayOutputStream stderrStream = new ByteArrayOutputStream(1000);
		Runnable stderrRunnable = new RunnableStreamReader(
				new BufferedInputStream(process.getErrorStream()),
				new BufferedOutputStream(stderrStream)
			);
		Thread stderrThread = new Thread(stderrRunnable, "stderr stream reader");
		stderrThread.start();

		// fork a thread to consume stdout
		ByteArrayOutputStream stdoutStream = new ByteArrayOutputStream(1000);
		Runnable stdoutRunnable = new RunnableStreamReader(
				new BufferedInputStream(process.getInputStream()),
				new BufferedOutputStream(stdoutStream)
			);
		Thread stdoutThread = new Thread(stdoutRunnable, "stdout stream reader");
		stdoutThread.start();

		// wait for all the threads to die
		stderrThread.join();
		stdoutThread.join();
		int exitValue = process.waitFor();

		stderrStream.close();
		stdoutStream.close();

		if (exitValue != 0) {
			StringBuffer sb = new StringBuffer(2000);
			sb.append(CR);
			sb.append("**** exit value: ");
			sb.append(exitValue);
			sb.append(CR);
			sb.append("**** stderr: ");
			sb.append(CR);
			sb.append(stderrStream.toString());
			sb.append(CR);
			sb.append("**** stdout: ");
			sb.append(CR);
			sb.append(stdoutStream.toString());
			throw new RuntimeException(sb.toString());
		}
	}

	/**
	 * Return the name of the standard Java Home directory.
	 */
	public static String javaHomeDirectoryName() {
		return JAVA_HOME;
	}

	/**
	 * Return the standard Java Home directory.
	 */
	public static File javaHomeDirectory() {
		return new File(javaHomeDirectoryName());
	}
	
	/**
	 * Return the directory that holds the various Java tools
	 * (e.g. java, javac, jar).
	 */
	public static File javaToolDirectory() {
		return new File(javaHomeDirectory().getParentFile(), "bin");
	}

	/**
	 * Return the name of the directory that holds the various Java tools
	 * (e.g. java, javac, jar).
	 */
	public static String javaToolDirectoryName() {
		return javaToolDirectory().getPath();
	}

	/**
	 * Return the fully-qualified name of the Java VM.
	 */
	public static String javaVM() {
		return javaToolDirectoryName() + FS + "java";
	}

	/**
	 * Return the fully-qualified name of the Java compiler.
	 */
	public static String javaCompiler() {
		return javaToolDirectoryName() + FS + "javac";
	}

	/**
	 * Return the fully-qualified name of the JAR utility.
	 */
	public static String jarUtility() {
		return javaToolDirectoryName() + FS + "jar";
	}

	/**
	 * Print the specified "command line" to the console.
	 */
	static void dump(String[] cmd) {
		for (int i = 0; i < cmd.length; i++) {
			System.out.print(cmd[i]);
			if (i + 1 < cmd.length) {
				System.out.print(" ");
			}
		}
		System.out.println();
	}

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private JDKTools() {
		super();
		throw new UnsupportedOperationException();
	}


	// ********** stream reader **********

	/**
	 * This class allows you to fork a thread to read an input stream
	 * asynchronously.
	 */
	/* CU private */ static class RunnableStreamReader
		implements Runnable
	{
		private InputStream inputStream;
		private OutputStream outputStream;


		/**
		 * Construct a stream reader that reads the specified stream
		 * and discards the data.
		 */
		RunnableStreamReader(InputStream inputStream) {
			this(inputStream, NullOutputStream.instance());
		}

		/**
		 * Construct a stream reader that reads the specified input stream
		 * and copies its data to the specified output stream.
		 */
		RunnableStreamReader(InputStream inputStream, OutputStream outputStream) {
			super();
			this.inputStream = inputStream;
			this.outputStream = outputStream;
		}

		public void run() {
			try {
				for (int b = -1; (b = this.inputStream.read()) != -1; ) {
					this.outputStream.write(b);
				}
			} catch (IOException ex) {
				// hmmm - not sure what to do here, but this seems good enough
				throw new RuntimeException(ex);
			}
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}

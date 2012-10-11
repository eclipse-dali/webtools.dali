/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.libval;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jpt.common.core.internal.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link org.eclipse.jpt.common.core.libval.LibraryValidator} utility methods.
 */
public class LibraryValidatorTools {

	/**
	 * Validate whether all the specified classes can be found in the specified
	 * library classpath entries.
	 */
	public static IStatus validateClasspathEntries(Iterable<IClasspathEntry> libraryClasspathEntries, Iterable<String> classNames) {
		return validatePaths(new TransformationIterable<IClasspathEntry, IPath>(libraryClasspathEntries, CLASSPATH_ENTRY_PATH_TRANSFORMER), classNames);
	}

	private static final Transformer<IClasspathEntry, IPath> CLASSPATH_ENTRY_PATH_TRANSFORMER = new ClasspathEntryPathTransformer();
	/* CU private */ static class ClasspathEntryPathTransformer
		extends TransformerAdapter<IClasspathEntry, IPath>
	{
		@Override
		public IPath transform(IClasspathEntry classpathEntry) {
			return classpathEntry.getPath();
		}
	}

	/**
	 * Validate whether all the specified classes can be found in the JARs at
	 * the specified library paths.
	 */
	public static IStatus validatePaths(Iterable<IPath> libraryPaths, Iterable<String> classNames) {
		HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
		HashMap<String, String> classFileNameToClassName = new HashMap<String, String>();
		for (String className : CollectionTools.set(classNames)) {
			String classFileName = className.replace('.', '/') + ".class"; //$NON-NLS-1$
			flags.put(classFileName, Boolean.FALSE);
			classFileNameToClassName.put(classFileName, className);
		}

		for (IPath libraryPath : libraryPaths) {
			validate(libraryPath, flags);
		}
		
		for (Map.Entry<String, Boolean> entry : flags.entrySet()) {
			if ( ! entry.getValue().booleanValue()) {
				String className = classFileNameToClassName.get(entry.getKey());
				return JptCommonCorePlugin.instance().buildErrorStatus(JptCommonCoreMessages.USER_LIBRARY_VALIDATOR__CLASS_NOT_FOUND, className);
			}
		}
		return Status.OK_STATUS;
	}

	private static void validate(IPath libraryPath, HashMap<String, Boolean> flags) {
		File file = libraryPath.toFile();
		if (file.exists()) {
			validate(file, flags);
		}
	}

	/**
	 * Pre-condition: the specified library file exists.
	 */
	private static void validate(File libraryFile, HashMap<String, Boolean> flags) {
		ZipFile zip = null;
		try {
			zip = new ZipFile(libraryFile);
			for (Enumeration<? extends ZipEntry> stream = zip.entries(); stream.hasMoreElements(); ) {
				ZipEntry zipEntry = stream.nextElement();
				String name = zipEntry.getName();
				if (flags.containsKey(name)) {
					flags.put(name, Boolean.TRUE);
				}
			}
		} catch (IOException ex) {
			// ignore
		} finally {
			if (zip != null) {
				try {
					zip.close();
				} catch (IOException ex) {
					// ignore
				}
			}
		}
	}
}

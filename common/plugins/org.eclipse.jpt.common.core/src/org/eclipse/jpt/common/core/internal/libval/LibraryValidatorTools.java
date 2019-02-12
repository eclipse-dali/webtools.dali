/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.common.core.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jst.common.project.facet.core.JavaFacet;
import org.eclipse.jst.common.project.facet.core.libprov.osgi.BundleReference;
import org.eclipse.jst.common.project.facet.core.libprov.osgi.OsgiBundlesLibraryProviderInstallOperationConfig;
import org.eclipse.jst.common.project.facet.core.libprov.user.UserLibraryProviderInstallOperationConfig;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * {@link org.eclipse.jpt.common.core.libval.LibraryValidator} utility methods.
 */
public class LibraryValidatorTools {

	// ********** libraries **********

	/**
	 * Validate whether all the specified classes can be found in the specified
	 * config's library classpath entries.
	 */
	public static IStatus validateClasses(UserLibraryProviderInstallOperationConfig config, Iterable<String> classNames) {
		return validateClassesInClasspathEntries(config.resolve(), classNames);
	}

	/**
	 * Validate whether all the specified classes can be found in the specified
	 * library classpath entries.
	 */
	private static IStatus validateClassesInClasspathEntries(Iterable<IClasspathEntry> libraryClasspathEntries, Iterable<String> classNames) {
		return validateClassesInLibraries(new TransformationIterable<IClasspathEntry, IPath>(libraryClasspathEntries, CLASSPATH_ENTRY_PATH_TRANSFORMER), classNames);
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
	private static IStatus validateClassesInLibraries(Iterable<IPath> libraryPaths, Iterable<String> classNames) {
		HashMap<String, Boolean> flags = new HashMap<String, Boolean>();
		HashMap<String, String> classFileNameToClassName = new HashMap<String, String>();
		for (String className : CollectionTools.hashSet(classNames)) {
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
				return buildErrorStatus(JptCommonCoreMessages.USER_LIBRARY_VALIDATOR__CLASS_NOT_FOUND, className);
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


	// ********** OSGi **********

	/**
	 * Validate the specified config's OSGi bundles against the specified
	 * bundle version range.
	 */
	public static IStatus validateBundleVersions(OsgiBundlesLibraryProviderInstallOperationConfig config, Map<String, VersionRange> bundleVersionRanges) {
		// gather up the config bundles, keyed by name
		HashMap<String, Bundle> configBundles = new HashMap<String, Bundle>();
		for (BundleReference bundleRef : config.getBundleReferences()) {
			Bundle bundle = bundleRef.getBundle();
			configBundles.put(bundle.getSymbolicName(), bundle);
		}

		for (Map.Entry<String, VersionRange> entry : bundleVersionRanges.entrySet()) {
			String bundleName = entry.getKey();
			Bundle configBundle = configBundles.get(bundleName);
			if (configBundle == null) {
				return buildErrorStatus(JptCommonCoreMessages.OSGI_BUNDLES_LIBRARY_VALIDATOR__BUNDLE_NOT_FOUND, bundleName);
			}

			Version configBundleVersion = configBundle.getVersion();
			VersionRange versionRange = entry.getValue();
			if ( ! versionRange.isIncluded(configBundleVersion)) {
				return buildErrorStatus(JptCommonCoreMessages.OSGI_BUNDLES_LIBRARY_VALIDATOR__IMPROPER_BUNDLE_VERSION, bundleName);
			}
		}

		return Status.OK_STATUS;
	}


	// ********** misc **********

	/**
	 * Return the specified config's Java facet version.
	 */
	public static IProjectFacetVersion getJavaFacetVersion(JptLibraryProviderInstallOperationConfig config) {
		return config.getFacetedProject().getProjectFacetVersion(JavaFacet.FACET);
	}

	private static IStatus buildErrorStatus(String message, Object... args) {
		return JptCommonCorePlugin.instance().buildErrorStatus(message, args);
	}
}

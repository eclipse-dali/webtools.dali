/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.eclipselink.core.internal.libval;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.util.IClassFileReader;
import org.eclipse.jdt.core.util.IFieldInfo;
import org.eclipse.jpt.common.eclipselink.core.JptCommonEclipseLinkCoreMessages;
import org.eclipse.jpt.common.eclipselink.core.internal.plugin.JptCommonEclipseLinkCorePlugin;
import org.eclipse.jst.common.project.facet.core.libprov.user.UserLibraryProviderInstallOperationConfig;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Version;

/**
 * EclipseLink
 * {@link org.eclipse.jpt.common.core.libval.LibraryValidator} utility methods.
 */
public class EclipseLinkLibraryValidatorTools {

	/**
	 * Validate that there is a one and only one occurrence of EclipseLink
	 * on the specified config's library classpath and that its version
	 * is within the specified version range.
	 */
	public static IStatus validateEclipseLinkVersion(UserLibraryProviderInstallOperationConfig config, VersionRange versionRange) {
		return validateEclipseLinkVersion(config.resolve(), versionRange);
	}

	private static IStatus validateEclipseLinkVersion(Iterable<IClasspathEntry> libraryEntries, VersionRange versionRange) {
		Version version = null;
		for (IClasspathEntry libraryEntry : libraryEntries) {
			if (libraryEntry.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
				String versionString = readEclipseLinkVersion(libraryEntry);
				if (versionString != null) {
					if (version != null) {
						return buildErrorStatus(JptCommonEclipseLinkCoreMessages.ECLIPSELINK_LIBRARY_VALIDATOR__MULTIPLE_ECLIPSELINK_VERSIONS);
					}
					// Parse only major.minor.micro, so qualifiers like "5.0.0-B01" no longer throw
					version = parseVersion(versionString);
					if (version == null) {
						return buildErrorStatus(JptCommonEclipseLinkCoreMessages.ECLIPSELINK_LIBRARY_VALIDATOR__IMPROPER_ECLIPSELINK_VERSION);
					}
				}
			}
		}

		if (version == null) {
			return buildErrorStatus(JptCommonEclipseLinkCoreMessages.ECLIPSELINK_LIBRARY_VALIDATOR__NO_ECLIPSELINK_VERSION);
		}

		if ( ! versionRange.isIncluded(version)) {
			return buildErrorStatus(JptCommonEclipseLinkCoreMessages.ECLIPSELINK_LIBRARY_VALIDATOR__IMPROPER_ECLIPSELINK_VERSION);
		}

		return Status.OK_STATUS;
	}

	/**
	 * Return the EclipseLink version of the specified library, or
	 * <code>null</code> if it does not contain
	 * <code>org.eclipse.persistence.Version</code>.
	 * <p>
	 * Up to 2.7, the version is a compile-time constant in
	 * <code>Version.class</code>. From 3.0 on, <code>Version</code> reads
	 * it at run time from <code>org/eclipse/persistence/version.properties</code>
	 * in the same jar, so the constant is gone and the properties file is
	 * read instead.
	 */
	private static String readEclipseLinkVersion(IClasspathEntry libraryEntry) {
		IClassFileReader classReader = buildClassFileReader(libraryEntry);
		if (classReader == null) {
			return null;  // not the EclipseLink core library
		}
		// EclipseLink 2.x stores the version as a constant; 3.0+ moved it to version.properties in the same jar
		String version = readVersionConstant(classReader);
		return (version != null) ? version : readVersionProperties(libraryEntry.getPath().toFile());
	}

	private static String readVersionConstant(IClassFileReader classReader) {
		for (IFieldInfo field : classReader.getFieldInfos()) {
			if (Arrays.equals(field.getName(), VERSION_FIELD_NAME_CHAR_ARRAY)) {
				try {
					return field.getConstantValueAttribute().getConstantValue().getStringValue();
				} catch (RuntimeException ex) {
					return null;  // the field is not a constant (EclipseLink 3.0 and later)
				}
			}
		}
		return null;
	}

	private static String readVersionProperties(File library) {
		try {
			if (library.isFile()) {
				try (ZipFile zipFile = new ZipFile(library)) {
					ZipEntry zipEntry = zipFile.getEntry(VERSION_PROPERTIES_PATH);
					if (zipEntry != null) {
						try (InputStream stream = zipFile.getInputStream(zipEntry)) {
							return loadVersion(stream);
						}
					}
				}
			} else if (library.isDirectory()) {
				File propertiesFile = new File(library, VERSION_PROPERTIES_PATH);
				if (propertiesFile.isFile()) {
					try (InputStream stream = new FileInputStream(propertiesFile)) {
						return loadVersion(stream);
					}
				}
			}
		} catch (IOException ex) {
			// unreadable library - treat it as having no version
		}
		return null;
	}

	private static String loadVersion(InputStream stream) throws IOException {
		Properties properties = new Properties();
		properties.load(stream);
		String version = properties.getProperty(VERSION_PROPERTY);
		return ((version == null) || version.trim().isEmpty()) ? null : version.trim();
	}

	/**
	 * Return the release part (<code>major.minor.micro</code>) of the specified
	 * EclipseLink version string, ignoring any qualifier, such as the
	 * <code>-B01</code> of a pre-release build, that is not a valid OSGi
	 * qualifier. Return <code>null</code> if the string does not start with a
	 * version number.
	 */
	private static Version parseVersion(String versionString) {
		Matcher matcher = RELEASE_PATTERN.matcher(versionString);
		return matcher.lookingAt() ? new Version(matcher.group()) : null;
	}

	private final static Pattern RELEASE_PATTERN = Pattern.compile("\\d+(\\.\\d+){0,2}"); //$NON-NLS-1$

	private final static String VERSION_FIELD_NAME = "version"; //$NON-NLS-1$
	private final static char[] VERSION_FIELD_NAME_CHAR_ARRAY = VERSION_FIELD_NAME.toCharArray();

	private static IClassFileReader buildClassFileReader(IClasspathEntry entry) {
		return ToolFactory.createDefaultClassFileReader(entry.getPath().toFile().getAbsolutePath(), VERSION_CLASS_PATH, IClassFileReader.FIELD_INFOS);
	}

	private final static String VERSION_CLASS_PATH = "org/eclipse/persistence/Version.class"; //$NON-NLS-1$
	private final static String VERSION_PROPERTIES_PATH = "org/eclipse/persistence/version.properties"; //$NON-NLS-1$
	private final static String VERSION_PROPERTY = "version"; //$NON-NLS-1$

	private static IStatus buildErrorStatus(String message) {
		return JptCommonEclipseLinkCorePlugin.instance().buildErrorStatus(message);
	}
}

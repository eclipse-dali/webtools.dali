/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.eclipselink.core.internal.libval;

import java.util.Arrays;
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
				String versionString = null;
				IClassFileReader classReader = buildClassFileReader(libraryEntry);
				if (classReader != null) {
					for (IFieldInfo field : classReader.getFieldInfos()) {
						if (Arrays.equals(field.getName(), VERSION_FIELD_NAME_CHAR_ARRAY)) {
							try {
								versionString = field.getConstantValueAttribute().getConstantValue().getStringValue();
							} catch (RuntimeException ex) {
								// assume the field has no value
							}
							break;
						}
					}
				}
				if (versionString != null) {
					if (version != null) {
						return buildErrorStatus(JptCommonEclipseLinkCoreMessages.ECLIPSELINK_LIBRARY_VALIDATOR__MULTIPLE_ECLIPSELINK_VERSIONS);
					}
					version = new Version(versionString);
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

	private final static String VERSION_FIELD_NAME = "version"; //$NON-NLS-1$
	private final static char[] VERSION_FIELD_NAME_CHAR_ARRAY = VERSION_FIELD_NAME.toCharArray();

	private static IClassFileReader buildClassFileReader(IClasspathEntry entry) {
		return ToolFactory.createDefaultClassFileReader(entry.getPath().toFile().getAbsolutePath(), VERSION_CLASS_PATH, IClassFileReader.FIELD_INFOS);
	}

	private final static String VERSION_CLASS_PATH = "org/eclipse/persistence/Version.class"; //$NON-NLS-1$

	private static IStatus buildErrorStatus(String message) {
		return JptCommonEclipseLinkCorePlugin.instance().buildErrorStatus(message);
	}
}

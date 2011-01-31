/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.libval;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.util.IClassFileReader;
import org.eclipse.jdt.core.util.IFieldInfo;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.core.internal.libprov.JpaUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCoreMessages;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Version;

/**
 * Library validator for EclipseLink user libraries.
 * 
 * In order to validate that the correct eclipselink.jar is present in the user library, the version
 * class which appears in standard EclipseLink libraries will be examined and compared against the
 * union of calculated version ranges, depending on the platform specified in the config.
 */
public class EclipseLinkUserLibraryValidator
	implements LibraryValidator {
	
	private final static String VERSION_CLASS_PATH = "org/eclipse/persistence/Version.class"; //$NON-NLS-1$
	
	private final static String VERSION_FIELD_NAME = "version"; //$NON-NLS-1$
	
	
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		JpaUserLibraryProviderInstallOperationConfig jpaConfig 
				= (JpaUserLibraryProviderInstallOperationConfig) config;
		JpaPlatformDescription platform = jpaConfig.getJpaPlatform();
		Set<VersionRange> versionRanges = new HashSet<VersionRange>();
		if (EclipseLinkPlatform.VERSION_1_0.equals(platform)) {
			versionRanges.add(new VersionRange("[1.0, 3.0)")); //$NON-NLS-1$
		}
		else if (EclipseLinkPlatform.VERSION_1_1.equals(platform)) {
			versionRanges.add(new VersionRange("[1.1, 3.0)")); //$NON-NLS-1$
		}
		else if (EclipseLinkPlatform.VERSION_1_2.equals(platform)) {
			versionRanges.add(new VersionRange("[1.2, 3.0)")); //$NON-NLS-1$
		}
		else if (EclipseLinkPlatform.VERSION_2_0.equals(platform)) {
			versionRanges.add(new VersionRange("[2.0, 3.0)")); //$NON-NLS-1$
		}
		else if (EclipseLinkPlatform.VERSION_2_1.equals(platform)) {
			versionRanges.add(new VersionRange("[2.1, 3.0)")); //$NON-NLS-1$
		}
		return validate(jpaConfig, versionRanges);
	}
	
	protected IStatus validate(
			JpaUserLibraryProviderInstallOperationConfig config, Set<VersionRange> versionRanges) {
		
		Version version = null;
		for (IClasspathEntry cpe : config.resolve()) {
			String versionString = null;
			if (cpe.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
				IClassFileReader classReader = 
						ToolFactory.createDefaultClassFileReader(
							cpe.getPath().toFile().getAbsolutePath(), VERSION_CLASS_PATH, IClassFileReader.FIELD_INFOS);
				if (classReader != null) {
					for (IFieldInfo field : classReader.getFieldInfos()) {
						if (StringTools.stringsAreEqual(field.getName(), VERSION_FIELD_NAME.toCharArray())) {
							try {
								versionString = field.getConstantValueAttribute().getConstantValue().getStringValue();
							}
							catch (Exception e) {
								// potentially a bit could go wrong with that last line, but if any
								// assumptions aren't met, there's no value
							}
							break;
						}
					}
				}
				if (versionString != null) {
					if (version != null) {
						return new Status(
								IStatus.ERROR, JptEclipseLinkCorePlugin.PLUGIN_ID,
								JptEclipseLinkCoreMessages.EclipseLinkLibraryValidator_multipleEclipseLinkVersions);
					}
					else {
						version = new Version(versionString);
					}
				}
			}
		}
		
		if (version == null) {
			return new Status(
					IStatus.ERROR, JptEclipseLinkCorePlugin.PLUGIN_ID, 
					JptEclipseLinkCoreMessages.EclipseLinkLibraryValidator_noEclipseLinkVersion);
		}
		
		for (VersionRange versionRange : versionRanges) {
			if (! versionRange.isIncluded(version)) {
				return new Status(
						IStatus.ERROR, JptEclipseLinkCorePlugin.PLUGIN_ID, 
						JptEclipseLinkCoreMessages.EclipseLinkLibraryValidator_improperEclipseLinkVersion);
			}
		}
		
		return Status.OK_STATUS;
	}
}

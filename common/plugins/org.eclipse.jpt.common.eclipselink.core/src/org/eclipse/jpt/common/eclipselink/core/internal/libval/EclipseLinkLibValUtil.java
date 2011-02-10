/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.eclipselink.core.internal.libval;

import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.util.IClassFileReader;
import org.eclipse.jdt.core.util.IFieldInfo;
import org.eclipse.jpt.common.eclipselink.core.JptCommonEclipseLinkCorePlugin;
import org.eclipse.jpt.common.eclipselink.core.internal.JptCommonEclipseLinkCoreMessages;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Version;


public class EclipseLinkLibValUtil {
	
	private final static String VERSION_CLASS_PATH = "org/eclipse/persistence/Version.class"; //$NON-NLS-1$
	
	private final static String VERSION_FIELD_NAME = "version"; //$NON-NLS-1$
	
	
	public static IStatus validate(Iterable<IClasspathEntry> libraryEntries, Set<VersionRange> versionRanges) {
		Version version = null;
		for (IClasspathEntry entry : libraryEntries) {
			String versionString = null;
			if (entry.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
				IClassFileReader classReader = 
						ToolFactory.createDefaultClassFileReader(
							entry.getPath().toFile().getAbsolutePath(), VERSION_CLASS_PATH, IClassFileReader.FIELD_INFOS);
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
								IStatus.ERROR, JptCommonEclipseLinkCorePlugin.PLUGIN_ID,
								JptCommonEclipseLinkCoreMessages.EclipseLinkLibraryValidator_multipleEclipseLinkVersions);
					}
					else {
						version = new Version(versionString);
					}
				}
			}
		}
		
		if (version == null) {
			return new Status(
					IStatus.ERROR, JptCommonEclipseLinkCorePlugin.PLUGIN_ID, 
					JptCommonEclipseLinkCoreMessages.EclipseLinkLibraryValidator_noEclipseLinkVersion);
		}
		
		for (VersionRange versionRange : versionRanges) {
			if (! versionRange.isIncluded(version)) {
				return new Status(
						IStatus.ERROR, JptCommonEclipseLinkCorePlugin.PLUGIN_ID, 
						JptCommonEclipseLinkCoreMessages.EclipseLinkLibraryValidator_improperEclipseLinkVersion);
			}
		}
		
		return Status.OK_STATUS;
	}
}

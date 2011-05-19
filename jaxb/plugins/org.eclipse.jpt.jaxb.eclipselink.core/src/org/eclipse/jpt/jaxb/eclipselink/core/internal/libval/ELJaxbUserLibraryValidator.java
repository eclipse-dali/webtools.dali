/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.libval;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jpt.common.core.internal.libval.LibValUtil;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.eclipselink.core.internal.JptCommonEclipseLinkCoreMessages;
import org.eclipse.jpt.common.eclipselink.core.internal.libval.EclipseLinkLibValUtil;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.internal.libprov.JaxbUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbPlatform;
import org.eclipse.osgi.service.resolver.VersionRange;


/**
 * Library validator for EclipseLink JAXB user libraries.
 * 
 * In order to validate that the correct eclipselink.jar is present in the user library, the version
 * class which appears in standard EclipseLink libraries will be examined and compared against the
 * union of calculated version ranges, depending on the platform specified in the config.
 */
public class ELJaxbUserLibraryValidator
		implements LibraryValidator {
	
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		JaxbUserLibraryProviderInstallOperationConfig jaxbConfig 
				= (JaxbUserLibraryProviderInstallOperationConfig) config;
		JaxbPlatformDescription platform = jaxbConfig.getJaxbPlatform();
		Set<VersionRange> versionRanges = new HashSet<VersionRange>();
		
		if (ELJaxbPlatform.VERSION_2_1.equals(platform)) {
			versionRanges.add(new VersionRange("[2.1, 3.0)")); //$NON-NLS-1$
		}
		else if (ELJaxbPlatform.VERSION_2_2.equals(platform)) {
			versionRanges.add(new VersionRange("[2.2, 3.0)")); //$NON-NLS-1$
		}
		else if (ELJaxbPlatform.VERSION_2_3.equals(platform)) {
			versionRanges.add(new VersionRange("[2.3, 3.0)")); //$NON-NLS-1$
		}
		
		IStatus status = EclipseLinkLibValUtil.validate(jaxbConfig.resolve(), versionRanges);
		
		if (! status.isOK()) {
			return status;
		}
		
		// finally look for xjc classes
		
		Set<String> classNames = new HashSet<String>();
		
		classNames.add("com.sun.tools.xjc.XJCFacade"); //$NON-NLS-1$
		classNames.add("com.sun.xml.bind.Util"); //$NON-NLS-1$
		
		Iterable<IPath> libraryPaths = 
				new TransformationIterable<IClasspathEntry, IPath>(jaxbConfig.resolve()) {
					@Override
					protected IPath transform(IClasspathEntry o) {
						return o.getPath();
					}
				};
		
		status = LibValUtil.validate(libraryPaths, classNames);
		
		if (! status.isOK()) {
			return new Status(IStatus.WARNING, JptJaxbCorePlugin.PLUGIN_ID, JptCommonEclipseLinkCoreMessages.ELJaxbUserLibraryValidator_noXjcClasses);
		}
		
		return Status.OK_STATUS;
	}
}

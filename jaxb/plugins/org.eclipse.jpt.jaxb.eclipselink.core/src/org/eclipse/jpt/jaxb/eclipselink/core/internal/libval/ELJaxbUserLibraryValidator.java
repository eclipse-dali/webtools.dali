/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.libval;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.internal.libval.LibraryValidatorTools;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.eclipselink.core.internal.JptCommonEclipseLinkCoreMessages;
import org.eclipse.jpt.common.eclipselink.core.internal.libval.EclipseLinkLibValUtil;
import org.eclipse.jpt.jaxb.core.internal.libprov.JaxbUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbPlatform;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.plugin.JptJaxbEclipseLinkCorePlugin;
import org.eclipse.osgi.service.resolver.VersionRange;


/**
 * Library validator for Oxm JAXB user libraries.
 * 
 * In order to validate that the correct eclipselink.jar is present in the user library, the version
 * class which appears in standard Oxm libraries will be examined and compared against the
 * union of calculated version ranges, depending on the platform specified in the config.
 */
public class ELJaxbUserLibraryValidator
		implements LibraryValidator {
	
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		JaxbUserLibraryProviderInstallOperationConfig jaxbConfig 
				= (JaxbUserLibraryProviderInstallOperationConfig) config;
		JaxbPlatformConfig platformConfig = jaxbConfig.getJaxbPlatformConfig();
		Set<VersionRange> versionRanges = new HashSet<VersionRange>();
		
		if (ELJaxbPlatform.VERSION_2_1.equals(platformConfig)) {
			versionRanges.add(new VersionRange("[2.1, 3.0)")); //$NON-NLS-1$
		}
		else if (ELJaxbPlatform.VERSION_2_2.equals(platformConfig)) {
			versionRanges.add(new VersionRange("[2.2, 3.0)")); //$NON-NLS-1$
		}
		else if (ELJaxbPlatform.VERSION_2_3.equals(platformConfig)) {
			versionRanges.add(new VersionRange("[2.3, 3.0)")); //$NON-NLS-1$
		}
		else if (ELJaxbPlatform.VERSION_2_4.equals(platformConfig)) {
			versionRanges.add(new VersionRange("[2.4, 3.0)")); //$NON-NLS-1$
		}
		
		IStatus status = EclipseLinkLibValUtil.validate(jaxbConfig.resolve(), versionRanges);
		
		if (! status.isOK()) {
			return status;
		}
		
		// finally look for xjc classes
		
		ArrayList<String> classNames = new ArrayList<String>(2);
		
		classNames.add("com.sun.tools.xjc.XJCFacade"); //$NON-NLS-1$
		classNames.add("com.sun.xml.bind.Util"); //$NON-NLS-1$
		
		status = LibraryValidatorTools.validateClasspathEntries(jaxbConfig.resolve(), classNames);
		
		return status.isOK() ?
				Status.OK_STATUS :
				JptJaxbEclipseLinkCorePlugin.instance().buildStatus(IStatus.WARNING, JptCommonEclipseLinkCoreMessages.ELJaxbUserLibraryValidator_noXjcClasses);
	}
}

/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.libval;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.eclipselink.core.internal.libval.EclipseLinkLibValUtil;
import org.eclipse.jpt.jpa.core.internal.libprov.JpaUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.osgi.service.resolver.VersionRange;

/**
 * Library validator for EclipseLink user libraries.
 * 
 * In order to validate that the correct eclipselink.jar is present in the user library, the version
 * class which appears in standard EclipseLink libraries will be examined and compared against the
 * union of calculated version ranges, depending on the platform specified in the config.
 */
public class EclipseLinkUserLibraryValidator
	implements LibraryValidator {
	
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
		
		return EclipseLinkLibValUtil.validate(jpaConfig.resolve(), versionRanges);
	}
}

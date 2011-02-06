/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.libval;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.internal.libprov.JpaOsgiBundlesLibraryProviderInstallOperationConfig;
import org.eclipse.osgi.service.resolver.VersionRange;

public class GenericEclipseLinkBundlesLibraryValidator 
		extends AbstractOsgiBundlesLibraryValidator {
	
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		JpaOsgiBundlesLibraryProviderInstallOperationConfig jpaConfig 
				= (JpaOsgiBundlesLibraryProviderInstallOperationConfig) config;
		Map<String, VersionRange[]> bundleVersionRanges = new HashMap<String, VersionRange[]>();
		String bundleName = "javax.persistence"; //$NON-NLS-1$
		VersionRange[] versionRanges = new VersionRange[0];
		if (config.getProjectFacetVersion().equals(JpaFacet.VERSION_1_0)) {
			versionRanges = new VersionRange[] {new VersionRange("[1.0, 3.0)")}; //$NON-NLS-1$
		}
		else if (config.getProjectFacetVersion().equals(JpaFacet.VERSION_2_0)) {
			versionRanges = new VersionRange[] {new VersionRange("[2.0, 3.0)")}; //$NON-NLS-1$
		}
		bundleVersionRanges.put(bundleName, versionRanges);
		return validate(jpaConfig, bundleVersionRanges);
	}
}

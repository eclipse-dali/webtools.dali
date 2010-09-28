/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.libval;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.core.internal.libprov.JpaOsgiBundlesLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.core.internal.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.core.internal.libval.AbstractOsgiBundlesLibraryValidator;
import org.eclipse.jpt.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.osgi.service.resolver.VersionRange;

public class EclipseLinkEclipseLinkBundlesLibraryValidator
	extends AbstractOsgiBundlesLibraryValidator {
	
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		JpaOsgiBundlesLibraryProviderInstallOperationConfig jpaConfig 
				= (JpaOsgiBundlesLibraryProviderInstallOperationConfig) config;
		Map<String, VersionRange[]> bundleVersionRanges = new HashMap<String, VersionRange[]>();
		JpaPlatformDescription platform = jpaConfig.getJpaPlatform();
		if (EclipseLinkPlatform.VERSION_1_0.equals(platform)) {
			bundleVersionRanges.put(
					"org.eclipse.persistence.core",
					new VersionRange[] {new VersionRange("[1.0, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.jpa",
					new VersionRange[] {new VersionRange("[1.0, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.asm",
					new VersionRange[] {new VersionRange("[1.0, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.antlr",
					new VersionRange[] {new VersionRange("[1.0, 3.0)")});
		}
		else if (EclipseLinkPlatform.VERSION_1_1.equals(platform)) {
			bundleVersionRanges.put(
					"org.eclipse.persistence.core",
					new VersionRange[] {new VersionRange("[1.1, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.jpa",
					new VersionRange[] {new VersionRange("[1.1, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.asm",
					new VersionRange[] {new VersionRange("[1.1, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.antlr",
					new VersionRange[] {new VersionRange("[1.1, 3.0)")});
		}
		else if (EclipseLinkPlatform.VERSION_1_2.equals(platform)) {
			bundleVersionRanges.put(
					"org.eclipse.persistence.core",
					new VersionRange[] {new VersionRange("[1.2, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.jpa",
					new VersionRange[] {new VersionRange("[1.2, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.asm",
					new VersionRange[] {new VersionRange("[1.2, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.antlr",
					new VersionRange[] {new VersionRange("[1.2, 3.0)")});
		}
		else if (EclipseLinkPlatform.VERSION_2_0.equals(platform)) {
			bundleVersionRanges.put(
					"org.eclipse.persistence.core",
					new VersionRange[] {new VersionRange("[2.0, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.jpa",
					new VersionRange[] {new VersionRange("[2.0, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.asm",
					new VersionRange[] {new VersionRange("[2.0, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.antlr",
					new VersionRange[] {new VersionRange("[2.0, 3.0)")});
		}
		else if (EclipseLinkPlatform.VERSION_2_1.equals(platform)) {
			bundleVersionRanges.put(
					"org.eclipse.persistence.core",
					new VersionRange[] {new VersionRange("[2.1, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.jpa",
					new VersionRange[] {new VersionRange("[2.1, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.asm",
					new VersionRange[] {new VersionRange("[2.1, 3.0)")});
			bundleVersionRanges.put(
					"org.eclipse.persistence.antlr",
					new VersionRange[] {new VersionRange("[2.1, 3.0)")});
		}
		return validate(jpaConfig, bundleVersionRanges);
	}
}

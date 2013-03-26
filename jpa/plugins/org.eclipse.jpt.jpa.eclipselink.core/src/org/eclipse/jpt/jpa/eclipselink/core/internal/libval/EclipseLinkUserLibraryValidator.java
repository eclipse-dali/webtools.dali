/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.libval;

import java.util.HashMap;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.eclipselink.core.internal.libval.EclipseLinkLibraryValidatorTools;
import org.eclipse.jpt.jpa.core.internal.libprov.JpaUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink1_1JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink1_2JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink2_0JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink2_1JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink2_2JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink2_3JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_4;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_5;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory;
import org.eclipse.osgi.service.resolver.VersionRange;

/**
 * Library validator for EclipseLink user libraries.
 * <p>
 * In order to validate that the correct eclipselink.jar is present in the
 * user library, the version class which appears in standard EclipseLink
 * libraries will be examined and compared against the union of calculated
 * version ranges, depending on the platform specified in the config.
 */
@SuppressWarnings("nls")
public class EclipseLinkUserLibraryValidator
	implements LibraryValidator
{
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		return validate((JpaUserLibraryProviderInstallOperationConfig) config);
	}

	private IStatus validate(JpaUserLibraryProviderInstallOperationConfig config) {
		String platformID = config.getJpaPlatformConfig().getId();
		VersionRange versionRange = PLATFORM_VERSION_RANGES.get(platformID);
		return EclipseLinkLibraryValidatorTools.validateEclipseLinkVersion(config, versionRange);
	}

	/**
	 * Map(platform ID => version range)
	 */
	private static final HashMap<String, VersionRange> PLATFORM_VERSION_RANGES = buildPlatformVersionRanges();

	private static HashMap<String, VersionRange> buildPlatformVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put(EclipseLinkJpaPlatformFactory.ID, new VersionRange("[1.0, 3.0)"));
		versionRanges.put(EclipseLink1_1JpaPlatformFactory.ID, new VersionRange("[1.1, 3.0)"));
		versionRanges.put(EclipseLink1_2JpaPlatformFactory.ID, new VersionRange("[1.2, 3.0)"));
		versionRanges.put(EclipseLink2_0JpaPlatformFactory.ID, new VersionRange("[2.0, 3.0)"));
		versionRanges.put(EclipseLink2_1JpaPlatformFactory.ID, new VersionRange("[2.1, 3.0)"));
		versionRanges.put(EclipseLink2_2JpaPlatformFactory.ID, new VersionRange("[2.2, 3.0)"));
		versionRanges.put(EclipseLink2_3JpaPlatformFactory.ID, new VersionRange("[2.3, 3.0)"));
		versionRanges.put(EclipseLinkJpaPlatformFactory2_4.ID, new VersionRange("[2.4, 3.0)"));
		versionRanges.put(EclipseLinkJpaPlatformFactory2_5.ID, new VersionRange("[2.5, 3.0)"));
		return versionRanges;
	}
}

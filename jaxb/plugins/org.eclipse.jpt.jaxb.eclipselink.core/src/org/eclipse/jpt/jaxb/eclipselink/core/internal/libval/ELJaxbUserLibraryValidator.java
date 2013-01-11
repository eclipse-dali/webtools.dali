/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.libval;

import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.internal.libval.LibraryValidatorTools;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.eclipselink.core.internal.JptCommonEclipseLinkCoreMessages;
import org.eclipse.jpt.common.eclipselink.core.internal.libval.EclipseLinkLibraryValidatorTools;
import org.eclipse.jpt.jaxb.core.internal.libprov.JaxbUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.plugin.JptJaxbEclipseLinkCorePlugin;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_1.ELJaxb_2_1_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.ELJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_3.ELJaxb_2_3_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_4.ELJaxb_2_4_PlatformDefinition;
import org.eclipse.osgi.service.resolver.VersionRange;


/**
 * Library validator for Oxm JAXB user libraries.
 * <p>
 * In order to validate that the correct eclipselink.jar is present in the
 * user library, the version class which appears in standard EclipseLink
 * libraries will be examined and compared against the union of calculated
 * version ranges, depending on the platform specified in the config.
 */
@SuppressWarnings("nls")
public class ELJaxbUserLibraryValidator
	implements LibraryValidator
{
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		return validate((JaxbUserLibraryProviderInstallOperationConfig) config);
	}

	private IStatus validate(JaxbUserLibraryProviderInstallOperationConfig config) {
		String platformID = config.getJaxbPlatformConfig().getId();
		VersionRange versionRange = PLATFORM_VERSION_RANGES.get(platformID);
		IStatus status = EclipseLinkLibraryValidatorTools.validateEclipseLinkVersion(config, versionRange);
		if (! status.isOK()) {
			return status;
		}
		
		// look for xjc classes
		status = LibraryValidatorTools.validateClasses(config, XJC_CLASS_NAMES);
		return status.isOK() ?
				status :
				JptJaxbEclipseLinkCorePlugin.instance().buildWarningStatus(JptCommonEclipseLinkCoreMessages.ELJaxbUserLibraryValidator_noXjcClasses);
	}

	/**
	 * Map(platform ID => version range)
	 */
	private static final HashMap<String, VersionRange> PLATFORM_VERSION_RANGES = buildPlatformVersionRanges();

	private static HashMap<String, VersionRange> buildPlatformVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put(ELJaxb_2_1_PlatformDefinition.ID, new VersionRange("[2.1, 3.0)"));
		versionRanges.put(ELJaxb_2_2_PlatformDefinition.ID, new VersionRange("[2.2, 3.0)"));
		versionRanges.put(ELJaxb_2_3_PlatformDefinition.ID, new VersionRange("[2.3, 3.0)"));
		versionRanges.put(ELJaxb_2_4_PlatformDefinition.ID, new VersionRange("[2.4, 3.0)"));
		return versionRanges;
	}

	private static final ArrayList<String> XJC_CLASS_NAMES = buildXjcClassNames();

	private static ArrayList<String> buildXjcClassNames() {
		ArrayList<String> xjcClassNames = new ArrayList<String>();
		xjcClassNames.add("com.sun.tools.xjc.XJCFacade");
		xjcClassNames.add("com.sun.xml.bind.Util");
		return xjcClassNames;
	}
}

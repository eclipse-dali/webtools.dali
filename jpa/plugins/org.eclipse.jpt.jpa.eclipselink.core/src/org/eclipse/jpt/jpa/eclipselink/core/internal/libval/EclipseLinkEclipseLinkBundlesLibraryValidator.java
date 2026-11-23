/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.libval;

import java.util.HashMap;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.internal.libval.LibraryValidatorTools;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.jpa.core.internal.libprov.JpaOsgiBundlesLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory1_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory1_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_4;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_5;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_6;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_7;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory3_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory4_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory5_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory;
import org.eclipse.osgi.service.resolver.VersionRange;

/**
 * <strong>NB:</strong> The class name is not a mistake - there is also a
 * {@link org.eclipse.jpt.jpa.core.internal.libval.GenericEclipseLinkBundlesLibraryValidator}.
 */
@SuppressWarnings("nls")
public class EclipseLinkEclipseLinkBundlesLibraryValidator
	implements LibraryValidator
{
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		return validate((JpaOsgiBundlesLibraryProviderInstallOperationConfig) config);
	}

	private IStatus validate(JpaOsgiBundlesLibraryProviderInstallOperationConfig config) {
		String jpaPlatformID = config.getJpaPlatformConfig().getId();
		HashMap<String, VersionRange> bundleVersionRanges = PLATFORM_BUNDLE_VERSION_RANGES.get(jpaPlatformID);
		return LibraryValidatorTools.validateBundleVersions(config, bundleVersionRanges);
	}

	/**
	 * Map(platform ID => Map(bundle name => version range))
	 */
	private static final HashMap<String, HashMap<String, VersionRange>> PLATFORM_BUNDLE_VERSION_RANGES = buildPlatformBundleVersionRanges();

	private static HashMap<String, HashMap<String, VersionRange>> buildPlatformBundleVersionRanges() {
		HashMap<String, HashMap<String, VersionRange>> versionRanges = new HashMap<String, HashMap<String, VersionRange>>();
		versionRanges.put(EclipseLinkJpaPlatformFactory.ID,    buildEclipseLink1_0BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory1_1.ID, buildEclipseLink1_1BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory1_2.ID, buildEclipseLink1_2BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory2_0.ID, buildEclipseLink2_0BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory2_1.ID, buildEclipseLink2_1BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory2_2.ID, buildEclipseLink2_2BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory2_3.ID, buildEclipseLink2_3BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory2_4.ID, buildEclipseLink2_4BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory2_5.ID, buildEclipseLink2_5BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory2_6.ID, buildEclipseLink2_6BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory2_7.ID, buildEclipseLink2_7BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory3_0.ID, buildEclipseLink3_0BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory4_0.ID, buildEclipseLink4_0BundleVersionRanges());
		versionRanges.put(EclipseLinkJpaPlatformFactory5_0.ID, buildEclipseLink5_0BundleVersionRanges());
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink1_0BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core",  new VersionRange("[1.0, 3.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",   new VersionRange("[1.0, 3.0)"));
		versionRanges.put("org.eclipse.persistence.asm",   new VersionRange("[1.0, 3.0)"));
		versionRanges.put("org.eclipse.persistence.antlr", new VersionRange("[1.0, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink1_1BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core",  new VersionRange("[1.1, 3.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",   new VersionRange("[1.1, 3.0)"));
		versionRanges.put("org.eclipse.persistence.asm",   new VersionRange("[1.1, 3.0)"));
		versionRanges.put("org.eclipse.persistence.antlr", new VersionRange("[1.1, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink1_2BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core",  new VersionRange("[1.2, 3.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",   new VersionRange("[1.2, 3.0)"));
		versionRanges.put("org.eclipse.persistence.asm",   new VersionRange("[1.2, 3.0)"));
		versionRanges.put("org.eclipse.persistence.antlr", new VersionRange("[1.2, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink2_0BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core",  new VersionRange("[2.0, 3.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",   new VersionRange("[2.0, 3.0)"));
		versionRanges.put("org.eclipse.persistence.asm",   new VersionRange("[2.0, 3.0)"));
		versionRanges.put("org.eclipse.persistence.antlr", new VersionRange("[2.0, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink2_1BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core",  new VersionRange("[2.1, 3.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",   new VersionRange("[2.1, 3.0)"));
		versionRanges.put("org.eclipse.persistence.asm",   new VersionRange("[2.1, 3.0)"));
		versionRanges.put("org.eclipse.persistence.antlr", new VersionRange("[2.1, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink2_2BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core",  new VersionRange("[2.2, 3.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",   new VersionRange("[2.2, 3.0)"));
		versionRanges.put("org.eclipse.persistence.asm",   new VersionRange("[2.2, 3.0)"));
		versionRanges.put("org.eclipse.persistence.antlr", new VersionRange("[2.2, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink2_3BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core",  new VersionRange("[2.3, 3.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",   new VersionRange("[2.3, 3.0)"));
		versionRanges.put("org.eclipse.persistence.asm",   new VersionRange("[2.3, 3.0)"));
		versionRanges.put("org.eclipse.persistence.antlr", new VersionRange("[2.3, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink2_4BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core",  new VersionRange("[2.4, 3.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",   new VersionRange("[2.4, 3.0)"));
		versionRanges.put("org.eclipse.persistence.asm",   new VersionRange("[2.4, 3.0)"));
		versionRanges.put("org.eclipse.persistence.antlr", new VersionRange("[2.4, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink2_5BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core",  new VersionRange("[2.5, 3.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",   new VersionRange("[2.5, 3.0)"));
		versionRanges.put("org.eclipse.persistence.asm",   new VersionRange("[2.5, 3.0)"));
		versionRanges.put("org.eclipse.persistence.antlr", new VersionRange("[2.5, 3.0)"));
		return versionRanges;
	}

	/*
	 * From 2.6 onwards the repackaged ASM and ANTLR bundles carry the upstream
	 * library version, not the EclipseLink release version, e.g.
	 *     2.6.0  - org.eclipse.persistence.asm 5.0.1,  org.eclipse.persistence.antlr 3.2.0
	 *     2.6.9  - org.eclipse.persistence.asm 6.2.0,  org.eclipse.persistence.antlr 3.2.0
	 *     2.7.0  - org.eclipse.persistence.asm 6.0.0,  org.eclipse.persistence.antlr 3.5.2
	 *     2.7.16 - org.eclipse.persistence.asm 9.x,    org.eclipse.persistence.antlr 3.5.3
	 * (3.0+ ships ASM 9.x and no ANTLR bundle at all). Constraining them to the
	 * EclipseLink release range would reject every real distribution, so only
	 * the core and JPA bundles are constrained for 2.6 and later.
	 *
	 * As with the 2.x platforms, which accept any later 2.x runtime, the Jakarta
	 * platforms accept any later Jakarta-based runtime up to the next untested
	 * major release.
	 */

	private static HashMap<String, VersionRange> buildEclipseLink2_6BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core", new VersionRange("[2.6, 3.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",  new VersionRange("[2.6, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink2_7BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core", new VersionRange("[2.7, 3.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",  new VersionRange("[2.7, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink3_0BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core", new VersionRange("[3.0, 6.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",  new VersionRange("[3.0, 6.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink4_0BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core", new VersionRange("[4.0, 6.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",  new VersionRange("[4.0, 6.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildEclipseLink5_0BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("org.eclipse.persistence.core", new VersionRange("[5.0, 6.0)"));
		versionRanges.put("org.eclipse.persistence.jpa",  new VersionRange("[5.0, 6.0)"));
		return versionRanges;
	}
}

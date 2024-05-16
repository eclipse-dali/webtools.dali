/*******************************************************************************
 * Copyright (c) 2010, 2024 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.libval;

import java.util.HashMap;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.internal.libval.LibraryValidatorTools;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaProject2_1;
import org.eclipse.jpt.jpa.core.jpa2_2.JpaProject2_2;
import org.eclipse.jpt.jpa.core.jpa3_0.JpaProject3_0;
import org.eclipse.jpt.jpa.core.jpa3_1.JpaProject3_1;
import org.eclipse.jst.common.project.facet.core.libprov.osgi.OsgiBundlesLibraryProviderInstallOperationConfig;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

@SuppressWarnings("nls")
public class GenericEclipseLinkBundlesLibraryValidator 
	implements LibraryValidator
{
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		return validate((OsgiBundlesLibraryProviderInstallOperationConfig) config);
	}

	private IStatus validate(OsgiBundlesLibraryProviderInstallOperationConfig config) {
		IProjectFacetVersion facetVersion = config.getProjectFacetVersion();
		HashMap<String, VersionRange> bundleVersionRanges = JPA_FACET_BUNDLE_VERSION_RANGES.get(facetVersion);
		return LibraryValidatorTools.validateBundleVersions(config, bundleVersionRanges);
	}

	/**
	 * Map(facet version => Map(bundle name => version range))
	 */
	private static final HashMap<IProjectFacetVersion, HashMap<String, VersionRange>> JPA_FACET_BUNDLE_VERSION_RANGES = buildJpaFacetBundleVersionRanges();

	private static HashMap<IProjectFacetVersion, HashMap<String, VersionRange>> buildJpaFacetBundleVersionRanges() {
		HashMap<IProjectFacetVersion, HashMap<String, VersionRange>> versionRanges = new HashMap<IProjectFacetVersion, HashMap<String, VersionRange>>();
		versionRanges.put(JpaProject.FACET_VERSION,    buildJpa1_0BundleVersionRanges());
		versionRanges.put(JpaProject2_0.FACET_VERSION, buildJpa2_0BundleVersionRanges());
		versionRanges.put(JpaProject2_1.FACET_VERSION, buildJpa2_1BundleVersionRanges());
		versionRanges.put(JpaProject2_2.FACET_VERSION, buildJpa2_2BundleVersionRanges());
		versionRanges.put(JpaProject3_0.FACET_VERSION, buildJpa3_0BundleVersionRanges());
		versionRanges.put(JpaProject3_1.FACET_VERSION, buildJpa3_1BundleVersionRanges());
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildJpa1_0BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("javax.persistence", new VersionRange("[1.0, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildJpa2_0BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("javax.persistence", new VersionRange("[2.0, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildJpa2_1BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("javax.persistence", new VersionRange("[2.5, 3.0)"));
		return versionRanges;
	}
	
	private static HashMap<String, VersionRange> buildJpa2_2BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("javax.persistence", new VersionRange("[2.5, 3.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildJpa3_0BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("jakarta.persistence", new VersionRange("[3.0, 4.0)"));
		return versionRanges;
	}

	private static HashMap<String, VersionRange> buildJpa3_1BundleVersionRanges() {
		HashMap<String, VersionRange> versionRanges = new HashMap<String, VersionRange>();
		versionRanges.put("jakarta.persistence", new VersionRange("[4.0, 5.0)"));
		return versionRanges;
	}
}

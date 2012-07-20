/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.libval;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.jpa.core.internal.JptCoreMessages;
import org.eclipse.jpt.jpa.core.internal.libprov.JpaOsgiBundlesLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jst.common.project.facet.core.libprov.osgi.BundleReference;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Bundle;

public abstract class AbstractOsgiBundlesLibraryValidator
		implements LibraryValidator {
	
	protected IStatus validate(
			JpaOsgiBundlesLibraryProviderInstallOperationConfig config, 
			Map<String, VersionRange[]> bundleVersionRanges) {
		
		Map<String, Bundle> bundles = new HashMap<String, Bundle>();
		
		for (BundleReference bundleRef : config.getBundleReferences()) {
			for (String bundleName : bundleVersionRanges.keySet()) {
				// if we've gotten here, the bundle references are resolvable
				if (bundleRef.getBundle().getSymbolicName().equals(bundleName)) {
					bundles.put(bundleName, bundleRef.getBundle());
				}
			}
		}
		
		for (String bundleName : bundleVersionRanges.keySet()) {
			if (bundles.get(bundleName) == null) {
				return JptJpaCorePlugin.instance().buildErrorStatus(JptCoreMessages.OSGI_BUNDLES_LIBRARY_VALIDATOR__BUNDLE_NOT_FOUND, bundleName);
			}
		}
		
		for (String bundleName : bundleVersionRanges.keySet()) {
			Bundle bundle = bundles.get(bundleName);
			for (VersionRange versionRange : bundleVersionRanges.get(bundleName)) {
				if (! versionRange.isIncluded(bundle.getVersion())) {
					return JptJpaCorePlugin.instance().buildErrorStatus(JptCoreMessages.OSGI_BUNDLES_LIBRARY_VALIDATOR__IMPROPER_BUNDLE_VERSION, bundleName);
				}
			}
		}
		
		return Status.OK_STATUS;
	}
}

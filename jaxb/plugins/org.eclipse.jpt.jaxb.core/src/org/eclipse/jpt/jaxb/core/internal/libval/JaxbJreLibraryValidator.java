/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libval;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.internal.libval.LibraryValidatorTools;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.jaxb.core.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.GenericJaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;


public class JaxbJreLibraryValidator
		implements LibraryValidator {
	
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		
		JaxbLibraryProviderInstallOperationConfig jaxbConfig 
				= (JaxbLibraryProviderInstallOperationConfig) config;
		
		if (! jaxbConfig.getJaxbPlatformConfig().getGroupConfig().getId().equals(GenericJaxbPlatformDefinition.GROUP_ID)) {
			return JptJaxbCorePlugin.instance().buildErrorStatus(JptJaxbCoreMessages.JRE_LIBRARY_VALIDATOR_INVALID_PLATFORM);
		}
		
		IProjectFacetVersion jaxbVersion = config.getProjectFacetVersion();
		IProjectFacetVersion javaVersion = LibraryValidatorTools.getJavaFacetVersion(jaxbConfig);
		IProjectFacetVersion javaJaxbVersion = JaxbLibraryValidatorTools.findJavaJaxbVersion(jaxbConfig);
		
		// dev-time portion of validation - error if actual java library does not support jaxb facet
		
		IProjectFacetVersion jreJaxbVersion = JaxbLibraryValidatorTools.findJreJaxbVersion(jaxbConfig);
		
		// null here implies something prior to jaxb 2.1
		if (jreJaxbVersion == null || jreJaxbVersion.compareTo(jaxbVersion) < 0) {
			return JptJaxbCorePlugin.instance().buildErrorStatus(JptJaxbCoreMessages.JRE_LIBRARY_VALIDATOR_INVALID_JAVA_LIBRARY, jaxbVersion.getVersionString());
		}
		
		// runtime portion of validation - warn if java facet is insufficient to provide jaxb api.
		// user may override implementation manually, however
		// assume 1.6 runtime environs are latest (with jaxb 2.1, not 2.0)
		
		// null here implies something prior to jaxb 2.1
		if (javaJaxbVersion == null || javaJaxbVersion.compareTo(jaxbVersion) < 0) {
			return JptJaxbCorePlugin.instance().buildStatus(IStatus.WARNING, JptJaxbCoreMessages.JRE_LIBRARY_VALIDATOR_INVALID_JAVA_FACET, javaVersion.getVersionString(), jaxbVersion.getVersionString());
		}
		
		// TODO - check for xjc classes when we support jdk version of xjc
		
		return Status.OK_STATUS;
	}
}

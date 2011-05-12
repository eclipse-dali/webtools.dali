/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libval;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.jaxb.core.GenericJaxbPlatform;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;


public class JaxbJreLibraryValidator
		implements LibraryValidator {
	
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		
		JaxbLibraryProviderInstallOperationConfig jaxbConfig 
				= (JaxbLibraryProviderInstallOperationConfig) config;
		
		if (! jaxbConfig.getJaxbPlatform().getGroup().equals(GenericJaxbPlatform.GROUP)) {
			return new Status(IStatus.ERROR, JptJaxbCorePlugin.PLUGIN_ID, JptJaxbCoreMessages.JreLibraryValidator_invalidPlatform);
		}
		
		IProjectFacetVersion jaxbVersion = config.getProjectFacetVersion();
		IProjectFacetVersion javaVersion = JaxbLibValUtil.getJavaVersion(jaxbConfig);
		IProjectFacetVersion javaJaxbVersion = JaxbLibValUtil.findJavaJaxbVersion(jaxbConfig);
		
		// dev-time portion of validation - error if actual java library does not support jaxb facet
		
		IProjectFacetVersion jreJaxbVersion = JaxbLibValUtil.findJreJaxbVersion(jaxbConfig);
		
		// null here implies something prior to jaxb 2.1
		if (jreJaxbVersion == null || jreJaxbVersion.compareTo(jaxbVersion) < 0) {
			String message = NLS.bind(
					JptJaxbCoreMessages.JreLibraryValidator_invalidJavaLibrary,
					new String[] {jaxbVersion.getVersionString()});
			return new Status(IStatus.ERROR, JptJaxbCorePlugin.PLUGIN_ID, message);
		}
		
		// runtime portion of validation - warn if java facet is insufficient to provide jaxb api.
		// user may override implementation manually, however
		// assume 1.6 runtime environs are latest (with jaxb 2.1, not 2.0)
		
		// null here implies something prior to jaxb 2.1
		if (javaJaxbVersion == null || javaJaxbVersion.compareTo(jaxbVersion) < 0) {
			String message = NLS.bind(
					JptJaxbCoreMessages.JreLibraryValidator_invalidJavaFacet,
					new String[] {javaVersion.getVersionString(), jaxbVersion.getVersionString()});
			return new Status(IStatus.WARNING, JptJaxbCorePlugin.PLUGIN_ID, message);
		}
		
		// TODO - check for xjc classes when we support jdk version of xjc
		
		return Status.OK_STATUS;
	}
}

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
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;


public class JaxbJreLibraryValidator
		implements LibraryValidator {
	
	public IStatus validate(JptLibraryProviderInstallOperationConfig config) {
		
		JaxbLibraryProviderInstallOperationConfig jaxbConfig 
				= (JaxbLibraryProviderInstallOperationConfig) config;
		
		if (! jaxbConfig.getJaxbPlatform().getGroup().equals(GenericJaxbPlatform.GROUP)) {
			return new Status(IStatus.ERROR, JptJaxbCorePlugin.PLUGIN_ID, JptJaxbCoreMessages.JreLibraryValidator_invalidPlatform);
		}
		
		IProjectFacetVersion jreJaxbVersion = JaxbLibValUtil.findJreJaxbVersion(jaxbConfig);
		IProjectFacetVersion jaxbVersion = config.getProjectFacetVersion();
			
		if (jreJaxbVersion == null || jreJaxbVersion.compareTo(jaxbVersion) < 0) {
			return new Status(IStatus.ERROR, JptJaxbCorePlugin.PLUGIN_ID, JptJaxbCoreMessages.JreLibraryValidator_invalidJreJaxbVersion);
		}
		
		return Status.OK_STATUS;
	}
}

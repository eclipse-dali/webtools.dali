/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libprov;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryProviderInstallOperationConfig;


public class JaxbJreLibraryProviderInstallOperationConfig
		extends LibraryProviderInstallOperationConfig
		implements JaxbLibraryProviderInstallOperationConfig {
	
	private JaxbPlatformDescription jaxbPlatform;
	
	
	public JaxbJreLibraryProviderInstallOperationConfig() {
		super();
	}
	
	
	public JaxbPlatformDescription getJaxbPlatform() {
		return this.jaxbPlatform;
	}
	
	public void setJaxbPlatform(JaxbPlatformDescription jaxbPlatform) {
		JaxbPlatformDescription old = this.jaxbPlatform;
		this.jaxbPlatform = jaxbPlatform;
		if (old != jaxbPlatform) {
			notifyListeners(PROP_JAXB_PLATFORM, old, jaxbPlatform);
		}
	}
	
	@Override
	public synchronized IStatus validate() {
		IStatus status = super.validate();
		if (! status.isOK()) {
			return status;
		}
		
		for (LibraryValidator libraryValidator : JptCommonCorePlugin.getLibraryValidators(this)) {
			status = libraryValidator.validate(this);
			if (! status.isOK()) {
				return status;
			}
		}
		
		return Status.OK_STATUS;
	}
}

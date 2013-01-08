/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libprov;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.JptWorkspace;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.core.libval.LibraryValidatorManager;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryProviderInstallOperationConfig;


public class JaxbJreLibraryProviderInstallOperationConfig
		extends LibraryProviderInstallOperationConfig
		implements JaxbLibraryProviderInstallOperationConfig {
	
	private JaxbPlatformConfig jaxbPlatformConfig;
	
	
	public JaxbJreLibraryProviderInstallOperationConfig() {
		super();
	}
	
	
	public JaxbPlatformConfig getJaxbPlatformConfig() {
		return this.jaxbPlatformConfig;
	}
	
	public void setJaxbPlatformConfig(JaxbPlatformConfig jaxbPlatformConfig) {
		JaxbPlatformConfig old = this.jaxbPlatformConfig;
		this.jaxbPlatformConfig = jaxbPlatformConfig;
		if (old != jaxbPlatformConfig) {
			notifyListeners(PROP_JAXB_PLATFORM, old, jaxbPlatformConfig);
		}
	}
	
	@Override
	public synchronized IStatus validate() {
		IStatus status = super.validate();
		if ( ! status.isOK()) {
			return status;
		}
		LibraryValidatorManager lvManager = this.getLibraryValidatorManager();
		if (lvManager == null) {
			return Status.OK_STATUS;
		}
		for (LibraryValidator libraryValidator : lvManager.getLibraryValidators(this)) {
			status = libraryValidator.validate(this);
			if ( ! status.isOK()) {
				return status;
			}
		}
		
		return Status.OK_STATUS;
	}

	private LibraryValidatorManager getLibraryValidatorManager() {
		JptWorkspace jptWorkspace = this.getJptWorkspace();
		return (jptWorkspace == null) ? null : jptWorkspace.getLibraryValidatorManager();
	}

	private JptWorkspace getJptWorkspace() {
		return (JptWorkspace) this.getWorkspace().getAdapter(JptWorkspace.class);
	}

	private IWorkspace getWorkspace() {
		return this.getProject().getWorkspace();
	}

	private IProject getProject() {
		return this.getFacetedProject().getProject();
	}
}

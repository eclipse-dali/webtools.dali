/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.libprov;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.JptWorkspace;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.core.libval.LibraryValidatorManager;
import org.eclipse.jst.j2ee.internal.common.classpath.WtpOsgiBundlesLibraryProviderInstallOperationConfig;


public abstract class JptOsgiBundlesLibraryProviderInstallOperationConfig
		extends WtpOsgiBundlesLibraryProviderInstallOperationConfig
		implements JptLibraryProviderInstallOperationConfig {
	
	protected JptOsgiBundlesLibraryProviderInstallOperationConfig() {
		super();
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
		return ResourcesPlugin.getWorkspace();
	}
}

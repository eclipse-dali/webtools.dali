/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.libprov;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.internal.libval.LibraryValidatorManager;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
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
		if (! status.isOK()) {
			return status;
		}
		
		for (LibraryValidator libraryValidator : LibraryValidatorManager.instance().getLibraryValidators(this)) {
			status = libraryValidator.validate(this);
			if (! status.isOK()) {
				return status;
			}
		}
		
		return Status.OK_STATUS;
	}
}

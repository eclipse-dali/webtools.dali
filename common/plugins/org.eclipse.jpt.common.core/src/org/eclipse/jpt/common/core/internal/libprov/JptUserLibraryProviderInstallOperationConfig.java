/*******************************************************************************
 *  Copyright (c) 2010, 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.libprov;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jst.j2ee.internal.common.classpath.WtpUserLibraryProviderInstallOperationConfig;


public abstract class JptUserLibraryProviderInstallOperationConfig
		extends WtpUserLibraryProviderInstallOperationConfig
		implements JptLibraryProviderInstallOperationConfig {
	
	protected JptUserLibraryProviderInstallOperationConfig() {
		super();
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

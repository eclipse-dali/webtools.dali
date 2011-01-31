/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.libprov;

import org.eclipse.jpt.common.core.internal.libprov.JptUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.core.libprov.JpaLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.core.platform.JpaPlatformDescription;

public class JpaUserLibraryProviderInstallOperationConfig
		extends JptUserLibraryProviderInstallOperationConfig
		implements JpaLibraryProviderInstallOperationConfig {
	
	private JpaPlatformDescription jpaPlatform;
	
	
	public JpaUserLibraryProviderInstallOperationConfig() {
		super();
	}
	
	
	public JpaPlatformDescription getJpaPlatform() {
		return this.jpaPlatform;
	}
	
	public void setJpaPlatform(JpaPlatformDescription jpaPlatform) {
		JpaPlatformDescription old = this.jpaPlatform;
		this.jpaPlatform = jpaPlatform;
		notifyListeners(PROP_JPA_PLATFORM, old, jpaPlatform);
	}
}

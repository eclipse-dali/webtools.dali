/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.libprov;

import org.eclipse.jpt.common.core.internal.libprov.JptUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.libprov.JpaLibraryProviderInstallOperationConfig;

public class JpaUserLibraryProviderInstallOperationConfig
		extends JptUserLibraryProviderInstallOperationConfig
		implements JpaLibraryProviderInstallOperationConfig {
	
	private JpaPlatform.Config jpaPlatformConfig;
	
	
	public JpaUserLibraryProviderInstallOperationConfig() {
		super();
	}
	
	
	public JpaPlatform.Config getJpaPlatformConfig() {
		return this.jpaPlatformConfig;
	}
	
	public void setJpaPlatformConfig(JpaPlatform.Config jpaPlatformConfig) {
		JpaPlatform.Config old = this.jpaPlatformConfig;
		this.jpaPlatformConfig = jpaPlatformConfig;
		notifyListeners(PROP_JPA_PLATFORM, old, jpaPlatformConfig);
	}
}

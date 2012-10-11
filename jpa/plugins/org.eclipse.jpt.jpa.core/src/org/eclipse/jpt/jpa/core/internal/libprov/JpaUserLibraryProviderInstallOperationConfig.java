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
import org.eclipse.jpt.jpa.core.libprov.JpaLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformConfig;

public class JpaUserLibraryProviderInstallOperationConfig
		extends JptUserLibraryProviderInstallOperationConfig
		implements JpaLibraryProviderInstallOperationConfig {
	
	private JpaPlatformConfig jpaPlatformConfig;
	
	
	public JpaUserLibraryProviderInstallOperationConfig() {
		super();
	}
	
	
	public JpaPlatformConfig getJpaPlatformConfig() {
		return this.jpaPlatformConfig;
	}
	
	public void setJpaPlatformConfig(JpaPlatformConfig jpaPlatformConfig) {
		JpaPlatformConfig old = this.jpaPlatformConfig;
		this.jpaPlatformConfig = jpaPlatformConfig;
		notifyListeners(PROP_JPA_PLATFORM, old, jpaPlatformConfig);
	}
}

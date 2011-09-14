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

import org.eclipse.jpt.common.core.internal.libprov.JptOsgiBundlesLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.libprov.JpaLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;

public class JpaOsgiBundlesLibraryProviderInstallOperationConfig
		extends JptOsgiBundlesLibraryProviderInstallOperationConfig
		implements JpaLibraryProviderInstallOperationConfig {
	
	private JpaPlatformDescription jpaPlatform;
	
	
	public JpaOsgiBundlesLibraryProviderInstallOperationConfig() {
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
/*******************************************************************************
 *  Copyright (c) 2010, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
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

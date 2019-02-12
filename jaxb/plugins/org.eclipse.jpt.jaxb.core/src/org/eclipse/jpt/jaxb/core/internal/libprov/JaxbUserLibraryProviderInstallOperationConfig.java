/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libprov;

import org.eclipse.jpt.common.core.internal.libprov.JptUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;

public class JaxbUserLibraryProviderInstallOperationConfig
		extends JptUserLibraryProviderInstallOperationConfig
		implements JaxbLibraryProviderInstallOperationConfig {
	
	private JaxbPlatformConfig jaxbPlatformConfig;
	
	
	public JaxbUserLibraryProviderInstallOperationConfig() {
		super();
	}
	
	
	public JaxbPlatformConfig getJaxbPlatformConfig() {
		return this.jaxbPlatformConfig;
	}
	
	public void setJaxbPlatformConfig(JaxbPlatformConfig jaxbPlatformConfig) {
		JaxbPlatformConfig old = this.jaxbPlatformConfig;
		this.jaxbPlatformConfig = jaxbPlatformConfig;
		notifyListeners(PROP_JAXB_PLATFORM, old, jaxbPlatformConfig);
	}
}

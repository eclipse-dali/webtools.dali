/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libprov;

import org.eclipse.jpt.common.core.internal.libprov.JptOsgiBundlesLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;


public class JaxbOsgiBundlesLibraryProviderInstallOperationConfig
		extends JptOsgiBundlesLibraryProviderInstallOperationConfig
		implements JaxbLibraryProviderInstallOperationConfig {
	
	private JaxbPlatformConfig jaxbPlatformConfig;
	
	
	public JaxbOsgiBundlesLibraryProviderInstallOperationConfig() {
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

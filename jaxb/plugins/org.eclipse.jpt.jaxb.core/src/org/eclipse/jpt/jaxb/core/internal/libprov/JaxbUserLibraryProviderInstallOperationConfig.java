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

import org.eclipse.jpt.core.internal.libprov.JptUserLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;

public class JaxbUserLibraryProviderInstallOperationConfig
		extends JptUserLibraryProviderInstallOperationConfig
		implements JaxbLibraryProviderInstallOperationConfig {
	
	private JaxbPlatformDescription jaxbPlatform;
	
	
	public JaxbUserLibraryProviderInstallOperationConfig() {
		super();
	}
	
	
	public JaxbPlatformDescription getJaxbPlatform() {
		return this.jaxbPlatform;
	}
	
	public void setJaxbPlatform(JaxbPlatformDescription jaxbPlatform) {
		JaxbPlatformDescription old = this.jaxbPlatform;
		this.jaxbPlatform = jaxbPlatform;
		notifyListeners(PROP_JAXB_PLATFORM, old, jaxbPlatform);
	}
}

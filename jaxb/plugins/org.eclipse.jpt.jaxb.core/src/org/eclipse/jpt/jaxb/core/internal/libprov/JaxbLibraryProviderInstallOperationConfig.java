/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.libprov;

import org.eclipse.jpt.core.libval.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;


public interface JaxbLibraryProviderInstallOperationConfig 
		extends JptLibraryProviderInstallOperationConfig {
	
	public static final String PROP_JAXB_PLATFORM = "JAXB_PLATFORM"; //$NON-NLS-1$
	
	JaxbPlatformDescription getJaxbPlatform();
	
	void setJaxbPlatform(JaxbPlatformDescription jaxbPlatform);
}

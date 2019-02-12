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
package org.eclipse.jpt.jaxb.core.libprov;

import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;


public interface JaxbLibraryProviderInstallOperationConfig 
		extends JptLibraryProviderInstallOperationConfig {
	
	public static final String PROP_JAXB_PLATFORM = "JAXB_PLATFORM"; //$NON-NLS-1$
	
	JaxbPlatformConfig getJaxbPlatformConfig();
	
	void setJaxbPlatformConfig(JaxbPlatformConfig jaxbPlatformConfig);
}

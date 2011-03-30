/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.libprov;

import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;


public interface JpaLibraryProviderInstallOperationConfig 
		extends JptLibraryProviderInstallOperationConfig {
	
	/** enablement expression for jpa platform id (String) */
	public static final String JPA_PLATFORM_ENABLEMENT_EXP = "jpaPlatform"; //$NON-NLS-1$
	
	/** enablement expression for jpa platform description (JpaPlatformDescription) */
	public static final String JPA_PLATFORM_DESCRIPTION_ENABLEMENT_EXP = "jpaPlatformDescription";  //$NON-NLS-1$ 
	
	/** property of all jpa library provider install operation configs */
	public static final String PROP_JPA_PLATFORM = "JPA_PLATFORM"; //$NON-NLS-1$
	
	JpaPlatformDescription getJpaPlatform();
	
	void setJpaPlatform(JpaPlatformDescription jpaPlatform);
}

/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.libprov;

import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.JpaPlatform;


public interface JpaLibraryProviderInstallOperationConfig 
	extends JptLibraryProviderInstallOperationConfig
{
	/** enablement expression for jpa platform id (String) */
	public static final String JPA_PLATFORM_ENABLEMENT_EXP = "jpaPlatform"; //$NON-NLS-1$
	
	/** enablement expression for jpa platform description (JpaPlatformDescription) */
	public static final String JPA_PLATFORM_DESCRIPTION_ENABLEMENT_EXP = "jpaPlatformDescription";  //$NON-NLS-1$ 
	
	/** property of all jpa library provider install operation configs */
	public static final String PROP_JPA_PLATFORM = "JPA_PLATFORM"; //$NON-NLS-1$
	
	JpaPlatform.Config getJpaPlatformConfig();
	
	void setJpaPlatformConfig(JpaPlatform.Config jpaPlatformConfig);
}

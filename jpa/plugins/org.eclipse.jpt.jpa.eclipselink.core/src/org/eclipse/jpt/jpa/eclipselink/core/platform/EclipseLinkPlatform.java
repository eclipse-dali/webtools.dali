/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.platform;

import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformGroupDescription;

/**
 * Constants pertaining to the EclipseLink JPA platforms and their group
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.0
 */
public class EclipseLinkPlatform {
	
	public static final JpaPlatformGroupDescription GROUP
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatformGroup("eclipselink"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_1_0
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform("org.eclipse.eclipselink.platform"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_1_1
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform("eclipselink1_1"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_1_2
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform("eclipselink1_2"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_2_0
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform("eclipselink2_0"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_2_1
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform("eclipselink2_1"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_2_2
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform("eclipselink2_2"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_2_3
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform("eclipselink2_3"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_2_4
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform("eclipselink2_4"); //$NON-NLS-1$
	
	
	/**
	 * Not for instantiation
	 */
	private EclipseLinkPlatform() {}
}

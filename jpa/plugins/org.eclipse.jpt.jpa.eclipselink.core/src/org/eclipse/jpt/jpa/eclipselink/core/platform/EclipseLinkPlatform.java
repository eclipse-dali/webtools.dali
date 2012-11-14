/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.platform;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformConfig;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformGroupConfig;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;

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
	
	public static final JpaPlatformGroupConfig GROUP = getJpaPlatformGroupConfig("eclipselink"); //$NON-NLS-1$
	
	public static final JpaPlatformConfig VERSION_1_0 = getJpaPlatformConfig("org.eclipse.eclipselink.platform"); //$NON-NLS-1$
	
	public static final JpaPlatformConfig VERSION_1_1 = getJpaPlatformConfig("eclipselink1_1"); //$NON-NLS-1$
	
	public static final JpaPlatformConfig VERSION_1_2 = getJpaPlatformConfig("eclipselink1_2"); //$NON-NLS-1$
	
	public static final JpaPlatformConfig VERSION_2_0 = getJpaPlatformConfig("eclipselink2_0"); //$NON-NLS-1$
	
	public static final JpaPlatformConfig VERSION_2_1 = getJpaPlatformConfig("eclipselink2_1"); //$NON-NLS-1$
	
	public static final JpaPlatformConfig VERSION_2_2 = getJpaPlatformConfig("eclipselink2_2"); //$NON-NLS-1$
	
	public static final JpaPlatformConfig VERSION_2_3 = getJpaPlatformConfig("eclipselink2_3"); //$NON-NLS-1$
	
	public static final JpaPlatformConfig VERSION_2_4 = getJpaPlatformConfig("eclipselink2_4"); //$NON-NLS-1$
	
	public static final JpaPlatformConfig VERSION_2_5 = getJpaPlatformConfig("eclipselink2_5"); //$NON-NLS-1$

	private static JpaPlatformGroupConfig getJpaPlatformGroupConfig(String platformGroupID) {
		return getJpaPlatformManager().getJpaPlatformGroupConfig(platformGroupID);
	}

	private static JpaPlatformConfig getJpaPlatformConfig(String platformID) {
		return getJpaPlatformManager().getJpaPlatformConfig(platformID);
	}

	private static JpaPlatformManager getJpaPlatformManager() {
		return getJpaWorkspace().getJpaPlatformManager();
	}

	private static JpaWorkspace getJpaWorkspace() {
		return (JpaWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JpaWorkspace.class);
	}
	
	/**
	 * Not for instantiation
	 */
	private EclipseLinkPlatform() {}
}

/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;


public class ELJaxbPlatform {
	
	public static final JaxbPlatformGroupConfig GROUP = getJaxbPlatformGroupConfig("eclipselink"); //$NON-NLS-1$
	
	public static final JaxbPlatformConfig VERSION_2_1 = getJaxbPlatformConfig("eclipselink_2_1"); //$NON-NLS-1$
	
	public static final JaxbPlatformConfig VERSION_2_2 = getJaxbPlatformConfig("eclipselink_2_2"); //$NON-NLS-1$
	
	public static final JaxbPlatformConfig VERSION_2_3 = getJaxbPlatformConfig("eclipselink_2_3"); //$NON-NLS-1$
	
	public static final JaxbPlatformConfig VERSION_2_4 = getJaxbPlatformConfig("eclipselink_2_4"); //$NON-NLS-1$
	
	
	public static JaxbPlatformConfig getDefaultPlatformConfig(IProjectFacetVersion jaxbVersion) {
		if (jaxbVersion.equals(JaxbFacet.VERSION_2_1)) {
			return VERSION_2_1;
		}
		return VERSION_2_4;
	}
	
	
	private static JaxbPlatformGroupConfig getJaxbPlatformGroupConfig(String platformGroupID) {
		return getJaxbPlatformManager().getJaxbPlatformGroupConfig(platformGroupID);
	}

	private static JaxbPlatformConfig getJaxbPlatformConfig(String platformID) {
		return getJaxbPlatformManager().getJaxbPlatformConfig(platformID);
	}

	private static JaxbPlatformManager getJaxbPlatformManager() {
		return getJaxbWorkspace().getJaxbPlatformManager();
	}

	private static JaxbWorkspace getJaxbWorkspace() {
		return (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class);
	}

	/**
	 * Not for instantiation
	 */
	private ELJaxbPlatform() {}
}

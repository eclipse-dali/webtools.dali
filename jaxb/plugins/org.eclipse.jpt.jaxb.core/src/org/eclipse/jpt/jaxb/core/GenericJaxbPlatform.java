/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;

/**
 * Constants pertaining to the Generic JAXB platforms and their group.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public class GenericJaxbPlatform {
	
	public static final JaxbPlatformGroupConfig GROUP = getJaxbPlatformGroupConfig("generic"); //$NON-NLS-1$
	
	public static final JaxbPlatformConfig VERSION_2_1 = getJaxbPlatformConfig("generic_2_1"); //$NON-NLS-1$
	
	public static final JaxbPlatformConfig VERSION_2_2 = getJaxbPlatformConfig("generic_2_2"); //$NON-NLS-1$
	
	
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
	private GenericJaxbPlatform() {}
}

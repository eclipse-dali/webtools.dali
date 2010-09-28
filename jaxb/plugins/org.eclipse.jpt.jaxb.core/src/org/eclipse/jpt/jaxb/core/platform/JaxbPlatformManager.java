/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.platform;

import org.eclipse.core.resources.IProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Entry point to the "jaxbPlatforms" extension point
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
public interface JaxbPlatformManager {
	
	Iterable<JaxbPlatformGroupDescription> getJaxbPlatformGroups();
	
	JaxbPlatformGroupDescription getJaxbPlatformGroup(String groupId);
	
	Iterable<JaxbPlatformDescription> getJaxbPlatforms();
	
	JaxbPlatformDescription getJaxbPlatform(String platformId);
	
	/**
	 * Returns the first JAXB platform registered as a default platform and which supports the given 
	 * JAXB facet version.
	 * Returns null if there are no such registered platforms.
	 */
	JaxbPlatformDescription getDefaultJaxbPlatform(IProjectFacetVersion jaxbFacetVersion);
	
	JaxbPlatformDefinition buildJaxbPlatformDefinition(IProject project);
}

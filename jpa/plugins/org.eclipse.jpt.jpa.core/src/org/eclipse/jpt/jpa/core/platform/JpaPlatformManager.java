/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.platform;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Entry point to the "jpaPlatforms" extension point
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
public interface JpaPlatformManager {
	
	Iterable<JpaPlatformGroupDescription> getJpaPlatformGroups();
	
	JpaPlatformGroupDescription getJpaPlatformGroup(String groupId);
	
	Iterable<JpaPlatformDescription> getJpaPlatforms();
	
	JpaPlatformDescription getJpaPlatform(String platformId);
	
	/**
	 * Returns the first JPA platform registered as a default platform and which supports the given 
	 * JPA facet version.
	 * Returns null if there are no such registered platforms.
	 */
	JpaPlatformDescription getDefaultJpaPlatform(IProjectFacetVersion jpaFacetVersion);
	
	JpaPlatform buildJpaPlatformImplementation(IProject project);
}

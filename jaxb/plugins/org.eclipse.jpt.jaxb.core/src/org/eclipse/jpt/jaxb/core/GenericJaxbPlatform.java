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

import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupDescription;

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
	
	public static final JaxbPlatformGroupDescription GROUP 
			= JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatformGroup("generic"); //$NON-NLS-1$
	
	public static final JaxbPlatformDescription VERSION_2_2 
			= JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatform("generic_2_2"); //$NON-NLS-1$
	
	
	/**
	 * Not for instantiation
	 */
	private GenericJaxbPlatform() {}
}

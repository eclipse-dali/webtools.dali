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

import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;

/**
 * Constants pertaining to the Generic JPA platforms and their group.
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
public class GenericPlatform {
	
	public static final JpaPlatformGroupDescription GROUP 
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatformGroup("generic"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_1_0 
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform("generic"); //$NON-NLS-1$
	
	public static final JpaPlatformDescription VERSION_2_0 
			= JptJpaCorePlugin.getJpaPlatformManager().getJpaPlatform("generic2_0"); //$NON-NLS-1$
	
	/**
	 * Not for instantiation
	 */
	private GenericPlatform() {}
}

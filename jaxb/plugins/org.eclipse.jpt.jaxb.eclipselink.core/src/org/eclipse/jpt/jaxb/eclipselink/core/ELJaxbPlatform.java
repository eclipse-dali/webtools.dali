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

import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformGroupDescription;


public class ELJaxbPlatform {
	
	public static final JaxbPlatformGroupDescription GROUP 
			= JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatformGroup("eclipselink"); //$NON-NLS-1$
	
	public static final JaxbPlatformDescription VERSION_2_1
			= JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatform("eclipselink_2_1"); //$NON-NLS-1$
	
	public static final JaxbPlatformDescription VERSION_2_2
			= JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatform("eclipselink_2_2"); //$NON-NLS-1$
	
	public static final JaxbPlatformDescription VERSION_2_3
			= JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatform("eclipselink_2_3"); //$NON-NLS-1$
	
	
	/**
	 * Not for instantiation
	 */
	private ELJaxbPlatform() {}
}

/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_3;

import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbPlatform;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.ELJaxb_2_2_PlatformDefinition;


public class ELJaxb_2_3_PlatformDefinition
		extends  ELJaxb_2_2_PlatformDefinition {
	
	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new ELJaxb_2_3_PlatformDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}
	
	
	protected ELJaxb_2_3_PlatformDefinition() {
		super();
	}
	
	
	@Override
	public JaxbPlatformConfig getConfig() {
		return ELJaxbPlatform.VERSION_2_3;
	}
}

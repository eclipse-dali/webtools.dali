/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_3;

import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.AbstractELJaxb_2_2_PlatformDefinition;


public class ELJaxb_2_3_PlatformDefinition
		extends AbstractELJaxb_2_2_PlatformDefinition {
	
	/**
	 * See <code>org.eclipse.jpt.jaxb.eclipselink.core/plugin.xml:org.eclipse.jpt.jaxb.core.jaxbPlatforms</code>.
	 */
	public static final String ID = "eclipselink_2_3"; //$NON-NLS-1$

	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new ELJaxb_2_3_PlatformDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}
	
	
	private ELJaxb_2_3_PlatformDefinition() {
		super();
	}
	
	
	@Override
	protected String getConfigId() {
		return ID;
	}
}

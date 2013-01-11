/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_1;

import org.eclipse.jpt.jaxb.core.internal.jaxb21.GenericJaxb_2_1_PlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;


public class ELJaxb_2_1_PlatformDefinition
		extends  AbstractELJaxb_2_1_PlatformDefinition {
	
	/**
	 * See <code>org.eclipse.jpt.jaxb.eclipselink.core/plugin.xml:org.eclipse.jpt.jaxb.core.jaxbPlatforms</code>.
	 */
	public static final String ID = "eclipselink_2_1"; //$NON-NLS-1$

	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new ELJaxb_2_1_PlatformDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}
	
	
	private ELJaxb_2_1_PlatformDefinition() {
		super();
	}
	
	
	@Override
	protected JaxbPlatformDefinition getGenericJaxbPlatformDefinition() {
		return GenericJaxb_2_1_PlatformDefinition.instance();
	}
	
	@Override
	protected String getConfigId() {
		return ID;
	}
}

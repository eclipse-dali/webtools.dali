/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.jaxb22;

import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.internal.GenericJaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.internal.jaxb21.GenericJaxb_2_1_Factory;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;


public class GenericJaxb_2_2_PlatformDefinition
		extends GenericJaxbPlatformDefinition {
	
	/**
	 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:org.eclipse.jpt.jaxb.core.jaxbPlatforms</code>.
	 */
	public static final String ID = "generic_2_2"; //$NON-NLS-1$

	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new GenericJaxb_2_2_PlatformDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}
	
	
	private GenericJaxb_2_2_PlatformDefinition() {
		super();
	}
	
	
	@Override
	protected String getConfigId() {
		return ID;
	}
	
	public JaxbFactory getFactory() {
		return GenericJaxb_2_1_Factory.instance();
	}
}

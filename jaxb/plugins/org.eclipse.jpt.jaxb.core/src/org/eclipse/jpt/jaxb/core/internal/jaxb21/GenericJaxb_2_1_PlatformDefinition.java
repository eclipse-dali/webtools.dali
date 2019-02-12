/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.jaxb21;

import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.internal.GenericJaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;

public class GenericJaxb_2_1_PlatformDefinition
		extends GenericJaxbPlatformDefinition {

	/**
	 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:org.eclipse.jpt.jaxb.core.jaxbPlatforms</code>.
	 */
	public static final String ID = "generic_2_1"; //$NON-NLS-1$

	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new GenericJaxb_2_1_PlatformDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}
	
	private GenericJaxb_2_1_PlatformDefinition() {
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

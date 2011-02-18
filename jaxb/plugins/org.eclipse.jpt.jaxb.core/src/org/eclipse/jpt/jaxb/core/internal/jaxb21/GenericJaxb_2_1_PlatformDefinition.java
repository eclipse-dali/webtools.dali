/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.jaxb21;

import org.eclipse.jpt.jaxb.core.GenericJaxbPlatform;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;

public class GenericJaxb_2_1_PlatformDefinition
		extends  AbstractJaxb_2_1_PlatformDefinition {
	
	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new GenericJaxb_2_1_PlatformDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}
	
	protected GenericJaxb_2_1_PlatformDefinition() {
		super();
	}
	
	
	public JaxbPlatformDescription getDescription() {
		return GenericJaxbPlatform.VERSION_2_1;
	}
	
	public JaxbFactory getFactory() {
		return GenericJaxb_2_1_Factory.instance();
	}
}

/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_4;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.AbstractELJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.Oxm;


public class ELJaxb_2_4_PlatformDefinition
		extends AbstractELJaxb_2_2_PlatformDefinition {
	
	/**
	 * See <code>org.eclipse.jpt.jaxb.eclipselink.core/plugin.xml:org.eclipse.jpt.jaxb.core.jaxbPlatforms</code>.
	 */
	public static final String ID = "eclipselink_2_4"; //$NON-NLS-1$

	// singleton
	private static final JaxbPlatformDefinition INSTANCE = new ELJaxb_2_4_PlatformDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static JaxbPlatformDefinition instance() {
		return INSTANCE;
	}
	
	
	private ELJaxb_2_4_PlatformDefinition() {
		super();
	}
	
	
	@Override
	protected String getConfigId() {
		return ID;
	}
	
	@Override
	protected JptResourceType getMostRecentOxmResourceType() {
		return Oxm.RESOURCE_TYPE_2_4;
	}
}

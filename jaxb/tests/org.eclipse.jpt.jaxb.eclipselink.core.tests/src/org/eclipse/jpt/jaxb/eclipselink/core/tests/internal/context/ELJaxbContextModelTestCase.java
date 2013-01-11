/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_1.ELJaxb_2_1_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_4.ELJaxb_2_4_PlatformDefinition;


public abstract class ELJaxbContextModelTestCase
		extends JaxbContextModelTestCase {
	
	public ELJaxbContextModelTestCase(String name) {
		super(name);
	}
	
	
	@Override
	protected String getPlatformID() {
		return this.getProjectFacetVersion().equals(JaxbProject.FACET_VERSION_2_1) ?
				ELJaxb_2_1_PlatformDefinition.ID :
				ELJaxb_2_4_PlatformDefinition.ID;
	}
}

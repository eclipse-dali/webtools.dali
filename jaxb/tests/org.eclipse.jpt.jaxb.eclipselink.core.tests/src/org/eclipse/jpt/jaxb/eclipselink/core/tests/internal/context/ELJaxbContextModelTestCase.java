/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.jaxb.core.internal.libval.JaxbLibraryValidatorTools;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_1.ELJaxb_2_1_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_5.ELJaxb_2_5_PlatformDefinition;


public abstract class ELJaxbContextModelTestCase
		extends JaxbContextModelTestCase {
	
	public ELJaxbContextModelTestCase(String name) {
		super(name);
	}
	
	
	@Override
	protected String getPlatformID() {
		return this.getProjectFacetVersion().equals(JaxbLibraryValidatorTools.FACET_VERSION_2_1) ?
				ELJaxb_2_1_PlatformDefinition.ID :
				ELJaxb_2_5_PlatformDefinition.ID;
	}
}

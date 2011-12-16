/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbPlatform;


public abstract class ELJaxbContextModelTestCase
		extends JaxbContextModelTestCase {
	
	public ELJaxbContextModelTestCase(String name) {
		super(name);
	}
	
	
	@Override
	protected JaxbPlatformDescription getPlatform() {
		return ELJaxbPlatform.getDefaultPlatform(getProjectFacetVersion());
	}
}

/*******************************************************************************
 *  Copyright (c) 2009, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;

public abstract class EclipseLink2_0ContextModelTestCase extends EclipseLinkContextModelTestCase
{
	protected EclipseLink2_0ContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaFacet.VERSION_2_0.getVersionString();
	}

	@Override
	protected JpaPlatformDescription getJpaPlatformDescription() {
		return EclipseLinkPlatform.VERSION_2_0;
	}

	@Override
	protected String getEclipseLinkSchemaVersion() {
		return EclipseLink2_0.SCHEMA_VERSION;
	}
}

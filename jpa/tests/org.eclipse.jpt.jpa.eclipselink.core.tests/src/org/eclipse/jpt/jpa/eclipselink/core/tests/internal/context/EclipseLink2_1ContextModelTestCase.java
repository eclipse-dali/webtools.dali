/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;

public abstract class EclipseLink2_1ContextModelTestCase extends EclipseLinkContextModelTestCase
{
	protected EclipseLink2_1ContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaFacet.VERSION_2_0.getVersionString();
	}

	@Override
	protected JpaPlatformDescription getJpaPlatformDescription() {
		return EclipseLinkPlatform.VERSION_2_1;
	}

	@Override
	protected String getEclipseLinkSchemaVersion() {
		return EclipseLink2_1.SCHEMA_VERSION;
	}
}

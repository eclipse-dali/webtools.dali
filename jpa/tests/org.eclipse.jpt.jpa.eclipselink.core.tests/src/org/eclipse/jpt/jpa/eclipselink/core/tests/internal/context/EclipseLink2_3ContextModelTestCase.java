/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLink2_3;

public abstract class EclipseLink2_3ContextModelTestCase extends EclipseLinkContextModelTestCase
{
	protected EclipseLink2_3ContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject2_0.FACET_VERSION_STRING;
	}

	@Override
	protected JpaPlatformDescription getJpaPlatformDescription() {
		return EclipseLinkPlatform.VERSION_2_3;
	}

	@Override
	protected String getEclipseLinkSchemaVersion() {
		return EclipseLink2_3.SCHEMA_VERSION;
	}
}

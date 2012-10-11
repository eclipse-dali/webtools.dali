/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformConfig;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;

public abstract class EclipseLink2_0ContextModelTestCase extends EclipseLinkContextModelTestCase
{
	protected EclipseLink2_0ContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject2_0.FACET_VERSION_STRING;
	}

	@Override
	protected JpaPlatformConfig getJpaPlatformConfig() {
		return EclipseLinkPlatform.VERSION_2_0;
	}

	@Override
	protected String getEclipseLinkSchemaVersion() {
		return EclipseLink2_0.SCHEMA_VERSION;
	}
}

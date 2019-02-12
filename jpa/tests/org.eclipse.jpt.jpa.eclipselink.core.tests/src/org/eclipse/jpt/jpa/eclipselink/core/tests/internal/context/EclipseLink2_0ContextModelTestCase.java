/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_0;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;

public abstract class EclipseLink2_0ContextModelTestCase
	extends EclipseLinkContextModelTestCase
{
	protected EclipseLink2_0ContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject2_0.FACET_VERSION_STRING;
	}

	@Override
	protected String getJpaPlatformID() {
		return EclipseLinkJpaPlatformFactory2_0.ID;
	}

	@Override
	protected String getEclipseLinkSchemaVersion() {
		return EclipseLink2_0.SCHEMA_VERSION;
	}
}

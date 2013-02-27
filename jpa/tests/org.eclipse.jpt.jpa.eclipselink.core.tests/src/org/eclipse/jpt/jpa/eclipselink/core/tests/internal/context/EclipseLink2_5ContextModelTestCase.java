/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.jpa.core.jpa2_1.JpaProject2_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLink2_5JpaPlatformFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.EclipseLink2_5;

public abstract class EclipseLink2_5ContextModelTestCase
	extends EclipseLinkContextModelTestCase
{
	protected EclipseLink2_5ContextModelTestCase(String name) {
		super(name);
	}

	@Override
	protected String getJpaFacetVersionString() {
		return JpaProject2_1.FACET_VERSION_STRING;
	}

	@Override
	protected String getJpaPlatformID() {
		return EclipseLink2_5JpaPlatformFactory.ID;
	}

	@Override
	protected String getEclipseLinkSchemaVersion() {
		return EclipseLink2_5.SCHEMA_VERSION;
	}
}

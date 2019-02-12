/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context;

import org.eclipse.jpt.jpa.core.jpa2_1.JpaProject2_1;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_5;
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
		return EclipseLinkJpaPlatformFactory2_5.ID;
	}

	@Override
	protected String getEclipseLinkSchemaVersion() {
		return EclipseLink2_5.SCHEMA_VERSION;
	}
}

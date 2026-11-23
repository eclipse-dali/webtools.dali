/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v3_0;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_5.EclipseLink2_5;

/**
 * EclipseLink 3.0 <code>eclipselink-orm.xml</code> constants.
 * <p>
 * The EclipseLink mapping file keeps its own namespace across the Jakarta
 * move, so only the schema version and location change from 2.5. The element
 * set is identical to 2.5 apart from the removal of the deprecated
 * <code>shared</code> attribute on <code>&lt;cache&gt;</code> (replaced by
 * <code>isolation</code>), so the existing EclipseLink ORM resource model is
 * reused. <code>shared</code> stays in that model for the 2.x schemas; the
 * caching UI does not offer it on EclipseLink 3.0 and later platforms.
 */
@SuppressWarnings("nls")
public interface EclipseLink3_0
	extends EclipseLink2_5
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_3_0.xsd";
	String SCHEMA_VERSION	= "3.0";

	// EclipseLink 3.0 specific nodes

}

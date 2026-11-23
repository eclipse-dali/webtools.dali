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
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v3_1;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v3_0.EclipseLink3_0;

/**
 * EclipseLink 3.1 <code>eclipselink-orm.xml</code> constants.
 * <p>
 * This is the mapping file schema used by <em>EclipseLink 4.0</em>: EclipseLink
 * publishes no <code>eclipselink_orm_4_0.xsd</code> and the 4.0 runtime ships
 * <code>eclipselink_orm_3_1.xsd</code> as its EclipseLink mapping file schema.
 * Its element set is identical to 3.0; the only schema change is the
 * <code>UUID</code> generation type (Jakarta Persistence 3.1), which Dali does
 * not model yet.
 */
@SuppressWarnings("nls")
public interface EclipseLink3_1
	extends EclipseLink3_0
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_3_1.xsd";
	String SCHEMA_VERSION	= "3.1";

	// EclipseLink 3.1 specific nodes

}

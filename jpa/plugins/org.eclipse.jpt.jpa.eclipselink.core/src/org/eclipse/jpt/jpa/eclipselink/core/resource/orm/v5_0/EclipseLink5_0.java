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
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v5_0;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v3_1.EclipseLink3_1;

/**
 * EclipseLink 5.0 <code>eclipselink-orm.xml</code> constants.
 * <p>
 * <code>eclipselink_orm_5_0.xsd</code> is the first new EclipseLink mapping
 * file schema since 2.5. It picks up the Jakarta Persistence 3.2 additions:
 * a <code>check-constraint</code> element and <code>comment</code> element on
 * the table and column types, an <code>options</code> attribute on seventeen
 * types, and <code>second-precision</code> on <code>column</code>.
 * <p>
 * Those elements are not yet represented in the EclipseLink ORM resource
 * model. Dali does not model them for the generic Jakarta Persistence 3.2
 * mapping file either, so a document that uses them is validated against the
 * schema but its new elements are not surfaced in the context model or the
 * JPA Details view. Modelling them belongs at the generic layer first, with
 * EclipseLink following.
 */
@SuppressWarnings("nls")
public interface EclipseLink5_0
	extends EclipseLink3_1
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_5_0.xsd";
	String SCHEMA_VERSION	= "5.0";

	// EclipseLink 5.0 specific nodes - not yet modelled, see above

}

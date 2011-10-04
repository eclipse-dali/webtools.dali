/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.EclipseLinkMultitenancy;

public interface OrmEclipseLinkMultitenancy
	extends EclipseLinkMultitenancy, XmlContextNode
{
	ListIterable<OrmReadOnlyTenantDiscriminatorColumn> getTenantDiscriminatorColumns();

	ListIterable<OrmTenantDiscriminatorColumn> getSpecifiedTenantDiscriminatorColumns();
	OrmTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn();
	OrmTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn(int index);
	
	ListIterable<OrmVirtualTenantDiscriminatorColumn> getDefaultTenantDiscriminatorColumns();
}

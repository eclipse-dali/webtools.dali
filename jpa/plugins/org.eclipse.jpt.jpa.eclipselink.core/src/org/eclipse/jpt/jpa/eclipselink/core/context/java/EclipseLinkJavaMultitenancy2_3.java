/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.java;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenancy2_3;

public interface EclipseLinkJavaMultitenancy2_3
	extends EclipseLinkMultitenancy2_3
{

	ListIterable<EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns();
	EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn();
	EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn(int index);
}
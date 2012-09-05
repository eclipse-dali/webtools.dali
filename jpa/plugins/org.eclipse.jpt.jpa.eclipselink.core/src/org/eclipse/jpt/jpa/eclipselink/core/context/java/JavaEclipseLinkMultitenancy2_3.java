/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.java;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenancy2_3;

public interface JavaEclipseLinkMultitenancy2_3
	extends EclipseLinkMultitenancy2_3
{

	ListIterable<JavaTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns();
	JavaTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn();
	JavaTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn(int index);
}
/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.EclipseLinkMultitenancy;

public interface JavaEclipseLinkMultitenancy
	extends EclipseLinkMultitenancy, JavaJpaContextNode
{

	ListIterable<JavaReadOnlyTenantDiscriminatorColumn> getTenantDiscriminatorColumns();

	ListIterable<JavaTenantDiscriminatorColumn> getSpecifiedTenantDiscriminatorColumns();
	JavaTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn();
	JavaTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn(int index);

	ListIterable<JavaVirtualTenantDiscriminatorColumn> getDefaultTenantDiscriminatorColumns();
}
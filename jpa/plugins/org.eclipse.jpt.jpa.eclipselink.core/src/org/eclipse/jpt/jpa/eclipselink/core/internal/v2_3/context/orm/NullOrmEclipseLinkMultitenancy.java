/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.EclipseLinkMultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.TenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmEclipseLinkMultitenancy;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmVirtualTenantDiscriminatorColumn;

public class NullOrmEclipseLinkMultitenancy
	extends AbstractOrmXmlContextNode
	implements OrmEclipseLinkMultitenancy
{

	public NullOrmEclipseLinkMultitenancy(OrmEclipseLinkNonEmbeddableTypeMapping parent) {
		super(parent);
	}


	// ********** multitenant **********

	public boolean isMultitenant() {
		return false;
	}

	public boolean isDefaultMultitenant() {
		return false;
	}

	public boolean isSpecifiedMultitenant() {
		return false;
	}

	public void setSpecifiedMultitenant(boolean isMultitenant) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}


	// ********** type **********

	public EclipseLinkMultitenantType getType() {
		return null;
	}

	public EclipseLinkMultitenantType getSpecifiedType() {
		return null;
	}

	public void setSpecifiedType(EclipseLinkMultitenantType type) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public EclipseLinkMultitenantType getDefaultType() {
		return null;
	}


	// ********** include criteria **********

	public boolean isIncludeCriteria() {
		return false;
	}

	public Boolean getSpecifiedIncludeCriteria() {
		return null;
	}

	public void setSpecifiedIncludeCriteria(Boolean includeCriteria) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public boolean isDefaultIncludeCriteria() {
		return false;
	}



	// ********** tenant discriminator columns **********

	public ListIterable<OrmReadOnlyTenantDiscriminatorColumn> getTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public ListIterable<OrmTenantDiscriminatorColumn> getSpecifiedTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public boolean hasSpecifiedTenantDiscriminatorColumns() {
		return false;
	}

	public OrmTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn() {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public OrmTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn(int index) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public void removeSpecifiedTenantDiscriminatorColumn(TenantDiscriminatorColumn tenantDiscriminatorColumn) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public void removeSpecifiedTenantDiscriminatorColumn(int index) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public void moveSpecifiedTenantDiscriminatorColumn(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public TextRange getValidationTextRange() {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public ListIterable<OrmVirtualTenantDiscriminatorColumn> getDefaultTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getDefaultTenantDiscriminatorColumnsSize() {
		return 0;
	}

}

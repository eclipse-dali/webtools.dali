/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenantType2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.TenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmVirtualTenantDiscriminatorColumn2_3;

public class NullOrmEclipseLinkMultitenancy2_3
	extends AbstractOrmXmlContextNode
	implements OrmEclipseLinkMultitenancy2_3
{

	public NullOrmEclipseLinkMultitenancy2_3(OrmEclipseLinkNonEmbeddableTypeMapping parent) {
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

	public EclipseLinkMultitenantType2_3 getType() {
		return null;
	}

	public EclipseLinkMultitenantType2_3 getSpecifiedType() {
		return null;
	}

	public void setSpecifiedType(EclipseLinkMultitenantType2_3 type) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public EclipseLinkMultitenantType2_3 getDefaultType() {
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

	public ListIterable<OrmReadOnlyTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public ListIterable<OrmTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public boolean hasSpecifiedTenantDiscriminatorColumns() {
		return false;
	}

	public OrmTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn() {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public OrmTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn(int index) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public void removeSpecifiedTenantDiscriminatorColumn(TenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
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

	public ListIterable<OrmVirtualTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getDefaultTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public boolean specifiedTenantDiscriminatorColumnsAllowed() {
		return false;
	}
}

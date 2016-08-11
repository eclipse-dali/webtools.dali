/*******************************************************************************
 * Copyright (c) 2011, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenantType2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVirtualTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3;

public class EclipseLinkNullOrmMultitenancy2_3
	extends AbstractOrmXmlContextModel<EclipseLinkOrmNonEmbeddableTypeMapping>
	implements EclipseLinkOrmMultitenancy2_3
{

	public EclipseLinkNullOrmMultitenancy2_3(EclipseLinkOrmNonEmbeddableTypeMapping parent) {
		super(parent);
	}


	// ********** multitenant **********

	public boolean isMultitenant() {
		return false;
	}

	public boolean getDefaultMultitenant() {
		return false;
	}

	public boolean getSpecifiedMultitenant() {
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

	public boolean getDefaultIncludeCriteria() {
		return false;
	}



	// ********** tenant discriminator columns **********

	public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public ListIterable<EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public boolean hasSpecifiedTenantDiscriminatorColumns() {
		return false;
	}

	public EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn() {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn(int index) {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public void removeSpecifiedTenantDiscriminatorColumn(EclipseLinkSpecifiedTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
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

	public ListIterable<EclipseLinkVirtualTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getDefaultTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public boolean specifiedTenantDiscriminatorColumnsAllowed() {
		return false;
	}

	public boolean usesPrimaryKeyTenantDiscriminatorColumns() {
		return false;
	}
}

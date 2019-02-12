/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenantType2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVirtualTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3;

public class EclipseLinkNullJavaMultitenancy2_3
	extends AbstractJavaContextModel<EclipseLinkJavaNonEmbeddableTypeMapping>
	implements EclipseLinkJavaMultitenancy2_3
{

	public EclipseLinkNullJavaMultitenancy2_3(EclipseLinkJavaNonEmbeddableTypeMapping parent) {
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

	public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public ListIterable<EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public boolean hasSpecifiedTenantDiscriminatorColumns() {
		return false;
	}

	public EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn() {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn(int index) {
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

	public ListIterable<EclipseLinkVirtualTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns() {
		return EmptyListIterable.instance();
	}

	public int getDefaultTenantDiscriminatorColumnsSize() {
		return 0;
	}

	public TextRange getValidationTextRange() {
		throw new UnsupportedOperationException("Multitenancy is only supported in EclipseLink version 2.3 and higher"); //$NON-NLS-1$
	}

	public boolean specifiedTenantDiscriminatorColumnsAllowed() {
		return false;
	}

	public boolean usesPrimaryKeyTenantDiscriminatorColumns() {
		return false;
	}
}

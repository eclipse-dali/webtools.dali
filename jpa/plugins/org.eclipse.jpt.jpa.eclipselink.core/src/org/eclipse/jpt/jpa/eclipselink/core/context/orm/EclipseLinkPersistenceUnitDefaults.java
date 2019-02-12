/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.orm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmPersistenceUnitDefaults2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkSpecifiedAccessMethodsContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3;

/**
 * EclipseLink <code>orm.xml</code> file
 * <br>
 * <code>persistence-unit-defaults</code> element
 */
public interface EclipseLinkPersistenceUnitDefaults
	extends OrmPersistenceUnitDefaults2_0, EclipseLinkSpecifiedAccessMethodsContainer
{

	// ********** tenant discriminator columns **********

	/**
	 * Return a list iterable of the tenant discriminator columns.
	 * This will not be null.
	 */
	ListIterable<EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumns();
		String TENANT_DISCRIMINATOR_COLUMNS_LIST = "tenantDiscriminatorColumns"; //$NON-NLS-1$

	/**
	 * Return the number of tenant discriminator columns.
	 */
	int getTenantDiscriminatorColumnsSize();

	/**
	 * Return whether there are any tenant discriminator columns.
	 */
	boolean hasTenantDiscriminatorColumns();

	/**
	 * Add a tenant discriminator column and return the object
	 * representing it.
	 */
	EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 addTenantDiscriminatorColumn();

	/**
	 * Add a tenant discriminator column and return the object
	 * representing it.
	 */
	EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 addTenantDiscriminatorColumn(int index);

	/**
	 * Remove the tenant discriminator column.
	 */
	void removeTenantDiscriminatorColumn(EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn);

	/**
	 * Remove the tenant discriminator column at the index.
	 */
	void removeTenantDiscriminatorColumn(int index);

	/**
	 * Move the tenant discriminator column from the source index to the target index.
	 */
	void moveTenantDiscriminatorColumn(int targetIndex, int sourceIndex);
}

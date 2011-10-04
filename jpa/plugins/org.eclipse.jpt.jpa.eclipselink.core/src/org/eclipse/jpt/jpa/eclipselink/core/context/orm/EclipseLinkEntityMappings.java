/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.orm;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.ReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.TenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmTenantDiscriminatorColumn;

public interface EclipseLinkEntityMappings 
	extends EntityMappings
{
	/**
	 * Return all the converters defined in both the entity mappings and its
	 * entities (but <em>not</em> in any associated Java annotations).
	 */
	Iterable<EclipseLinkConverter> getMappingFileConverters();

	OrmEclipseLinkConverterContainer getConverterContainer();


	// ********** tenant discriminator columns **********

	/**
	 * Return the tenant discriminator columns whether specified or default.
	 */
	ListIterable<ReadOnlyTenantDiscriminatorColumn> getTenantDiscriminatorColumns();

	/**
	 * Return the number of tenant discriminator columns, whether specified and default.
	 */
	int getTenantDiscriminatorColumnsSize();


	// ********** specified tenant discriminator columns **********

	/**
	 * Return a list iterable of the specified tenant discriminator columns.
	 * This will not be null.
	 */
	ListIterable<OrmTenantDiscriminatorColumn> getSpecifiedTenantDiscriminatorColumns();
		String SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST = "specifiedTenantDiscriminatorColumns"; //$NON-NLS-1$

	/**
	 * Return the number of specified tenant discriminator columns.
	 */
	int getSpecifiedTenantDiscriminatorColumnsSize();

	/**
	 * Return whether there are any specified tenant discriminator columns.
	 */
	boolean hasSpecifiedTenantDiscriminatorColumns();

	/**
	 * Add a specified tenant discriminator column and return the object
	 * representing it.
	 */
	TenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn();

	/**
	 * Add a specified tenant discriminator column and return the object
	 * representing it.
	 */
	TenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn(int index);

	/**
	 * Remove the specified tenant discriminator column.
	 */
	void removeSpecifiedTenantDiscriminatorColumn(TenantDiscriminatorColumn tenantDiscriminatorColumn);

	/**
	 * Remove the specified tenant discriminator column at the index.
	 */
	void removeSpecifiedTenantDiscriminatorColumn(int index);

	/**
	 * Move the specified tenant discriminator column from the source index to the target index.
	 */
	void moveSpecifiedTenantDiscriminatorColumn(int targetIndex, int sourceIndex);


	// ********** default tenant discriminator columns **********

	/**
	 * Return a list iterable of the default tenant discriminator columns.
	 * This will not be null. If there are specified tenant discriminator columns
	 * then there will be no default tenant discriminator columns.
	 */
	ListIterable<? extends ReadOnlyTenantDiscriminatorColumn> getDefaultTenantDiscriminatorColumns();
		String DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST = "defaultTenantDiscriminatorColumns"; //$NON-NLS-1$

	/**
	 * Return the number of default tenant discriminator columns.
	 */
	int getDefaultTenantDiscriminatorColumnsSize();
}

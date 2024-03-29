/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.orm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkSpecifiedAccessMethodsContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;

public interface EclipseLinkEntityMappings 
	extends EntityMappings, EclipseLinkSpecifiedAccessMethodsContainer
{
	/**
	 * Return all the converters defined in both the entity mappings and its
	 * entities (but <em>not</em> in any associated Java annotations).
	 */
	Iterable<EclipseLinkConverter> getMappingFileConverters();

	EclipseLinkOrmConverterContainer getConverterContainer();


	// ********** tenant discriminator columns **********

	/**
	 * Return the tenant discriminator columns whether specified or default.
	 */
	ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumns();

	/**
	 * Return the number of tenant discriminator columns, whether specified and default.
	 */
	int getTenantDiscriminatorColumnsSize();


	// ********** specified tenant discriminator columns **********

	/**
	 * Return a list iterable of the specified tenant discriminator columns.
	 * This will not be null.
	 */
	ListIterable<EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns();
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
	EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn();

	/**
	 * Add a specified tenant discriminator column and return the object
	 * representing it.
	 */
	EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn(int index);

	/**
	 * Remove the specified tenant discriminator column.
	 */
	void removeSpecifiedTenantDiscriminatorColumn(EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn);

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
	ListIterable<? extends EclipseLinkTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns();
		String DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST = "defaultTenantDiscriminatorColumns"; //$NON-NLS-1$

	/**
	 * Return the number of default tenant discriminator columns.
	 */
	int getDefaultTenantDiscriminatorColumnsSize();


	ListIterable<EclipseLinkOrmUuidGenerator> getUuidGenerators();
	int getUuidGeneratorsSize();
	EclipseLinkOrmUuidGenerator addUuidGenerator();
	EclipseLinkOrmUuidGenerator addUuidGenerator(int index);
	void removeUuidGenerator(int index);
	void removeUuidGenerator(EclipseLinkOrmUuidGenerator uuidGenerator);
	void moveUuidGenerator(int targetIndex, int sourceIndex);
		String UUID_GENERATORS_LIST = "uuidGenerators"; //$NON-NLS-1$

}

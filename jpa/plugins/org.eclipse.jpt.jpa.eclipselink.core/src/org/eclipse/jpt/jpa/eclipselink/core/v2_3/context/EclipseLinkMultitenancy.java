/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.v2_3.context;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;

/**
 * EclipseLink multitenancy
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
public interface EclipseLinkMultitenancy
	extends JpaContextNode
{

	// ********** type **********

	/**
	 * This is the combination of defaultType and specifiedType.
	 * If getSpecifiedType() returns null, then return getDefaultType()
	 */
	EclipseLinkMultitenantType getType();

	EclipseLinkMultitenantType getSpecifiedType();	
	void setSpecifiedType(EclipseLinkMultitenantType type);
		String SPECIFIED_TYPE_PROPERTY = "specifiedType"; //$NON-NLS-1$

	EclipseLinkMultitenantType getDefaultType();		
		String DEFAULT_TYPE_PROPERTY = "defaultType"; //$NON-NLS-1$
		EclipseLinkMultitenantType DEFAULT_TYPE = EclipseLinkMultitenantType.SINGLE_TABLE;


	// ********** include criteria (EclipseLink 2.4+) **********

	/**
	 * This is the combination of defaultIncludeCriteria and specifiedIncludeCriteria.
	 * If getSpecifiedIncludeCriteria() returns null, then return isDefaultIncludeCriteria()
	 */
	boolean isIncludeCriteria();

	Boolean getSpecifiedIncludeCriteria();
	void setSpecifiedIncludeCriteria(Boolean includeCriteria);
		String SPECIFIED_INCLUDE_CRITERIA_PROPERTY = "specifiedIncludeCriteria"; //$NON-NLS-1$

	boolean isDefaultIncludeCriteria();
		String DEFAULT_INCLUDE_CRITERIA_PROPERTY = "defaultIncludeCriteria"; //$NON-NLS-1$
		boolean DEFAULT_INCLUDE_CRITERIA = true;


	// ********** tenant discriminator columns **********

	/**
	 * Return a list iterable of the specified tenant discriminator columns.
	 * This will not be null.
	 */
	ListIterable<? extends TenantDiscriminatorColumn> getSpecifiedTenantDiscriminatorColumns();
		String SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST = "specifiedTenantDiscriminatorColumns"; //$NON-NLS-1$

	/**
	 * Return the number of specified tenant discriminator columns.
	 */
	int getSpecifiedTenantDiscriminatorColumnsSize();

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

}

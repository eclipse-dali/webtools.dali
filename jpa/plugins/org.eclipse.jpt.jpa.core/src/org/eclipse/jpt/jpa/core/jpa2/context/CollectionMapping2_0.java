/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;

/**
 * JPA 2.0 collection mapping (e.g. 1:m, m:m, element collection)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 2.3
 */
public interface CollectionMapping2_0
	extends CollectionMapping, AttributeMapping2_0, ConvertibleKeyMapping2_0
{
	// ********** map key class **********
	
	String getMapKeyClass();

	String getSpecifiedMapKeyClass();
	void setSpecifiedMapKeyClass(String value);
		String SPECIFIED_MAP_KEY_CLASS_PROPERTY = "specifiedMapKeyClass"; //$NON-NLS-1$

	String getDefaultMapKeyClass();
		String DEFAULT_MAP_KEY_CLASS_PROPERTY = "defaultMapKeyClass"; //$NON-NLS-1$

	/**
	 * Return the character to be used for browsing or creating the map key
	 * class {@link IType}.
	 * @see org.eclipse.jdt.core.IType#getFullyQualifiedName(char)
	 */
	char getMapKeyClassEnclosingTypeSeparator();

	/**
	 * If the map key class is specified, this will return it fully qualified.
	 * If not specified, it returns the default map key class, which is always
	 * fully qualified.
	 */
	String getFullyQualifiedMapKeyClass();
		String FULLY_QUALIFIED_MAP_KEY_CLASS_PROPERTY = "fullyQualifiedMapKeyClass"; //$NON-NLS-1$


	// ********** map key column **********

	/**
	 * Return the map key column for this collection mapping.
	 */
	SpecifiedColumn getMapKeyColumn();

	AttributeOverrideContainer getMapKeyAttributeOverrideContainer();

	// ********** map key join columns **********

	/**
	 * Return the map key join columns whether specified or default.
	 */
	ListIterable<? extends ReadOnlyJoinColumn> getMapKeyJoinColumns();

	/**
	 * Return the number of map key join columns, whether specified and default.
	 */
	int getMapKeyJoinColumnsSize();


	// ********** specified map key join columns **********

	/**
	 * Change notification identifier for "specifiedMapKeyJoinColumns" list
	 */
	String SPECIFIED_MAP_KEY_JOIN_COLUMNS_LIST = "specifiedMapKeyJoinColumns"; //$NON-NLS-1$

	/**
	 * Return the specified map key join columns.
	 */
	ListIterable<? extends SpecifiedJoinColumn> getSpecifiedMapKeyJoinColumns();

	/**
	 * Return the number of specified join columns.
	 */
	int getSpecifiedMapKeyJoinColumnsSize();

	/**
	 * Return whether the mapping has any specified map key join columns.
	 * (Equivalent to {@link #getSpecifiedMapKeyJoinColumnsSize()} != 0.)
	 */
	boolean hasSpecifiedMapKeyJoinColumns();

	/**
	 * Return the specified map key join column at the specified index.
	 */
	SpecifiedJoinColumn getSpecifiedMapKeyJoinColumn(int index);

	/**
	 * Add a specified map key join column to the relationship.
	 */
	SpecifiedJoinColumn addSpecifiedMapKeyJoinColumn();

	/**
	 * Add a specified map key join column to the relationship.
	 */
	SpecifiedJoinColumn addSpecifiedMapKeyJoinColumn(int index);

	/**
	 * Remove the specified map key join column.
	 */
	void removeSpecifiedMapKeyJoinColumn(int index);

	/**
	 * Remove the specified map key join column.
	 */
	void removeSpecifiedMapKeyJoinColumn(SpecifiedJoinColumn joinColumn);

	/**
	 * Move the specified map key join column from the source index to the target index.
	 */
	void moveSpecifiedMapKeyJoinColumn(int targetIndex, int sourceIndex);


	// ********** default map key join column **********

	/**
	 * Change notification identifier for "defaultMapKeyJoinColumn" property
	 */
	String DEFAULT_MAP_KEY_JOIN_COLUMN_PROPERTY = "defaultMapKeyJoinColumn"; //$NON-NLS-1$

	/**
	 * Return the default map key join column. If there are specified map key join
	 * columns, there is no default join column. There are also
	 * times that there may be no default map key join column even if there are no
	 * specified map key join columns.
	 */
	SpecifiedJoinColumn getDefaultMapKeyJoinColumn();

}

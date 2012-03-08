/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * Corresponds to the JPA annotations:<code><ul>
 * <li>javax.persistence.Table
 * <li>javax.persistence.JoinTable
 * <li>javax.persistence.SecondaryTable
 * <li>javax.persistence.CollectionTable
 * </ul></code>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 2.3
 * @since 2.2
 */
public interface BaseTableAnnotation
	extends Annotation
{
	/**
	 * Return whether the annotation exists in Java.
	 */
	boolean isSpecified();


	// ********** name **********

	/**
	 * Corresponds to the 'name' element of the *Table annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'name' element of the *Table annotation.
	 * Set to null to remove the element.
	 */
	void setName(String name);

	/**
	 * Return the {@link TextRange} for the 'name' element. If the element
	 * does not exist return the {@link TextRange} for the *Table annotation.
	 */
	TextRange getNameTextRange();

	/**
	 * Return whether the specified position touches the 'name' element.
	 * Return false if the element does not exist.
	 */
	boolean nameTouches(int pos);


	// ********** schema **********

	/**
	 * Corresponds to the 'schema' element of the *Table annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getSchema();
		String SCHEMA_PROPERTY = "schema"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'schema' element of the *Table annotation.
	 * Set to null to remove the element.
	 */
	void setSchema(String schema);

	/**
	 * Return the {@link TextRange} for the 'schema' element. If the element
	 * does not exist return the {@link TextRange} for the *Table annotation.
	 */
	TextRange getSchemaTextRange();

	/**
	 * Return whether the specified position touches the 'schema' element.
	 * Return false if the element does not exist.
	 */
	boolean schemaTouches(int pos);


	// ********** catalog **********

	/**
	 * Corresponds to the 'catalog' element of the *Table annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getCatalog();
		String CATALOG_PROPERTY = "catalog"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'catalog' element of the *Table annotation.
	 * Set to null to remove the element.
	 */
	void setCatalog(String catalog);

	/**
	 * Return the {@link TextRange} for the 'catalog' element. If the element
	 * does not exist return the {@link TextRange} for the *Table annotation.
	 */
	TextRange getCatalogTextRange();

	/**
	 * Return whether the specified position touches the 'catalog' element.
	 * Return false if the element does not exist.
	 */
	boolean catalogTouches(int pos);


	// ********** unique constraints **********

	/**
	 * Corresponds to the 'uniqueConstraints' element of the *Table annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterable<UniqueConstraintAnnotation> getUniqueConstraints();
		String UNIQUE_CONSTRAINTS_LIST = "uniqueConstraints"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'uniqueConstraints' element of the *Table annotation.
	 */
	int getUniqueConstraintsSize();

	/**
	 * Corresponds to the 'uniqueConstraints' element of the *Table annotation.
	 */
	UniqueConstraintAnnotation uniqueConstraintAt(int index);

	/**
	 * Corresponds to the 'uniqueConstraints' element of the *Table annotation.
	 */
	UniqueConstraintAnnotation addUniqueConstraint(int index);

	/**
	 * Corresponds to the 'uniqueConstraints' element of the *Table annotation.
	 */
	void moveUniqueConstraint(int targetIndex, int sourceIndex);

	/**
	 * Corresponds to the 'uniqueConstraints' element of the *Table annotation.
	 */
	void removeUniqueConstraint(int index);
}

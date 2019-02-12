/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JPA annotations:<code><ul>
 * <li>javax.persistence.Column
 * <li>javax.persistence.MapKeyColumn
 * <li>javax.persistence.JoinColumn
 * <li>javax.persistence.MapKeyJoinColumn
 * </ul></code>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface BaseColumnAnnotation
	extends NamedColumnAnnotation
{
	// ********** unique **********

	/**
	 * Corresponds to the 'unique' element of the *Column annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getUnique();
		String UNIQUE_PROPERTY = "unique"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'unique' element of the *Column annotation.
	 * Set to null to remove the element.
	 */
	void setUnique(Boolean unique);

	/**
	 * Return the {@link TextRange} for the 'unique' element. If the element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getUniqueTextRange();


	// ********** nullable **********

	/**
	 * Corresponds to the 'nullable' element of the *Column annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getNullable();
		String NULLABLE_PROPERTY = "nullable"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'nullable' element of the *Column annotation.
	 * Set to null to remove the element.
	 */
	void setNullable(Boolean nullable);

	/**
	 * Return the {@link TextRange} for the 'nullable' element. If the element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getNullableTextRange();


	// ********** insertable **********

	/**
	 * Corresponds to the 'insertable' element of the *Column annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getInsertable();
		String INSERTABLE_PROPERTY = "insertable"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'insertable' element of the *Column annotation.
	 * Set to null to remove the element.
	 */
	void setInsertable(Boolean insertable);

	/**
	 * Return the {@link TextRange} for the 'insertable' element. If the element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getInsertableTextRange();


	// ********** updatable **********

	/**
	 * Corresponds to the 'updatable' element of the *Column annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getUpdatable();
		String UPDATABLE_PROPERTY = "updatable"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'updatable' element of the *Column annotation.
	 * Set to null to remove the element.
	 */
	void setUpdatable(Boolean updatable);

	/**
	 * Return the {@link TextRange} for the 'updatable' element. If the element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getUpdatableTextRange();


	// ********** table **********

	/**
	 * Corresponds to the 'table' element of the *Column annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getTable();
		String TABLE_PROPERTY = "table"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'table' element of the *Column annotation.
	 * Set to null to remove the element.
	 */
	void setTable(String table);

	/**
	 * Return the {@link TextRange} for the 'table' element. If the element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getTableTextRange();

	/**
	 * Return whether the specified position touches the 'table' element.
	 * Return false if the element does not exist.
	 */
	boolean tableTouches(int pos);
}

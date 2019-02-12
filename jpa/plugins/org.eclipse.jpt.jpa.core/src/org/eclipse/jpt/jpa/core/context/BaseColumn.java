/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * <ul>
 * <li>column
 * <li>join column
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface BaseColumn
	extends TableColumn
{
	// ********** unique **********

	/**
	 * Return the specified unique setting if present, otherwise return the
	 * default unique setting.
	 */
	boolean isUnique();
	Boolean getSpecifiedUnique();
		String SPECIFIED_UNIQUE_PROPERTY = "specifiedUnique"; //$NON-NLS-1$
	boolean isDefaultUnique();
		String DEFAULT_UNIQUE_PROPERTY = "defaultUnique"; //$NON-NLS-1$
	boolean DEFAULT_UNIQUE = false;


	// ********** nullable **********

	/**
	 * Return the specified nullable setting if present, otherwise return the
	 * default nullable setting.
	 */
	boolean isNullable();
	Boolean getSpecifiedNullable();
		String SPECIFIED_NULLABLE_PROPERTY = "specifiedNullable"; //$NON-NLS-1$
	boolean isDefaultNullable();
		String DEFAULT_NULLABLE_PROPERTY = "defaultNullable"; //$NON-NLS-1$
	boolean DEFAULT_NULLABLE = true;


	// ********** insertable **********

	/**
	 * Return the specified insertable setting if present, otherwise return the
	 * default insertable setting.
	 */
	boolean isInsertable();
	Boolean getSpecifiedInsertable();
		String SPECIFIED_INSERTABLE_PROPERTY = "specifiedInsertable"; //$NON-NLS-1$
	boolean isDefaultInsertable();
		String DEFAULT_INSERTABLE_PROPERTY = "defaultInsertable"; //$NON-NLS-1$
	boolean DEFAULT_INSERTABLE = true;


	// ********** updatable **********

	/**
	 * Return the specified updatable setting if present, otherwise return the
	 * default updatable setting.
	 */
	boolean isUpdatable();
	Boolean getSpecifiedUpdatable();
		String SPECIFIED_UPDATABLE_PROPERTY = "specifiedUpdatable"; //$NON-NLS-1$
	boolean isDefaultUpdatable();
		String DEFAULT_UPDATABLE_PROPERTY = "defaultUpdatable"; //$NON-NLS-1$
	boolean DEFAULT_UPDATABLE = true;
}

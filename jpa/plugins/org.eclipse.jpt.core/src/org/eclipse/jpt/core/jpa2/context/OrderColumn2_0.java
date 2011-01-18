/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context;

import org.eclipse.jpt.core.context.NamedColumn;

/**
 * order column
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface OrderColumn2_0
	extends NamedColumn
{
	// ********** nullable **********

	/**
	 * Return the specified nullable setting if present, otherwise return the
	 * default nullable setting.
	 */
	boolean isNullable();
	Boolean getSpecifiedNullable();
	void setSpecifiedNullable(Boolean newSpecifiedNullable);
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
	void setSpecifiedInsertable(Boolean newSpecifiedInsertable);
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
	void setSpecifiedUpdatable(Boolean newSpecifiedUpdatable);
		String SPECIFIED_UPDATABLE_PROPERTY = "specifiedUpdatable"; //$NON-NLS-1$
	boolean isDefaultUpdatable();
		String DEFAULT_UPDATABLE_PROPERTY = "defaultUpdatable"; //$NON-NLS-1$
		boolean DEFAULT_UPDATABLE = true;
}

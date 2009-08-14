/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface BaseColumn extends NamedColumn
{

	String getTable();

	String getDefaultTable();
		String DEFAULT_TABLE_PROPERTY = "defaultTable"; //$NON-NLS-1$

	String getSpecifiedTable();
	void setSpecifiedTable(String value);
		String SPECIFIED_TABLE_PROPERTY = "specifiedTable"; //$NON-NLS-1$

	
	boolean isUnique();
	
	boolean isDefaultUnique();
		String DEFAULT_UNIQUE_PROPERTY = "defaultUnique"; //$NON-NLS-1$
		boolean DEFAULT_UNIQUE = false;
	Boolean getSpecifiedUnique();
	void setSpecifiedUnique(Boolean newSpecifiedUnique);
		String SPECIFIED_UNIQUE_PROPERTY = "specifiedUnique"; //$NON-NLS-1$
	

	boolean isNullable();
		
	boolean isDefaultNullable();
		String DEFAULT_NULLABLE_PROPERTY = "defaultNullable"; //$NON-NLS-1$
		boolean DEFAULT_NULLABLE = true;
	Boolean getSpecifiedNullable();
	void setSpecifiedNullable(Boolean newSpecifiedNullable);
		String SPECIFIED_NULLABLE_PROPERTY = "specifiedNullable"; //$NON-NLS-1$


	boolean isInsertable();
	
	boolean isDefaultInsertable();
		String DEFAULT_INSERTABLE_PROPERTY = "defaultInsertable"; //$NON-NLS-1$
		boolean DEFAULT_INSERTABLE = true;
	Boolean getSpecifiedInsertable();
	void setSpecifiedInsertable(Boolean newSpecifiedInsertable);
		String SPECIFIED_INSERTABLE_PROPERTY = "specifiedInsertable"; //$NON-NLS-1$
	
	
	boolean isUpdatable();
	
	boolean isDefaultUpdatable();
		String DEFAULT_UPDATABLE_PROPERTY = "defaultUpdatable"; //$NON-NLS-1$
		boolean DEFAULT_UPDATABLE = true;
	Boolean getSpecifiedUpdatable();
	void setSpecifiedUpdatable(Boolean newSpecifiedUpdatable);
		String SPECIFIED_UPDATABLE_PROPERTY = "specifiedUpdatable"; //$NON-NLS-1$

	//TODO not sure we really need/want this to be public.  This
	//is used by ColumnComposite to get a list of possible associated tables, but
	//right now that list isn't going to update in the UI except when we repopulate
	Owner getOwner();
	
	/**
	 * interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides)
	 */
	interface Owner extends NamedColumn.Owner
	{
		/**
		 * Return the table to which the column belongs by default.
		 */
		String getDefaultTableName();
	}
}

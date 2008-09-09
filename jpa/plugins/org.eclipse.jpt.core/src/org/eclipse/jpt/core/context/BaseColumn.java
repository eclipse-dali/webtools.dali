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

	
	Boolean getUnique();
	
	Boolean getDefaultUnique();
		String DEFAULT_UNIQUE_PROPERTY = "defaultUnique"; //$NON-NLS-1$
		Boolean DEFAULT_UNIQUE = Boolean.FALSE;
	Boolean getSpecifiedUnique();
	void setSpecifiedUnique(Boolean newSpecifiedUnique);
		String SPECIFIED_UNIQUE_PROPERTY = "specifiedUnique"; //$NON-NLS-1$
	

	Boolean getNullable();
		
	Boolean getDefaultNullable();
		String DEFAULT_NULLABLE_PROPERTY = "defaultNullable"; //$NON-NLS-1$
		Boolean DEFAULT_NULLABLE = Boolean.TRUE;
	Boolean getSpecifiedNullable();
	void setSpecifiedNullable(Boolean newSpecifiedNullable);
		String SPECIFIED_NULLABLE_PROPERTY = "specifiedNullable"; //$NON-NLS-1$


	Boolean getInsertable();
	
	Boolean getDefaultInsertable();
		String DEFAULT_INSERTABLE_PROPERTY = "defaulInsertable"; //$NON-NLS-1$
		Boolean DEFAULT_INSERTABLE = Boolean.TRUE;
	Boolean getSpecifiedInsertable();
	void setSpecifiedInsertable(Boolean newSpecifiedInsertable);
		String SPECIFIED_INSERTABLE_PROPERTY = "specifiedInsertable"; //$NON-NLS-1$
	
	
	Boolean getUpdatable();
	
	Boolean getDefaultUpdatable();
		String DEFAULT_UPDATABLE_PROPERTY = "defaulUpdatable"; //$NON-NLS-1$
		Boolean DEFAULT_UPDATABLE = Boolean.TRUE;
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
		 * Return the name of the persistent attribute that contains the column.
		 */
		String getDefaultTableName();
	}
}

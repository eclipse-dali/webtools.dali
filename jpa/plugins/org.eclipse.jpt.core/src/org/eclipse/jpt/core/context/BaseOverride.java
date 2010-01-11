/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;
import org.eclipse.jpt.db.Table;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface BaseOverride extends JpaContextNode
{

	String getName();
	void setName(String value);
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	/**
	 * Return true if override exists as specified on the owning object, or false
	 * if the override is "gotten for free" as a result of defaults calculation
	 */
	boolean isVirtual();

	/**
	 * If false, add the given override as a specified override and remove
	 * it from the list of virtual overrides.  If true, then remove it
	 * from the specified overrides and add it to the virtual overrides
	 * as applicable.  Return the new override (whether virtual or specified)
	 */
	BaseOverride setVirtual(boolean virtual);
	
	interface Owner
	{
		/**
		 * Return the type mapping that this override is contained in
		 * @return
		 */
		TypeMapping getTypeMapping();
		
		/**
		 * Return an Iterator of all attribute names that can be overridden
		 */
		Iterator<String> allOverridableAttributeNames();

		/**
		 * Return whether the given override is virtual. Virtual means that
		 * it is not specified, but defaulted in from the mapped superclass or
		 * embeddable.
		 */
		boolean isVirtual(BaseOverride override);
		
		/**
		 * If false, add the given override as a specified override and remove
		 * it from the list of virtual overrides.  If true, then remove it
		 * from the specified overrides and add it to the virtual overrides
		 * as applicable.  Return the new override (whether virtual or specified)
		 */
		BaseOverride setVirtual(boolean virtual, BaseOverride override);	

		/**
		 * return whether the given table cannot be explicitly specified
		 * in the column or join column's 'table' element
		 */
		boolean tableNameIsInvalid(String tableName);

		/**
		 * Return a list of table names that are valid for the overrides column or join columns
		 */
		Iterator<String> candidateTableNames();

		/**
		 * Return the database table for the specified table name
		 */
		Table getDbTable(String tableName);

		/**
		 * Return the name of the table which the column belongs to by default
		 */
		String getDefaultTableName();
	}
}
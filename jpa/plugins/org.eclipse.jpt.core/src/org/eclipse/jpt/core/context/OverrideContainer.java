/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.OverrideTextRangeResolver;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.3
 */
public interface OverrideContainer
	extends JpaContextNode
{
	
	interface Owner
	{
		/**
		 * Return the mapping of the persistent type where the container is defined.
		 * (For example, on an entity, this would be the entity.  On an embedded, this would
		 *  be the type mapping where the embedded is defined.)
		 */
		TypeMapping getTypeMapping();
		
		/**
		 * Return the type mapping that contains the attributes/associations to be overridden.
		 * (Though the type mapping may not *directly* own them.  i.e. they may be on a supertype
		 *  mapping.)
		 * (For example, on an entity, this would be the supertype mapping of that entity.  On
		 *  an embedded, this would be the target type mapping of the embedded.)
		 */
		TypeMapping getOverridableTypeMapping();
		
		/**
		 * Return an iterator of all the names of the attributes/associations to be overridden.
		 * This is usually just all of the overridable names of the overridable type mapping.
		 * @see #getOverridableTypeMapping()
		 */
		Iterator<String> allOverridableNames();
		
		/**
		 * Return the name of the table which the column belongs to by default
		 */
		String getDefaultTableName();
		
		/**
		 * return whether the given table cannot be explicitly specified
		 * in the column's 'table' element
		 */
		boolean tableNameIsInvalid(String tableName);
		
		/**
		 * Return the database table for the specified table name
		 */
		org.eclipse.jpt.db.Table getDbTable(String tableName);
		
		/**
		 * Return a list of table names that are valid for the overrides column, or join columns
		 */
		Iterator<String> candidateTableNames();		
		
		JptValidator buildValidator(BaseOverride override, BaseOverride.Owner owner, OverrideTextRangeResolver textRangeResolver);

		JptValidator buildColumnValidator(BaseOverride override, BaseColumn column, BaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver);
	}

}

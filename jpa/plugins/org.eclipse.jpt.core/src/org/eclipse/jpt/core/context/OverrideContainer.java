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
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface OverrideContainer
	extends JpaContextNode
{
	
	interface Owner
	{
		/**
		 * Return the type mapping of the owning persistent type.
		 */
		TypeMapping getTypeMapping();

		/**
		 * Return the overridable persistent type, not the owning persistent type.
		 * This will be the persistent type of the mapped superclass or embeddable.
		 */
		TypeMapping getOverridableTypeMapping();

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
		
		/**
		 * Return a validation message for the column's table not being valid in the context.
		 * Use the given text range in the message
		 */
		IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange);
		
		/**
		 * Return a validation message for the column's name not resolving on the 
		 * table either specified or default. Use the given text range in the message
		 */
		IMessage buildColumnUnresolvedNameMessage(BaseOverride override, NamedColumn column, TextRange textRange);
	}

}

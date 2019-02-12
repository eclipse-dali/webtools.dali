/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.annotate.util;

import java.util.Iterator;

import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;

public class DefaultTableUtil 
{
	public static Table findTable(Schema schema, String javaClassName, PersistenceUnit persistenceUnit)
	{
		if (schema == null || javaClassName == null || persistenceUnit == null)
		{
			return null;
		}
		Table table = null;
		Entity entity = persistenceUnit.getEntity(javaClassName);
		if (entity != null)
		{
			String tableName = entity.getTable().getName();
			table = schema.getTableNamed(tableName);
		}
			
		if (table == null)
		{
			table = findTable(schema, javaClassName);
		}
		return table;		
	}
	
	public static Table findTable(Schema schema, String javaClassName)
	{
		if (schema == null || javaClassName == null)
		{
			return null;
		}
		String simpleClassName = AnnotateMappingUtil.getClassName(javaClassName);
		Table table = findTableCaseInsensitive(schema, simpleClassName);
		if (table == null)
		{
			simpleClassName = AnnotateMappingUtil.pluralise(simpleClassName);
			table = findTableCaseInsensitive(schema, simpleClassName);
		}
		if (table == null)
		{
			simpleClassName = AnnotateMappingUtil.singularise(simpleClassName);
			table = findTableCaseInsensitive(schema, simpleClassName);
		}
		if (table == null)
		{
			Iterator<Table> tableIt = schema.getTables().iterator();
			while (tableIt.hasNext())
			{
				Table table2 = tableIt.next();
				String tableName2 = AnnotateMappingUtil.dbNameToJavaName(table2.getName());
				if (tableName2.equalsIgnoreCase(simpleClassName))
				{
					table = table2;
					break;
				}
			}
		}
		return table;
	}
	
	private static Table findTableCaseInsensitive(Schema schema, String tableName)
	{
		String tableName2 = tableName;
		Table table = null;
		if (schema != null)
		{
			table = schema.getTableNamed(tableName2);
			if (table == null)
			{
				tableName2 = tableName2.toUpperCase();
				table = schema.getTableNamed(tableName2);
			}			
			if (table == null)
			{
				tableName2 = tableName2.toLowerCase();
				table = schema.getTableNamed(tableName2);
			}
		}
		return table;
	}
	
}

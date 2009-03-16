/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal2.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.db.ForeignKey.ColumnPair;
import org.eclipse.jpt.gen.internal2.ForeignKeyInfo;

/**
 * Collection of utility methods to access DTP and other jpt.db APIs 
 *
 */
public class DTPUtil {

	/**
	 * Return list of the pk names
	 * @param dbTable
	 * @return
	 */
	public static List<String> getPrimaryKeyColumnNames(Table dbTable ) {
		Iterator<Column> pkColumns = dbTable.primaryKeyColumns();
		ArrayList<String> ret = new ArrayList<String>();
		while( pkColumns.hasNext() ){
			ret.add( pkColumns.next().getName() );
		}
		return ret;
	}

	/**
	 * 
	 * @param dbTable
	 * @return
	 */
	public static List<Column> getPrimaryKeyColumns(Table dbTable ) {
		Iterator<Column> pkColumns = dbTable.primaryKeyColumns();
		ArrayList<Column> ret = new ArrayList<Column>();
		while( pkColumns.hasNext() ){
			ret.add( pkColumns.next() );
		}
		return ret;
	}
	
	public static boolean isAutoIncrement(Column c){
		//@ TODO
		//Blocked by DTP bug
		//https://bugs.eclipse.org/bugs/show_bug.cgi?id=250023
		//The Dali bug is
		//https://bugs.eclipse.org/bugs/show_bug.cgi?id=249658
		//
		return false;
	}

	/**
	 * Return list of fk
	 * @param dbTable
	 * @return
	 */
	public static List<ForeignKeyInfo> getForeignKeys(Table dbTable) {
		List<ForeignKeyInfo> ret = new ArrayList<ForeignKeyInfo>();
		if(dbTable!=null){
			Iterator<ForeignKey> fks = dbTable.foreignKeys();
			while( fks.hasNext() ){
				ForeignKey fk  = fks.next();
				ColumnPair columnPair = fk.getColumnPair();

				String tableName = dbTable.getName();
				String referencedTableName = "";
				Table referencedTable = fk.getReferencedTable();
				referencedTableName = referencedTable.getName();
				ForeignKeyInfo fkInfo = new ForeignKeyInfo(fk, tableName, referencedTableName );
				String baseColName = columnPair.getBaseColumn().getName();
				String referencedColName = columnPair.getReferencedColumn().getName();
				fkInfo.addColumnMapping( baseColName,  referencedColName );
				ret.add( fkInfo );
			}
		}
		return ret;
	}

	public static String getJavaType(Schema schema, Column dbColumn) {
		if( isPrimaryKey(dbColumn) )
			return dbColumn.getPrimaryKeyJavaTypeDeclaration();
		return dbColumn.getJavaTypeDeclaration();
	}

	public static boolean isPrimaryKey(Column dbColumn){
		Table dbTable = dbColumn.getTable();
		Iterator<Column> pkColumns = dbTable.primaryKeyColumns();		
		while( pkColumns.hasNext() ){
			if( pkColumns.next().equals( dbColumn )){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isDefaultSchema(Table dbTable){
		String schemaName = dbTable.getSchema().getName();
		Schema defaultSchema = dbTable.getSchema().getConnectionProfile().getDatabase().getDefaultSchema();
		return defaultSchema.getName() == schemaName;
	}	
}

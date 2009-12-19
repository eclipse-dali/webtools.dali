/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.db.ForeignKey.ColumnPair;

/**
 * Collection of utility methods to access DTP and other jpt.db APIs 
 *
 */
public class DTPUtil {

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
			for (ForeignKey fk : dbTable.getForeignKeys()) {
				Iterator<ColumnPair> columnPaires = fk.getColumnPairs().iterator();
				ForeignKeyInfo fkInfo = null;
				while( columnPaires.hasNext() ){
					ColumnPair columnPair = columnPaires.next();
					if( fkInfo == null){
						String tableName = dbTable.getName();
						String referencedTableName = "";
						Table referencedTable = fk.getReferencedTable();
						referencedTableName = referencedTable.getName();
						fkInfo = new ForeignKeyInfo(fk, tableName, referencedTableName );
					}
					String baseColName = columnPair.getBaseColumn().getName();
					String referencedColName = columnPair.getReferencedColumn().getName();
					fkInfo.addColumnMapping( baseColName,  referencedColName );
				} 
				if( fkInfo !=null )
					ret.add( fkInfo );
			}
		}
		return ret;
	}

	public static String getJavaType(Column dbColumn) {
		return dbColumn.isPartOfPrimaryKey() ?
				dbColumn.getPrimaryKeyJavaTypeDeclaration() :
				dbColumn.getJavaTypeDeclaration();
	}

	public static boolean isDefaultSchema(Table dbTable){
		String schemaName = dbTable.getSchema().getName();
		Schema defaultSchema = dbTable.getSchema().getConnectionProfile().getDatabase().getDefaultSchema();
		return defaultSchema.getName() == schemaName;
	}	
}

/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.text.Collator;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;
import org.eclipse.datatools.modelbase.sql.datatypes.DataType;
import org.eclipse.datatools.modelbase.sql.datatypes.PredefinedDataType;
import org.eclipse.jpt.utility.internal.JavaType;
import org.eclipse.jpt.utility.internal.NameTools;

/**
 *  Wrap a DTP Column
 */
public final class Column extends DTPWrapper {
	private final Table table;
	private final org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn;
	private ICatalogObjectListener columnListener;

	// TODO Object is the default?
	private static final JavaType DEFAULT_JAVA_TYPE = new JavaType(java.lang.Object.class);

	// ********** constructors **********

	Column( Table table, org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		super();
		this.table = table;
		this.dtpColumn = dtpColumn;
		this.initialize();
	}

	// ********** behavior **********

	private void initialize() {
		if( this.connectionIsOnline()) {
			this.columnListener = this.buildColumnListener();
			this.addCatalogObjectListener(( ICatalogObject) this.dtpColumn, this.columnListener);
		}
	}
	
	protected boolean connectionIsOnline() {
		return this.table.connectionIsOnline();
	}
	
	private ICatalogObjectListener buildColumnListener() {
       return new ICatalogObjectListener() {
    	    public void notifyChanged( final ICatalogObject column, final int eventType) { 
//				TODO
//    			if( column == Column.this.dtpColumn) {	    	    	
//    				Column.this.table.columnChanged( Column.this, eventType);
//    			}
    	    }
        };
    }
	
	protected void dispose() {
		
		this.removeCatalogObjectListener(( ICatalogObject) this.dtpColumn, this.columnListener);
	}
	
	// ********** queries **********

	public String getName() {
		return this.dtpColumn.getName();
	}

	boolean isCaseSensitive() {
		return this.table.isCaseSensitive();
	}

	public String dataTypeName() {
		DataType dataType = this.dtpColumn.getDataType();
		return (dataType == null) ? null : dataType.getName();
	}

	public String javaFieldName() {
		String jName = this.getName();
		if ( ! this.isCaseSensitive()) {
			jName = jName.toLowerCase();
		}
		return NameTools.convertToJavaIdentifier(jName);
	}

	public boolean matchesJavaFieldName(String javaFieldName) {
		return this.isCaseSensitive() ?
			this.getName().equals(javaFieldName)
		:
			this.getName().equalsIgnoreCase(javaFieldName);
	}

	/**
	 * Return a Java type declaration that is reasonably
	 * similar to the column's data type.
	 */
	public String javaTypeDeclaration() {
		return this.javaType().declaration();
	}

	/**
	 * Return a Java type that is reasonably
	 * similar to the column's data type.
	 */
	public JavaType javaType() {
		DataType dataType = this.dtpColumn.getDataType();
		return (dataType instanceof PredefinedDataType) ?
			DTPTools.javaTypeFor(((PredefinedDataType) dataType).getPrimitiveType())
		:
			DEFAULT_JAVA_TYPE;
	}

	boolean wraps( org.eclipse.datatools.modelbase.sql.tables.Column column) {
		return this.dtpColumn == column;
	}

	// ********** Comparable implementation **********
	
	public int compareTo( Object o) {
		return Collator.getInstance().compare( this.getName(), (( Column)o).getName());
	}
}


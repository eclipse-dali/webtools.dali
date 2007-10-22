/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

/**
 * Corresponds to the javax.persistence.TableGenerator annotation
 */
public interface TableGenerator extends Generator
{
	
	/**
	 * Corresponds to the table element of the TableGenerator annotation.
	 * Returns null if the table element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getTable();
	
	/**
	 * Corresponds to the table element of the TableGenerator annotation.
	 * Set to null to remove the table element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setTable(String table);
	
	/**
	 * Corresponds to the catalog element of the TableGenerator annotation.
	 * Returns null if the catalog element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getCatalog();
	
	/**
	 * Corresponds to the catalog element of the TableGenerator annotation.
	 * Set to null to remove the catalog element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setCatalog(String catalog);
	
	/**
	 * Corresponds to the schema element of the TableGenerator annotation.
	 * Returns null if the schema element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getSchema();
	
	/**
	 * Corresponds to the schema element of the TableGenerator annotation.
	 * Set to null to remove the schema element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setSchema(String schema);
	
	/**
	 * Corresponds to the pkColumnName element of the TableGenerator annotation.
	 * Returns null if the pkColumnName element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getPkColumnName();
	
	/**
	 * Corresponds to the pkColumnName element of the TableGenerator annotation.
	 * Set to null to remove the pkColumnName element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setPkColumnName(String pkColumnName);
	
	/**
	 * Corresponds to the valueColumnName element of the TableGenerator annotation.
	 * Returns null if the valueColumnName element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getValueColumnName();
	
	/**
	 * Corresponds to the valueColumnName element of the TableGenerator annotation.
	 * Set to null to remove the valueColumnName element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setValueColumnName(String valueColumnName);
	
	/**
	 * Corresponds to the pkColumnValue element of the TableGenerator annotation.
	 * Returns null if the pkColumnValue element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getPkColumnValue();
	
	/**
	 * Corresponds to the pkColumnValue element of the TableGenerator annotation.
	 * Set to null to remove the pkColumnValue element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setPkColumnValue(String pkColumnValue);
	
	ListIterator<UniqueConstraint> uniqueConstraints();
	
	UniqueConstraint uniqueConstraintAt(int index);
	
	int indexOfUniqueConstraint(UniqueConstraint uniqueConstraint);
	
	int uniqueConstraintsSize();

	UniqueConstraint addUniqueConstraint(int index);
	
	void removeUniqueConstraint(int index);
	
	void moveUniqueConstraint(int oldIndex, int newIndex);

	/**
	 * Return the ITextRange for the table element.  If the table element 
	 * does not exist return the ITextRange for the TableGenerator annotation.
	 */
	ITextRange tableTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the catalog element.  If the catalog element 
	 * does not exist return the ITextRange for the TableGenerator annotation.
	 */
	ITextRange catalogTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the schema element.  If the schema element 
	 * does not exist return the ITextRange for the TableGenerator annotation.
	 */
	ITextRange schemaTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the pkColumnName element.  If the pkColumnName element 
	 * does not exist return the ITextRange for the TableGenerator annotation.
	 */
	ITextRange pkColumnNameTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the valueColumnName element.  If the valueColumnName element 
	 * does not exist return the ITextRange for the TableGenerator annotation.
	 */
	ITextRange valueColumnNameTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the pkColumnValue element.  If the pkColumnValue element 
	 * does not exist return the ITextRange for the TableGenerator annotation.
	 */
	ITextRange pkColumnValueTextRange(CompilationUnit astRoot);

	
	class UniqueConstraintOwner implements UniqueConstraint.Owner
	{
		private final TableGenerator tableGenerator;

		public UniqueConstraintOwner(TableGenerator tableGenerator) {
			super();
			this.tableGenerator = tableGenerator;
		}

		public Iterator<String> candidateUniqueConstraintColumnNames() {
			return EmptyIterator.instance();
			//TODO this.table.dbTable().columnNames();
		}
		
		public JavaResource javaResource() {
			return this.tableGenerator;
		}
	}
}

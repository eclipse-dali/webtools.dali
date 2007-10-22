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

public interface Table extends JavaResource
{
	String getName();
	
	void setName(String name);
	
	String getCatalog();
	
	void setCatalog(String catalog);

	String getSchema();
	
	void setSchema(String schema);
	
	ListIterator<UniqueConstraint> uniqueConstraints();
	
	UniqueConstraint uniqueConstraintAt(int index);
	
	int indexOfUniqueConstraint(UniqueConstraint uniqueConstraint);
	
	int uniqueConstraintsSize();

	UniqueConstraint addUniqueConstraint(int index);
	
	void removeUniqueConstraint(int index);
	
	void moveUniqueConstraint(int oldIndex, int newIndex);

	/**
	 * Return the ITextRange for the name element.  If the name element 
	 * does not exist return the ITextRange for the *Table annotation.
	 */
	ITextRange nameTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the catalog element.  If the catalog element 
	 * does not exist return the ITextRange for the *Table annotation.
	 */
	ITextRange catalogTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the schema element.  If the schema element 
	 * does not exist return the ITextRange for the *Table annotation.
	 */
	ITextRange schemaTextRange(CompilationUnit astRoot);

	class UniqueConstraintOwner implements UniqueConstraint.Owner
	{
		private final Table table;

		public UniqueConstraintOwner(Table table) {
			super();
			this.table = table;
		}

		public Iterator<String> candidateUniqueConstraintColumnNames() {
			return EmptyIterator.instance();
			//TODO this.table.dbTable().columnNames();
		}
		
		public JavaResource javaResource() {
			return this.table;
		}
	}
}

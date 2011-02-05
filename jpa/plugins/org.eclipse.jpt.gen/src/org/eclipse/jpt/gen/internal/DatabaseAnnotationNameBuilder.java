/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal;

import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ForeignKey;
import org.eclipse.jpt.jpa.db.Table;


/**
 * Provide a pluggable way to determine whether and how the entity generator
 * prints the names of various database objects.
 */
public interface DatabaseAnnotationNameBuilder {

	/**
	 * Given the name of an entity and the table to which it is mapped,
	 * build and return a string to be used as the value for the entity's
	 * Table annotation's 'name' element. Return null if the entity
	 * maps to the table by default.
	 */
	String buildTableAnnotationName(String entityName, Table table);

	/**
	 * Given the name of an attribute (field or property) and the column
	 * to which it is mapped,
	 * build and return a string to be used as the value for the attribute's
	 * Column annotation's 'name' element. Return null if the attribute
	 * maps to the column by default.
	 */
	String buildColumnAnnotationName(String attributeName, Column column);

	/**
	 * Given the name of an attribute (field or property) and the
	 * many-to-one or many-to-many foreign key to which it is mapped,
	 * build and return a string to be used as the value for the attribute's
	 * JoinColumn annotation's 'name' element. Return null if the attribute
	 * maps to the join column by default.
	 * The specified foreign key consists of a single column pair whose
	 * referenced column is the single-column primary key of the foreign
	 * key's referenced table.
	 */
	String buildJoinColumnAnnotationName(String attributeName, ForeignKey foreignKey);

	/**
	 * Build and return a string to be used as the value for a JoinColumn
	 * annotation's 'name' or 'referencedColumnName' element.
	 * This is called for many-to-one and many-to-many mappings when
	 * the default join column name and/or referenced column name are/is
	 * not applicable.
	 * @see buildJoinColumnAnnotationName(String, ForeignKey)
	 */
	String buildJoinColumnAnnotationName(Column column);

	/**
	 * Build and return a string to be used as the value for a JoinTable
	 * annotation's 'name' element.
	 * This is called for many-to-many mappings when the default
	 * join table name is not applicable.
	 */
	String buildJoinTableAnnotationName(Table table);


	/**
	 * The default implementation simple returns the database object's name,
	 * unaltered.
	 */
	final class Default implements DatabaseAnnotationNameBuilder {
		public static final DatabaseAnnotationNameBuilder INSTANCE = new Default();
		public static DatabaseAnnotationNameBuilder instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		public String buildTableAnnotationName(String entityName, Table table) {
			return table.getName();
		}
		public String buildColumnAnnotationName(String attributeName, Column column) {
			return column.getName();
		}
		public String buildJoinColumnAnnotationName(String attributeName, ForeignKey foreignKey) {
			return foreignKey.getColumnPair().getBaseColumn().getName();
		}
		public String buildJoinColumnAnnotationName(Column column) {
			return column.getName();
		}
		public String buildJoinTableAnnotationName(Table table) {
			return table.getName();
		}
		@Override
		public String toString() {
			return "DatabaseAnnotationNameBuilder.Default";  //$NON-NLS-1$
		}
	}

}

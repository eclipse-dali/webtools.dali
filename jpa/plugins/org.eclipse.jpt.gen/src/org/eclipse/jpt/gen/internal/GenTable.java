/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * associate a table with the various relations that will be used when
 * generating the entity
 */
class GenTable {
	private final GenScope scope;
	private final Table table;
	private final EntityGenerator.Config entityConfig;
	private final String entityName;
	private ManyToManyRelation joinTableRelation;
	private Collection<ManyToManyRelation> ownedManyToManyRelations = new ArrayList<ManyToManyRelation>();
	private Collection<ManyToManyRelation> nonOwnedManyToManyRelations = new ArrayList<ManyToManyRelation>();
	private Collection<ManyToOneRelation> manyToOneRelations = new ArrayList<ManyToOneRelation>();
	private Collection<OneToManyRelation> oneToManyRelations = new ArrayList<OneToManyRelation>();
	private Set<Column> foreignKeyColumns = new HashSet<Column>();

	// key=column/relation; value=name
	private final Map<Object, String> fieldNames = new HashMap<Object, String>();

	private static final Object EMBEDDED_ID_VIRTUAL_COLUMN = new Object();


	// ********** construction/initialization **********

	GenTable(GenScope scope, Table table, EntityGenerator.Config entityConfig, Collection<String> entityNames) {
		super();
		this.scope = scope;
		this.table = table;
		this.entityConfig = entityConfig;
		this.entityName = this.buildEntityName(entityNames);
	}

	private String buildEntityName(Collection<String> entityNames) {
		String name = this.table.shortJavaClassName();
		String overrideEntityName = this.entityConfig.getOverrideEntityName(this.table);
		if (overrideEntityName == null) {
			if (this.entityConfig.convertToCamelCase()) {
				name = StringTools.convertUnderscoresToCamelCase(name);
			}
		} else {
			name = overrideEntityName;
		}
		name = NameTools.uniqueNameForIgnoreCase(name, entityNames);
		entityNames.add(name);
		return name;
	}


	// ********** package API **********

	/**
	 * determine whether the table is a "join" table within the table's scope
	 */
	void configureManyToManyRelations() {
		if (this.table.foreignKeysSize() != 2) {
			// the table must have exactly 2 foreign keys
			return;
		}
		for (Iterator<Column> stream = this.table.columns(); stream.hasNext(); ) {
			if ( ! this.table.foreignKeyBaseColumnsContains(stream.next())) {
				// all the table's columns must belong to one (or both) of the 2 foreign keys
				return;
			}
		}
		Iterator<ForeignKey> fKeys = this.table.foreignKeys();
		ForeignKey owningFK = fKeys.next();
		ForeignKey nonOwningFK = fKeys.next();
		GenTable owningTable = this.scope.genTable(owningFK.referencedTable());
		GenTable nonOwningTable = this.scope.genTable(nonOwningFK.referencedTable());
		if ((owningTable == null) || (nonOwningTable == null)) {
			// both tables must be in the scope
			return;
		}
		this.joinTableRelation = new ManyToManyRelation(this, owningFK, owningTable, nonOwningFK, nonOwningTable);
	}

	void addReferencedTablesTo(Set<GenTable> referencedTables) {
		for (Iterator<ForeignKey> stream = this.table.foreignKeys(); stream.hasNext(); ) {
			ForeignKey fk = stream.next();
			GenTable genTable = this.scope.genTable(fk.referencedTable());
			if (genTable != null) {
				referencedTables.add(genTable);
			}
		}
	}

	void clearJoinTableRelation() {
		this.joinTableRelation.clear();
		this.joinTableRelation = null;
	}

	/**
	 * find "in-scope" foreign keys
	 */
	void configureManyToOneRelations() {
		for (Iterator<ForeignKey> stream = this.table.foreignKeys(); stream.hasNext(); ) {
			ForeignKey fk = stream.next();
			GenTable referencedtable = this.scope.genTable(fk.referencedTable());
			if (referencedtable == null) {
				continue;  // skip to next FK
			}
			this.manyToOneRelations.add(new ManyToOneRelation(this, fk, referencedtable));
		}
	}

	/**
	 * now that all the relations are in place, we can configure the Java
	 * field names
	 */
	void configureFieldNames() {
		Set<Column> columns = CollectionTools.set(this.table.columns());
		if ((this.table.primaryKeyColumnsSize() > 1) && this.entityConfig.generateEmbeddedIdForCompoundPK()) {
			// if we are going to generate an EmbeddedId field, add it to
			// 'fieldNames' so we don't collide with it later, when generating
			// field names for the columns etc.
			this.configureFieldName(EMBEDDED_ID_VIRTUAL_COLUMN, "pk");
		}
		this.configureManyToOneFieldNames(columns);
		this.configureBasicFieldNames(columns);
		this.configureOneToManyFieldNames();
		this.configureOwnedManyToManyFieldNames();
		this.configureNonOwnedManyToManyFieldNames();
	}

	/**
	 * return the columns that are part of the table's primary key
	 * but are also part of an "in-scope" foreign key
	 */
	Iterator<Column> readOnlyPrimaryKeyColumns() {
		return new FilteringIterator<Column, Column>(this.table.primaryKeyColumns()) {
			@Override
			protected boolean accept(Column column) {
				return GenTable.this.foreignKeyColumnsContains(column);
			}
		};
	}

	/**
	 * return the columns that are part of the table's primary key
	 * but are NOT part of any "in-scope" foreign key
	 */
	Iterator<Column> writablePrimaryKeyColumns() {
		return new FilteringIterator<Column, Column>(this.table.primaryKeyColumns()) {
			@Override
			protected boolean accept(Column column) {
				return ! GenTable.this.foreignKeyColumnsContains(column);
			}
		};
	}

	/**
	 * return the columns that NEITHER part of the table's primary key
	 * NOR part of any foreign key
	 */
	Iterator<Column> nonPrimaryKeyBasicColumns() {
		return new FilteringIterator<Column, Column>(this.table.columns()) {
			@Override
			protected boolean accept(Column column) {
				return ! (GenTable.this.primaryKeyColumnsContains(column)
						|| GenTable.this.foreignKeyColumnsContains(column));
			}
		};
	}

	Table getTable() {
		return this.table;
	}

	String getEntityName() {
		return this.entityName;
	}

	boolean isJoinTable() {
		return this.joinTableRelation != null;
	}

	void addOwnedManyToManyRelation(ManyToManyRelation relation) {
		this.ownedManyToManyRelations.add(relation);
	}

	void removeOwnedManyToManyRelation(ManyToManyRelation relation) {
		this.ownedManyToManyRelations.remove(relation);
	}

	void addNonOwnedManyToManyRelation(ManyToManyRelation relation) {
		this.nonOwnedManyToManyRelations.add(relation);
	}

	void removeNonOwnedManyToManyRelation(ManyToManyRelation relation) {
		this.nonOwnedManyToManyRelations.remove(relation);
	}

	void addOneToManyRelation(OneToManyRelation relation) {
		this.oneToManyRelations.add(relation);
	}

	String javaFieldName() {
		return this.table.javaFieldName();
	}

	Iterator<ManyToOneRelation> manyToOneRelations() {
		return this.manyToOneRelations.iterator();
	}

	Iterator<OneToManyRelation> oneToManyRelations() {
		return this.oneToManyRelations.iterator();
	}

	Iterator<ManyToManyRelation> ownedManyToManyRelations() {
		return this.ownedManyToManyRelations.iterator();
	}

	Iterator<ManyToManyRelation> nonOwnedManyToManyRelations() {
		return this.nonOwnedManyToManyRelations.iterator();
	}

	/**
	 * the key can be a column or relation
	 */
	private String fieldNameForInternal(Object o) {
		return this.fieldNames.get(o);
	}

	/**
	 * this will return null if we don't want an embedded id field
	 */
	String fieldNameForEmbeddedId() {
		return this.fieldNameForInternal(EMBEDDED_ID_VIRTUAL_COLUMN);
	}

	String fieldNameFor(Column column) {
		return this.fieldNameForInternal(column);
	}

	String fieldNameFor(ManyToOneRelation relation) {
		return this.fieldNameForInternal(relation);
	}

	String fieldNameFor(OneToManyRelation relation) {
		return this.fieldNameForInternal(relation);
	}

	String fieldNameFor(ManyToManyRelation relation) {
		return this.fieldNameForInternal(relation);
	}

	String name() {
		return this.table.name();
	}


	// ********** internal API **********

	/**
	 * while we are figuring out the names for the m:1 fields, remove from the
	 * specified set of columns the columns that are only part of the foreign keys
	 */
	private void configureManyToOneFieldNames(Set<Column> columns) {
		for (ManyToOneRelation relation : this.manyToOneRelations) {
			CollectionTools.removeAll(columns, relation.getForeignKey().nonPrimaryKeyBaseColumns());
			CollectionTools.addAll(this.foreignKeyColumns, relation.getForeignKey().baseColumns());
			relation.setMappedBy(this.configureFieldName(relation, relation.javaFieldName()));
		}
	}

	private String configureFieldName(Object o, String fieldName) {
		fieldName = this.camelCase(fieldName);
		fieldName = NameTools.uniqueNameFor(fieldName, this.fieldNames.values());
		this.fieldNames.put(o, fieldName);
		return fieldName;
	}

	private String camelCase(String name) {
		return this.entityConfig.convertToCamelCase() ?
			StringTools.convertUnderscoresToCamelCase(name, false)  // false = don't capitalize first letter
		:
			name;
	}

	/**
	 * build a unique field name for the specified "basic" columns,
	 * checking for name collisions
	 */
	private void configureBasicFieldNames(Set<Column> columns) {
		for (Column column : columns) {
			this.configureFieldName(column, column.javaFieldName());
		}
	}

	private void configureOneToManyFieldNames() {
		for (OneToManyRelation relation : this.oneToManyRelations) {
			this.configureFieldName(relation, relation.javaFieldName());
		}
	}

	private void configureOwnedManyToManyFieldNames() {
		for (ManyToManyRelation relation : this.ownedManyToManyRelations) {
			relation.setMappedBy(this.configureFieldName(relation, relation.javaFieldNameFor(this)));
		}
	}

	private void configureNonOwnedManyToManyFieldNames() {
		for (ManyToManyRelation relation : this.nonOwnedManyToManyRelations) {
			this.configureFieldName(relation, relation.javaFieldNameFor(this));
		}
	}

	boolean foreignKeyColumnsContains(Column column) {
		return this.foreignKeyColumns.contains(column);
	}

	boolean primaryKeyColumnsContains(Column column) {
		return this.table.primaryKeyColumnsContains(column);
	}

}

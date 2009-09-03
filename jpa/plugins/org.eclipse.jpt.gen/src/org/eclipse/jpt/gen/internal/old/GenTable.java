/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal.old;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * generating the entity corresponding to the table
 */
class GenTable {
	private final GenScope scope;
	private final Table table;

	// these relations cannot be built until after we have built all the scope's tables
	private ManyToManyRelation joinTableRelation;
	private final ArrayList<ManyToManyRelation> ownedManyToManyRelations = new ArrayList<ManyToManyRelation>();
	private final ArrayList<ManyToManyRelation> nonOwnedManyToManyRelations = new ArrayList<ManyToManyRelation>();
	private final ArrayList<ManyToOneRelation> manyToOneRelations = new ArrayList<ManyToOneRelation>();
	private final ArrayList<OneToManyRelation> oneToManyRelations = new ArrayList<OneToManyRelation>();
	private final HashSet<Column> foreignKeyColumns = new HashSet<Column>();

	private final HashSet<String> attributeNames = new HashSet<String>();
	private String attributeNameForEmbeddedId;
	private final HashMap<Column, String> basicAttributeNames = new HashMap<Column, String>();
	private final HashMap<ManyToOneRelation, String> manyToOneAttributeNames = new HashMap<ManyToOneRelation, String>();
	private final HashMap<OneToManyRelation, String> oneToManyAttributeNames = new HashMap<OneToManyRelation, String>();
	private final HashMap<ManyToManyRelation, String> ownedManyToManyAttributeNames = new HashMap<ManyToManyRelation, String>();
	private final HashMap<ManyToManyRelation, String> nonOwnedManyToManyAttributeNames = new HashMap<ManyToManyRelation, String>();


	// ********** construction/initialization **********

	GenTable(GenScope scope, Table table) {
		super();
		this.scope = scope;
		this.table = table;
	}


	// ********** package API **********

	EntityGenerator.Config getEntityConfig() {
		return this.scope.getEntityConfig();
	}

	/**
	 * examples:
	 *   GenTable(FOO) => "FOO_COLLECTION"
	 *   GenTable(foo) => "fooCollection"
	 *   GenTable(Foo) => "FooCollection"
	 */
	String getCollectionAttributeName() {
		String name = this.getName();
		String suffix = this.getEntityConfig().getCollectionAttributeNameSuffix();
		if (StringTools.stringIsUppercase(name)) {  // hmmm  ~bjv
			suffix = '_' + suffix.toUpperCase();
		}
		return name + suffix;
	}

	/**
	 * determine whether the table is a "join" table within the table's scope;
	 * this can be removed, later, if we find another table references the,
	 * seemingly, join table
	 * @see #clearJoinTableRelation() (and callers)
	 */
	void buildJoinTableRelation() {
		if ( ! this.table.isPossibleJoinTable()) {
			return;  // the table must have exactly 2 foreign keys
		}
		ForeignKey owningFK = this.table.getJoinTableOwningForeignKey();
		GenTable owningGenTable = this.scope.getGenTable(owningFK.getReferencedTable());
		if (owningGenTable == null) {
			return;  // both tables must be in the scope
		}
		ForeignKey nonOwningFK = this.table.getJoinTableNonOwningForeignKey();
		GenTable nonOwningGenTable = this.scope.getGenTable(nonOwningFK.getReferencedTable());
		if (nonOwningGenTable == null) {
			return;  // both tables must be in the scope
		}
		this.joinTableRelation = new ManyToManyRelation(
											this,
											owningFK,
											owningGenTable,
											nonOwningFK,
											nonOwningGenTable
										);
	}

	/**
	 * used by the scope to figure out whether "join" tables should be
	 * converted to "entity" tables
	 */
	void addReferencedGenTablesTo(Set<GenTable> referencedTables) {
		for (Iterator<ForeignKey> stream = this.table.foreignKeys(); stream.hasNext(); ) {
			ForeignKey fk = stream.next();
			GenTable genTable = this.scope.getGenTable(fk.getReferencedTable());
			if (genTable != null) {
				referencedTables.add(genTable);
			}
		}
	}

	/**
	 * the scope clears the join table relation if there are any references
	 * to the join table from other tables in the scope
	 */
	void clearJoinTableRelation() {
		this.joinTableRelation.clear();
		this.joinTableRelation = null;
	}

	/**
	 * find "in-scope" foreign keys
	 */
	void buildManyToOneRelations() {
		for (Iterator<ForeignKey> stream = this.table.foreignKeys(); stream.hasNext(); ) {
			ForeignKey fk = stream.next();
			GenTable referencedGenTable = this.scope.getGenTable(fk.getReferencedTable());
			if (referencedGenTable != null) {
				this.manyToOneRelations.add(new ManyToOneRelation(this, fk, referencedGenTable));
			}
		}
	}

	/**
	 * now that all the relations are in place, we can configure the Java
	 * attribute names
	 */
	void buildAttributeNames() {
		this.buildAttributeNameForEmbeddedId();

		// gather up all the table's columns...
		Set<Column> columns = CollectionTools.set(this.table.columns(), this.table.columnsSize());
		// ...remove the columns that belong exclusively to many-to-one foreign keys...
		this.buildManyToOneAttributeNames(columns);
		// ...and use the remaining columns to generate "basic" attribute names
		this.buildBasicAttributeNames(columns);

		this.buildOneToManyAttributeNames();
		this.buildOwnedManyToManyAttributeNames();
		this.buildNonOwnedManyToManyAttributeNames();
	}

	/**
	 * return the columns that are part of the table's primary key
	 * but are also part of an "in-scope" foreign key
	 */
	Iterator<Column> readOnlyPrimaryKeyColumns() {
		return new FilteringIterator<Column, Column>(this.table.primaryKeyColumns()) {
			@Override
			protected boolean accept(Column pkColumn) {
				return pkColumn.isPartOfForeignKey();
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
			protected boolean accept(Column pkColumn) {
				return ! pkColumn.isPartOfForeignKey();
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
				return ! (column.isPartOfPrimaryKey() || column.isPartOfForeignKey());
			}
		};
	}

	Table getTable() {
		return this.table;
	}

	String getEntityName() {
		return this.getEntityConfig().getEntityName(this.table);
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
	 * this will return null if we don't want an embedded id attribute
	 */
	String getAttributeNameForEmbeddedId() {
		return this.attributeNameForEmbeddedId;
	}

	String getAttributeNameFor(Column column) {
		return this.basicAttributeNames.get(column);
	}

	String getAttributeNameFor(ManyToOneRelation relation) {
		return this.manyToOneAttributeNames.get(relation);
	}

	String getAttributeNameFor(OneToManyRelation relation) {
		return this.oneToManyAttributeNames.get(relation);
	}

	String getAttributeNameForOwned(ManyToManyRelation relation) {
		return this.ownedManyToManyAttributeNames.get(relation);
	}

	String getAttributeNameForNonOwned(ManyToManyRelation relation) {
		return this.nonOwnedManyToManyAttributeNames.get(relation);
	}

	String getName() {
		return this.table.getName();
	}

	boolean joinTableNameIsDefault() {
		return this.table.joinTableNameIsDefault();
	}


	// ********** internal API **********

	/**
	 * if we are going to generate an EmbeddedId attribute, add its name to
	 * 'attributeNames' so we don't collide with it later, when generating
	 * attribute names for the columns etc.
	 */
	private void buildAttributeNameForEmbeddedId() {
		if ((this.table.primaryKeyColumnsSize() > 1) && this.getEntityConfig().generateEmbeddedIdForCompoundPK()) {
			this.attributeNameForEmbeddedId = this.configureAttributeName(this.getEntityConfig().getEmbeddedIdAttributeName());
		}
	}

	/**
	 * While we are figuring out the names for the m:1 attributes, remove from the
	 * specified set of columns the columns that are only part of the foreign keys
	 * (leaving the remaining columns for basic attributes).
	 * Store the calculated names so we can get them back later, when we
	 * are generating source.
	 */
	private void buildManyToOneAttributeNames(Set<Column> columns) {
		for (ManyToOneRelation relation : this.manyToOneRelations) {
			CollectionTools.removeAll(columns, relation.getForeignKey().nonPrimaryKeyBaseColumns());
			CollectionTools.addAll(this.foreignKeyColumns, relation.getForeignKey().baseColumns());
			String attributeName = this.configureAttributeName(relation.getAttributeName());
			relation.setMappedBy(attributeName);
			this.manyToOneAttributeNames.put(relation, attributeName);
		}
	}

	/**
	 * Build a unique attribute name for the specified "basic" columns,
	 * checking for name collisions.
	 * Store the calculated names so we can get them back later, when we
	 * are generating source.
	 */
	private void buildBasicAttributeNames(Set<Column> columns) {
		for (Column column : columns) {
			String attributeName = this.configureAttributeName(column.getName());
			this.basicAttributeNames.put(column, attributeName);
		}
	}

	private void buildOneToManyAttributeNames() {
		for (OneToManyRelation relation : this.oneToManyRelations) {
			String attributeName = this.configureAttributeName(relation.getAttributeName());
			this.oneToManyAttributeNames.put(relation, attributeName);
		}
	}

	private void buildOwnedManyToManyAttributeNames() {
		for (ManyToManyRelation relation : this.ownedManyToManyRelations) {
			String attributeName = this.configureAttributeName(relation.getOwnedAttributeName());
			relation.setMappedBy(attributeName);
			this.ownedManyToManyAttributeNames.put(relation, attributeName);
		}
	}

	private void buildNonOwnedManyToManyAttributeNames() {
		for (ManyToManyRelation relation : this.nonOwnedManyToManyRelations) {
			String attributeName = this.configureAttributeName(relation.getNonOwnedAttributeName());
			this.nonOwnedManyToManyAttributeNames.put(relation, attributeName);
		}
	}

	/**
	 * Convert the specified attribute name to something unique for the entity,
	 * converting it to something Java-like if the config flag is set.
	 * Store the calculated name to prevent future name collisions.
	 */
	private String configureAttributeName(String attributeName) {
		String result = attributeName;
		if (this.getEntityConfig().convertToJavaStyleIdentifiers()) {
			result = EntityGenTools.convertToUniqueJavaStyleAttributeName(result, this.attributeNames);
		} else {
			// first, convert the attribute name to a legal Java identifier
			result = NameTools.convertToJavaIdentifier(result);
			// then make sure it's unique
			result = NameTools.uniqueNameForIgnoreCase(attributeName, this.attributeNames);
		}
		this.attributeNames.add(result);
		return result;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.table);
	}

}

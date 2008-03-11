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

import org.eclipse.jpt.db.ForeignKey;

/**
 * This object is shared by the two gen tables that make up the relation.
 * Upon construction, 'mappedBy' will be 'null'. The first gen table to be
 * used to generate an entity will fill in 'mappedBy' with the appropriate
 * field/property name.
 */
class ManyToManyRelation {
	private final GenTable joinTable;
	private final ForeignKey owningForeignKey;
	private final GenTable owningTable;
	private final ForeignKey nonOwningForeignKey;
	private final GenTable nonOwningTable;
	private String mappedBy;


	ManyToManyRelation(GenTable joinTable, ForeignKey owningForeignKey, GenTable owningTable, ForeignKey nonOwningForeignKey, GenTable nonOwningTable) {
		super();
		this.joinTable = joinTable;

		this.owningForeignKey = owningForeignKey;
		this.owningTable = owningTable;
		owningTable.addOwnedManyToManyRelation(this);

		this.nonOwningForeignKey = nonOwningForeignKey;
		this.nonOwningTable = nonOwningTable;
		nonOwningTable.addNonOwnedManyToManyRelation(this);
	}

	GenTable getJoinTable() {
		return this.joinTable;
	}

	ForeignKey getOwningForeignKey() {
		return this.owningForeignKey;
	}

	GenTable getOwningTable() {
		return this.owningTable;
	}

	ForeignKey getNonOwningForeignKey() {
		return this.nonOwningForeignKey;
	}

	GenTable getNonOwningTable() {
		return this.nonOwningTable;
	}

	private GenTable otherTable(GenTable table) {
		return (table == this.owningTable) ? this.nonOwningTable : this.owningTable;
	}

	String javaFieldNameFor(GenTable table) {
		// TODO i18n?
		return this.otherTable(table).javaFieldName() + "_collection";
	}

	void clear() {
		this.owningTable.removeOwnedManyToManyRelation(this);
		this.nonOwningTable.removeNonOwnedManyToManyRelation(this);
	}

	String getMappedBy() {
		return this.mappedBy;
	}

	void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	String owningEntityName() {
		return this.owningTable.getEntityName();
	}

	String nonOwningEntityName() {
		return this.nonOwningTable.getEntityName();
	}

	boolean joinTableNameIsDefault() {
		return this.joinTable.name().equals(this.getOwningTable().name() + "_" + this.getNonOwningTable().name());
	}

	boolean joinColumnsIsDefaultFor(String javaFieldName) {
		return this.owningForeignKey.isDefaultFor(javaFieldName);
	}

	boolean inverseJoinColumnsIsDefaultFor(String javaFieldName) {
		return this.nonOwningForeignKey.isDefaultFor(javaFieldName);
	}

}

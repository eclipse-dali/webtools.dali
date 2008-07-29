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
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * This object is shared by the two gen tables that make up the relation.
 * Upon construction, 'mappedBy' will be 'null'. The first gen table to be
 * used to generate an entity will fill in 'mappedBy' with the appropriate
 * attribute (field/property) name.
 */
class ManyToManyRelation {
	private final GenTable joinGenTable;
	private final ForeignKey owningForeignKey;
	private final GenTable owningGenTable;
	private final ForeignKey nonOwningForeignKey;
	private final GenTable nonOwningGenTable;
	private String mappedBy;  // set while generating entities


	ManyToManyRelation(
			GenTable joinGenTable,
			ForeignKey owningForeignKey,
			GenTable owningGenTable,
			ForeignKey nonOwningForeignKey,
			GenTable nonOwningGenTable
	) {
		super();
		this.joinGenTable = joinGenTable;

		this.owningForeignKey = owningForeignKey;
		this.owningGenTable = owningGenTable;
		owningGenTable.addOwnedManyToManyRelation(this);

		this.nonOwningForeignKey = nonOwningForeignKey;
		this.nonOwningGenTable = nonOwningGenTable;
		nonOwningGenTable.addNonOwnedManyToManyRelation(this);
	}

	GenTable getJoinGenTable() {
		return this.joinGenTable;
	}

	ForeignKey getOwningForeignKey() {
		return this.owningForeignKey;
	}

	GenTable getOwningGenTable() {
		return this.owningGenTable;
	}

	ForeignKey getNonOwningForeignKey() {
		return this.nonOwningForeignKey;
	}

	GenTable getNonOwningGenTable() {
		return this.nonOwningGenTable;
	}

	String getOwnedJavaAttributeName() {
		return this.nonOwningGenTable.getName() + this.getCollectionAttributeNameSuffix();
	}

	String getNonOwnedJavaAttributeName() {
		return this.owningGenTable.getName() + this.getCollectionAttributeNameSuffix();
	}

	private String getCollectionAttributeNameSuffix() {
		return this.getEntityConfig().getCollectionAttributeNameSuffix();
	}

	private EntityGenerator.Config getEntityConfig() {
		return this.joinGenTable.getEntityConfig();
	}

	/**
	 * the scope clears the join table relation if there are any references
	 * to the join table
	 */
	void clear() {
		this.owningGenTable.removeOwnedManyToManyRelation(this);
		this.nonOwningGenTable.removeNonOwnedManyToManyRelation(this);
	}

	String getMappedBy() {
		return this.mappedBy;
	}

	void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	String getOwningEntityName() {
		return this.owningGenTable.getEntityName();
	}

	String getNonOwningEntityName() {
		return this.nonOwningGenTable.getEntityName();
	}

	boolean joinTableNameIsDefault() {
		return this.joinGenTable.joinTableNameIsDefault();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.joinGenTable);
	}

}

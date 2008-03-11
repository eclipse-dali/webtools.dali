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

class ManyToOneRelation {
	private final GenTable baseTable;  // the "many" side
	private final ForeignKey foreignKey;
	private final GenTable referencedTable;  // the "one" side
	private String mappedBy;


	ManyToOneRelation(GenTable baseTable, ForeignKey foreignKey, GenTable referencedTable) {
		super();
		this.baseTable = baseTable;
		this.foreignKey = foreignKey;
		this.referencedTable = referencedTable;
		referencedTable.addOneToManyRelation(new OneToManyRelation(this));
	}

	GenTable getBaseTable() {
		return this.baseTable;
	}

	ForeignKey getForeignKey() {
		return this.foreignKey;
	}

	GenTable getReferencedTable() {
		return this.referencedTable;
	}

	String javaFieldName() {
		return this.foreignKey.javaFieldName();
	}

	String getMappedBy() {
		return this.mappedBy;
	}

	void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	String baseEntityName() {
		return this.baseTable.getEntityName();
	}

	String referencedEntityName() {
		return this.referencedTable.getEntityName();
	}

}

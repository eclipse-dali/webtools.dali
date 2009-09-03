/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal.old;

import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * This object is held by the "base" gen table and, indirectly via a
 * one-to-many relation, the "referenced" gen table.
 * The "mapped by" attribute (field/property) name is set while the
 * "base" table is calculating its attribute names.
 */
class ManyToOneRelation {
	private final GenTable baseGenTable;  // the "many" side (e.g. Detail)
	private final ForeignKey foreignKey;
	private final GenTable referencedGenTable;  // the "one" side (e.g. Master)
	private String mappedBy;  // set while generating entities; used by partner one-to-many relation


	ManyToOneRelation(
			GenTable baseGenTable,
			ForeignKey foreignKey,
			GenTable referencedGenTable
	) {
		super();
		this.baseGenTable = baseGenTable;
		this.foreignKey = foreignKey;
		this.referencedGenTable = referencedGenTable;
		referencedGenTable.addOneToManyRelation(new OneToManyRelation(this));
	}

	GenTable getBaseGenTable() {
		return this.baseGenTable;
	}

	ForeignKey getForeignKey() {
		return this.foreignKey;
	}

	GenTable getReferencedGenTable() {
		return this.referencedGenTable;
	}

	String getAttributeName() {
		return this.foreignKey.getAttributeName();
	}

	String getMappedBy() {
		return this.mappedBy;
	}

	void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	String getBaseEntityName() {
		return this.baseGenTable.getEntityName();
	}

	String getReferencedEntityName() {
		return this.referencedGenTable.getEntityName();
	}

	String getBaseGenTableCollectionAttributeName() {
		return this.baseGenTable.getCollectionAttributeName();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.foreignKey);
	}

}

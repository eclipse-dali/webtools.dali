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

import org.eclipse.jpt.utility.internal.StringTools;

/**
 * This object is the inverse of the corresponding many-to-one relation.
 */
class OneToManyRelation {
	private final ManyToOneRelation manyToOneRelation;


	OneToManyRelation(ManyToOneRelation manyToOneRelation) {
		super();
		this.manyToOneRelation = manyToOneRelation;
	}

	ManyToOneRelation getManyToOneRelation() {
		return this.manyToOneRelation;
	}

	/**
	 * e.g. "FOO_collection"
	 */
	String getJavaAttributeName() {
		return this.manyToOneRelation.getBaseGenTable().getName() + this.getCollectionAttributeNameSuffix();
	}

	String getMappedBy() {
		return this.manyToOneRelation.getMappedBy();
	}

	String getReferencedEntityName() {
		return this.manyToOneRelation.getBaseEntityName();
	}

	private String getCollectionAttributeNameSuffix() {
		return this.getEntityConfig().getCollectionAttributeNameSuffix();
	}

	private EntityGenerator.Config getEntityConfig() {
		return this.manyToOneRelation.getEntityConfig();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.manyToOneRelation);
	}

}

/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal;

class OneToManyRelation {
	private final ManyToOneRelation manyToOneRelation;

	OneToManyRelation(ManyToOneRelation manyToOneRelation) {
		super();
		this.manyToOneRelation = manyToOneRelation;
	}

	ManyToOneRelation getManyToOneRelation() {
		return this.manyToOneRelation;
	}

	String javaFieldName() {
		// TODO i18n?
		return this.manyToOneRelation.getBaseTable().javaFieldName() + "_collection";
	}

	String mappedBy() {
		return this.manyToOneRelation.getMappedBy();
	}

	String referencedEntityName() {
		return this.manyToOneRelation.baseEntityName();
	}

}

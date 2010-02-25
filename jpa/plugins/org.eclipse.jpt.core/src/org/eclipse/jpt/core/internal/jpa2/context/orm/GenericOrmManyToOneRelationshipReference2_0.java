/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmManyToOneRelationshipReference;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;

public class GenericOrmManyToOneRelationshipReference2_0
	extends AbstractOrmManyToOneRelationshipReference
{

	public GenericOrmManyToOneRelationshipReference2_0(
			OrmManyToOneMapping parent, XmlManyToOne resource) {
		super(parent, resource);
	}

	@Override
	protected OrmJoinTableJoiningStrategy buildJoinTableJoiningStrategy() {
		return new GenericOrmJoinTableJoiningStrategy(this, getResourceMapping());
	}

	@Override
	public void initializeOn(OrmRelationshipReference newRelationshipReference) {
		super.initializeOn(newRelationshipReference);
		newRelationshipReference.initializeFromJoinTableEnabledRelationshipReference(this);
	}

	@Override
	public void initializeFromJoinTableEnabledRelationshipReference(
			OrmJoinTableEnabledRelationshipReference oldRelationshipReference) {
		super.initializeFromJoinTableEnabledRelationshipReference(oldRelationshipReference);
		OrmJoinTable oldTable = 
			oldRelationshipReference.getJoinTableJoiningStrategy().getJoinTable();
		if (oldTable != null) {
			this.joinTableJoiningStrategy.addJoinTable().initializeFrom(oldTable);
		}
	}

	@Override
	protected OrmJoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.joinTableJoiningStrategy.getJoinTable() != null){
			return this.joinTableJoiningStrategy;
		}
		return this.joinColumnJoiningStrategy;
	}

}

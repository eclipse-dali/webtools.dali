/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmOneToManyRelationshipReference;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class GenericOrmOneToManyRelationshipReference2_0
	extends AbstractOrmOneToManyRelationshipReference
{	
	public GenericOrmOneToManyRelationshipReference2_0(
			OrmOneToManyMapping parent, XmlOneToMany resource) {
		
		super(parent, resource);
	}
	
	@Override
	protected OrmJoinColumnJoiningStrategy buildJoinColumnJoiningStrategy() {
		return new GenericOrmJoinColumnJoiningStrategy(this, getResourceMapping());
	}

	@Override
	public void initializeOn(OrmRelationshipReference newRelationshipReference) {
		super.initializeOn(newRelationshipReference);
		newRelationshipReference.initializeFromJoinColumnEnabledRelationshipReference(this);
	}

	@Override
	public void initializeFromJoinColumnEnabledRelationshipReference(
			OrmJoinColumnEnabledRelationshipReference oldRelationshipReference) {
		super.initializeFromJoinColumnEnabledRelationshipReference(oldRelationshipReference);
		int index = 0;
		for (JoinColumn joinColumn : 
				CollectionTools.iterable(
					oldRelationshipReference.getJoinColumnJoiningStrategy().specifiedJoinColumns())) {
			OrmJoinColumn newJoinColumn = this.joinColumnJoiningStrategy.addSpecifiedJoinColumn(index++);
			newJoinColumn.initializeFrom(joinColumn);
		}
	}

	@Override
	protected OrmJoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.mappedByJoiningStrategy.getMappedByAttribute() != null) {
			return this.mappedByJoiningStrategy;
		}
		else if (this.joinColumnJoiningStrategy.hasSpecifiedJoinColumns()) {
			return this.joinColumnJoiningStrategy;
		}
		else {
			return this.joinTableJoiningStrategy;
		}
	}
	
	
	// **************** mapped by **********************************************
	
	@Override
	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return super.mayBeMappedBy(mappedByMapping) ||
			mappedByMapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
}

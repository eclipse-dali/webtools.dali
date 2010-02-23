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
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.orm.OrmJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmOneToManyRelationshipReference;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;

public class GenericOrmOneToManyRelationshipReference
	extends AbstractOrmOneToManyRelationshipReference
{	
	protected OrmJoinColumnJoiningStrategy joinColumnJoiningStrategy;

	public GenericOrmOneToManyRelationshipReference(
			OrmOneToManyMapping parent, XmlOneToMany resource) {
		
		super(parent, resource);
	}
	
	@Override
	protected void initializeJoiningStrategies() {
		this.joinColumnJoiningStrategy = buildJoinColumnJoiningStrategy();		
		super.initializeJoiningStrategies();
	}
	
	
	protected OrmJoinColumnJoiningStrategy buildJoinColumnJoiningStrategy() {
		return new NullOrmJoinColumnJoiningStrategy(this);
	}
	
	
	@Override
	protected OrmJoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.mappedByJoiningStrategy.getMappedByAttribute() != null) {
			return this.mappedByJoiningStrategy;
		}
		return this.joinTableJoiningStrategy;
	}


	// **************** join columns *******************************************
	
	public OrmJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}
	
	public boolean usesJoinColumnJoiningStrategy() {
		return false;
	}
	
	public void setJoinColumnJoiningStrategy() {
		throw new UnsupportedOperationException("join column joining strategy not supported on a 1.0 1-m mapping"); //$NON-NLS-1$
	}
	
	public void unsetJoinColumnJoiningStrategy() {
		throw new UnsupportedOperationException("join column joining strategy not supported on a 1.0 1-m mapping"); //$NON-NLS-1$
	}
	
	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}

}

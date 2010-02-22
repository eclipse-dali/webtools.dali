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
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmOwnableRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping;
import org.eclipse.jpt.core.utility.TextRange;

public abstract class AbstractOrmRelationshipReference 
	extends AbstractOrmXmlContextNode
	implements OrmRelationshipReference
{
	protected AbstractXmlRelationshipMapping resourceMapping;
	
	// cache the strategy for property change notification
	protected OrmJoiningStrategy cachedPredominantJoiningStrategy;
	
	
	protected AbstractOrmRelationshipReference(
			OrmRelationshipMapping parent, AbstractXmlRelationshipMapping resourceMapping) {
		
		super(parent);
		this.resourceMapping = resourceMapping;
		this.initialize();
	}
	
	public void initializeFromOwnableRelationshipReference(
			OrmOwnableRelationshipReference oldRelationshipReference) {
		// no op
	}
	
	public void initializeFromJoinColumnEnabledRelationshipReference(
			OrmJoinColumnEnabledRelationshipReference oldRelationshipReference) {
		// no op
	}
	
	public void initializeFromJoinTableEnabledRelationshipReference(
			OrmJoinTableEnabledRelationshipReference oldRelationshipReference) {
		// no op
	}
	
	public OrmRelationshipMapping getRelationshipMapping() {
		return (OrmRelationshipMapping) getParent();
	}
	
	public TypeMapping getTypeMapping() {
		return getRelationshipMapping().getTypeMapping();
	}
	
	public Entity getEntity() {
		TypeMapping typeMapping = getTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}
	
	public boolean isOverridableAssociation() {
		return getPredominantJoiningStrategy().isOverridableAssociation();
	}

	public boolean isParentVirtual() {
		return getRelationshipMapping().getPersistentAttribute().isVirtual();
	}
	
	
	// **************** predominant joining strategy ***************************
	
	public OrmJoiningStrategy getPredominantJoiningStrategy() {
		return this.cachedPredominantJoiningStrategy;
	}
	
	protected void setPredominantJoiningStrategy() {
		setPredominantJoiningStrategy(calculatePredominantJoiningStrategy());
	}
	
	protected void setPredominantJoiningStrategy(OrmJoiningStrategy newJoiningStrategy) {
		JoiningStrategy oldJoiningStrategy = this.cachedPredominantJoiningStrategy;
		this.cachedPredominantJoiningStrategy = newJoiningStrategy;
		firePropertyChanged(PREDOMINANT_JOINING_STRATEGY_PROPERTY, oldJoiningStrategy, newJoiningStrategy);
	}
	
	
	// **************** resource -> context ************************************
	
	protected void initialize() {
		initializeJoiningStrategies();
		this.cachedPredominantJoiningStrategy = calculatePredominantJoiningStrategy();
	}
	
	protected abstract void initializeJoiningStrategies();
	

	public void update() {
		updateJoiningStrategies();
		setPredominantJoiningStrategy();
	}
	
	protected abstract void updateJoiningStrategies();
	
	protected abstract OrmJoiningStrategy calculatePredominantJoiningStrategy();
	
	
	// **************** validation *********************************************
	
	public TextRange getValidationTextRange() {
		return getRelationshipMapping().getValidationTextRange();
	}
}

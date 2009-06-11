/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmOwnableRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.AbstractXmlRelationshipMapping;
import org.eclipse.jpt.core.utility.TextRange;

public abstract class AbstractOrmRelationshipReference 
	extends AbstractXmlContextNode
	implements OrmRelationshipReference
{
	protected AbstractXmlRelationshipMapping resourceMapping;
	
	// cache the strategy for property change notification
	protected JoiningStrategy cachedPredominantJoiningStrategy;
	
	
	protected AbstractOrmRelationshipReference(
			OrmRelationshipMapping parent, AbstractXmlRelationshipMapping resourceMapping) {
		super(parent);
		this.resourceMapping = resourceMapping;
		initializeJoiningStrategies();
	}
	
	protected abstract void initializeJoiningStrategies();
	
	
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
	
	
	// **************** predominant joining strategy ***************************
	
	public JoiningStrategy getPredominantJoiningStrategy() {
		if (this.cachedPredominantJoiningStrategy == null) {
			this.cachedPredominantJoiningStrategy = calculatePredominantJoiningStrategy();
		}
		return this.cachedPredominantJoiningStrategy;
	}
	
	protected void setPredominantJoiningStrategy(JoiningStrategy newJoiningStrategy) {
		JoiningStrategy oldJoiningStrategy = this.cachedPredominantJoiningStrategy;
		this.cachedPredominantJoiningStrategy = newJoiningStrategy;
		firePropertyChanged(PREDOMINANT_JOINING_STRATEGY_PROPERTY, oldJoiningStrategy, newJoiningStrategy);
	}
	
	
	// **************** resource -> context ************************************
	
	public void update() {
		updateJoiningStrategies();
		setPredominantJoiningStrategy(calculatePredominantJoiningStrategy());
	}
	
	protected abstract void updateJoiningStrategies();
	
	protected abstract JoiningStrategy calculatePredominantJoiningStrategy();
	
	
	// **************** validation *********************************************
	
	public TextRange getValidationTextRange() {
		return getRelationshipMapping().getValidationTextRange();
	}
}

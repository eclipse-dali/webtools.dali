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
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaRelationshipReference;
import org.eclipse.jpt.core.utility.TextRange;

public abstract class AbstractJavaRelationshipReference
	extends AbstractJavaJpaContextNode 
	implements JavaRelationshipReference
{
	// cache the strategy for property change notification
	protected JoiningStrategy cachedPredominantJoiningStrategy;
	
	
	public AbstractJavaRelationshipReference(JavaRelationshipMapping parent) {
		super(parent);
	}
	
	
	public JavaRelationshipMapping getRelationshipMapping() {
		return (JavaRelationshipMapping) getParent();
	}
		
	public boolean isOverridableAssociation() {
		return getPredominantJoiningStrategy().isOverridableAssociation();
	}

	
	// **************** predominant joining strategy ***************************
	
	public JoiningStrategy getPredominantJoiningStrategy() {
		return this.cachedPredominantJoiningStrategy;
	}
	
	protected void setPredominantJoiningStrategy(JoiningStrategy newJoiningStrategy) {
		JoiningStrategy oldJoiningStrategy = this.cachedPredominantJoiningStrategy;
		this.cachedPredominantJoiningStrategy = newJoiningStrategy;
		firePropertyChanged(PREDOMINANT_JOINING_STRATEGY_PROPERTY, oldJoiningStrategy, newJoiningStrategy);
	}
	
	
	// **************** resource -> context ************************************
	
	public void initialize() {
		initializeJoiningStrategies();
		this.cachedPredominantJoiningStrategy = calculatePredominantJoiningStrategy();
	}
	
	protected abstract void initializeJoiningStrategies();
	
	public void update() {
		updateJoiningStrategies();
		setPredominantJoiningStrategy(calculatePredominantJoiningStrategy());
	}
	
	protected abstract void updateJoiningStrategies();
	
	protected abstract JoiningStrategy calculatePredominantJoiningStrategy();
	
	
	// **************** validation *********************************************
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getRelationshipMapping().getValidationTextRange(astRoot);
	}
}

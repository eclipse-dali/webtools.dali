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
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.DerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;

public class GenericJavaDerivedIdentity2_0 extends AbstractJavaJpaContextNode
	implements JavaDerivedIdentity2_0
{
	// cache the strategy for property change notification
	protected DerivedIdentityStrategy2_0 cachedPredominantDerivedIdentityStrategy;
	
	protected JavaIdDerivedIdentityStrategy2_0 idDerivedIdentityStrategy;
	
	protected JavaMapsIdDerivedIdentityStrategy2_0 mapsIdDerivedIdentityStrategy;
	
	
	public GenericJavaDerivedIdentity2_0(JavaSingleRelationshipMapping2_0 parent) {
		super(parent);
		this.idDerivedIdentityStrategy = buildIdDerivedIdentityStrategy();
		this.mapsIdDerivedIdentityStrategy = buildMapsIdDerivedIdentityStrategy();
	}
	
	
	protected JavaIdDerivedIdentityStrategy2_0 buildIdDerivedIdentityStrategy() {
		return new GenericJavaIdDerivedIdentityStrategy2_0(this);
	}
	
	protected JavaMapsIdDerivedIdentityStrategy2_0 buildMapsIdDerivedIdentityStrategy() {
		return new GenericJavaMapsIdDerivedIdentityStrategy2_0(this);
	}
	
	@Override
	public JavaSingleRelationshipMapping2_0 getParent() {
		return (JavaSingleRelationshipMapping2_0) super.getParent();
	}
	
	public JavaSingleRelationshipMapping2_0 getMapping() {
		return getParent();
	}
	
	
	// **************** predominant joining strategy ***************************
	
	public DerivedIdentityStrategy2_0 getPredominantDerivedIdentityStrategy() {
		return this.cachedPredominantDerivedIdentityStrategy;
	}
	
	protected void setPredominantJoiningStrategy(DerivedIdentityStrategy2_0 newStrategy) {
		DerivedIdentityStrategy2_0 oldStrategy = this.cachedPredominantDerivedIdentityStrategy;
		this.cachedPredominantDerivedIdentityStrategy = newStrategy;
		firePropertyChanged(PREDOMINANT_DERIVED_IDENTITY_STRATEGY_PROPERTY, oldStrategy, newStrategy);
	}
	
	
	// **************** resource -> context ************************************
	
	public void initialize() {
		this.idDerivedIdentityStrategy.initialize();
		this.mapsIdDerivedIdentityStrategy.initialize();
		this.cachedPredominantDerivedIdentityStrategy = calculatePredominantDerivedIdentityStrategy();
	}
	
	public void update() {
		this.idDerivedIdentityStrategy.update();
		this.mapsIdDerivedIdentityStrategy.update();
		setPredominantJoiningStrategy(calculatePredominantDerivedIdentityStrategy());
	}
	
	protected DerivedIdentityStrategy2_0 calculatePredominantDerivedIdentityStrategy() {
		if (this.mapsIdDerivedIdentityStrategy.isSpecified()) {
			return this.mapsIdDerivedIdentityStrategy;
		}
		else if (this.idDerivedIdentityStrategy.isSpecified()) {
			return this.idDerivedIdentityStrategy;
		}
		else {
			return null;
		}
	}
	
	
	// **************** no strategy *******************************************
	
	public void setNullDerivedIdentityStrategy() {
		this.mapsIdDerivedIdentityStrategy.removeStrategy();
		this.idDerivedIdentityStrategy.removeStrategy();
	}
	
	public boolean usesNullDerivedIdentityStrategy() {
		return this.cachedPredominantDerivedIdentityStrategy == null;
	}
	
	
	// **************** maps id strategy **************************************
	
	public JavaMapsIdDerivedIdentityStrategy2_0 getMapsIdDerivedIdentityStrategy() {
		return this.mapsIdDerivedIdentityStrategy;
	}
	
	public void setMapsIdDerivedIdentityStrategy() {
		this.mapsIdDerivedIdentityStrategy.addStrategy();
		this.idDerivedIdentityStrategy.removeStrategy();
	}
	
	public void unsetMapsIdDerivedIdentityStrategy() {
		this.mapsIdDerivedIdentityStrategy.removeStrategy();
	}
	
	public boolean usesMapsIdDerivedIdentityStrategy() {
		return this.cachedPredominantDerivedIdentityStrategy == this.mapsIdDerivedIdentityStrategy;
	}

	
	// **************** id strategy *******************************************
	
	public JavaIdDerivedIdentityStrategy2_0 getIdDerivedIdentityStrategy() {
		return this.idDerivedIdentityStrategy;
	}
	
	public void setIdDerivedIdentityStrategy() {
		this.idDerivedIdentityStrategy.addStrategy();
		this.mapsIdDerivedIdentityStrategy.removeStrategy();
	}
	
	public void unsetIdDerivedIdentityStrategy() {
		this.idDerivedIdentityStrategy.removeStrategy();
	}
	
	public boolean usesIdDerivedIdentityStrategy() {
		return this.cachedPredominantDerivedIdentityStrategy == this.idDerivedIdentityStrategy;
	}
	
	
	// **************** java completion ***************************************
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result == null) {
			result = this.mapsIdDerivedIdentityStrategy.javaCompletionProposals(pos, filter, astRoot);
		}
		if (result == null) {
			result = this.idDerivedIdentityStrategy.javaCompletionProposals(pos, filter, astRoot);
		}
		return result;
	}
	
	
	// **************** validation ********************************************
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getMapping().getValidationTextRange(astRoot);
	}
}

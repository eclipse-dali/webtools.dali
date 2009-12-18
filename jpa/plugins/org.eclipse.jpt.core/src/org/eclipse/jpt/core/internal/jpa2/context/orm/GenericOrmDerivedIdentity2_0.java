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
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.DerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmDerivedIdentity2_0
	extends AbstractOrmXmlContextNode
	implements OrmDerivedIdentity2_0
{
	protected XmlSingleRelationshipMapping_2_0 resource;
	
	// cache the strategy for property change notification
	protected DerivedIdentityStrategy2_0 cachedPredominantDerivedIdentityStrategy;
	
	protected OrmIdDerivedIdentityStrategy2_0 idDerivedIdentityStrategy;
	
	protected OrmMapsIdDerivedIdentityStrategy2_0 mapsIdDerivedIdentityStrategy;
	
	
	public GenericOrmDerivedIdentity2_0(
			OrmSingleRelationshipMapping2_0 parent, XmlSingleRelationshipMapping_2_0 resource) {
		super(parent);
		this.resource = resource;
		this.idDerivedIdentityStrategy = buildIdDerivedIdentityStrategy();
		this.mapsIdDerivedIdentityStrategy = buildMapsIdDerivedIdentityStrategy();
		this.cachedPredominantDerivedIdentityStrategy = calculatePredominantDerivedIdentityStrategy();
	}
	
	
	protected OrmIdDerivedIdentityStrategy2_0 buildIdDerivedIdentityStrategy() {
		return new GenericOrmIdDerivedIdentityStrategy2_0(this, resource);
	}
	
	protected OrmMapsIdDerivedIdentityStrategy2_0 buildMapsIdDerivedIdentityStrategy() {
		return new GenericOrmMapsIdDerivedIdentityStrategy2_0(this, resource);
	}
	
	public OrmSingleRelationshipMapping2_0 getMapping() {
		return (OrmSingleRelationshipMapping2_0) getParent();
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
	
	public OrmMapsIdDerivedIdentityStrategy2_0 getMapsIdDerivedIdentityStrategy() {
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
	
	public OrmIdDerivedIdentityStrategy2_0 getIdDerivedIdentityStrategy() {
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
	
	
	// **************** morphing **********************************************
	
	public void initializeFrom(OrmDerivedIdentity2_0 oldDerivedIdentity) {
		this.mapsIdDerivedIdentityStrategy.initializeFrom(oldDerivedIdentity.getMapsIdDerivedIdentityStrategy());
		this.idDerivedIdentityStrategy.initializeFrom(oldDerivedIdentity.getIdDerivedIdentityStrategy());
		this.cachedPredominantDerivedIdentityStrategy = calculatePredominantDerivedIdentityStrategy();
	}
	
	
	// **************** validation ********************************************
	
	public TextRange getValidationTextRange() {
		return getMapping().getValidationTextRange();
	}
}

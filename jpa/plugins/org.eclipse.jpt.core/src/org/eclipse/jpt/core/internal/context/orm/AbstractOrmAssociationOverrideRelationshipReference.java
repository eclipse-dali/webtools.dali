/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.AssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnInAssociationOverrideJoiningStrategy;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.utility.TextRange;

public abstract class AbstractOrmAssociationOverrideRelationshipReference extends AbstractOrmXmlContextNode
	implements OrmAssociationOverrideRelationshipReference
{

	// cache the strategy for property change notification
	protected JoiningStrategy cachedPredominantJoiningStrategy;

	protected OrmJoinColumnInAssociationOverrideJoiningStrategy joinColumnJoiningStrategy;

	protected AbstractOrmAssociationOverrideRelationshipReference(OrmAssociationOverride parent, XmlAssociationOverride xao) {
		super(parent);
		this.initializeJoiningStrategies(xao);
	}
	
	protected OrmJoinColumnInAssociationOverrideJoiningStrategy buildJoinColumnJoiningStrategy(XmlAssociationOverride xao) {
		return new GenericOrmJoinColumnInAssociationOverrideJoiningStrategy(this, xao);
	}
	
	public void initializeFrom(AssociationOverrideRelationshipReference oldAssociationOverride) {
		if (oldAssociationOverride.getJoinColumnJoiningStrategy().hasSpecifiedJoinColumns()) {
			getJoinColumnJoiningStrategy().initializeFrom(oldAssociationOverride.getJoinColumnJoiningStrategy());
		}
	}
	
	@Override
	public OrmAssociationOverride getParent() {
		return (OrmAssociationOverride) super.getParent();
	}
	
	public OrmAssociationOverride getAssociationOverride() {
		return getParent();
	}

	public TypeMapping getTypeMapping() {
		return getAssociationOverride().getOwner().getTypeMapping();
	}
	
	public Entity getEntity() {
		TypeMapping typeMapping = getTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}
	
	public boolean isOverridableAssociation() {
		return false;
	}
	
	public boolean isParentVirtual() {
		return getAssociationOverride().isVirtual();
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

	protected void initialize(XmlAssociationOverride associationOverride) {
		initializeJoiningStrategies(associationOverride);
		this.cachedPredominantJoiningStrategy = calculatePredominantJoiningStrategy();
	}		
	
	protected void initializeJoiningStrategies(XmlAssociationOverride xao) {
		this.joinColumnJoiningStrategy = buildJoinColumnJoiningStrategy(xao);
	}

	public void update(XmlAssociationOverride associationOverride) {
		updateJoiningStrategies(associationOverride);
		setPredominantJoiningStrategy(calculatePredominantJoiningStrategy());
	}	
		
	protected void updateJoiningStrategies(XmlAssociationOverride associationOverride) {
		this.joinColumnJoiningStrategy.update(associationOverride);
	}
	
	protected abstract JoiningStrategy calculatePredominantJoiningStrategy();
	
	// **************** join columns *******************************************
	

	public OrmJoinColumnInAssociationOverrideJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnJoiningStrategy;
	}
	
	public boolean usesJoinColumnJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinColumnJoiningStrategy;
	}
	
	public void setJoinColumnJoiningStrategy() {
		this.joinColumnJoiningStrategy.addStrategy();
	}
	
	public void unsetJoinColumnJoiningStrategy() {
		this.joinColumnJoiningStrategy.removeStrategy();
	}
	
	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}

	public RelationshipMapping getRelationshipMapping() {
		return getAssociationOverride().getOwner().getRelationshipMapping(getAssociationOverride().getName());
	}

	public boolean isOwnedBy(RelationshipMapping mapping) {
		return getRelationshipMapping().isOwnedBy(mapping);
	}

	public boolean isRelationshipOwner() {
		return getRelationshipMapping().isRelationshipOwner();
	}
	
	// ********** validation **********


	public TextRange getValidationTextRange() {
		return getAssociationOverride().getValidationTextRange();
	}

}

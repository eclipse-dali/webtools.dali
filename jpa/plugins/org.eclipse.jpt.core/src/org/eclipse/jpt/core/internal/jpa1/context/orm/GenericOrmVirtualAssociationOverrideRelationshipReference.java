/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.AssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmVirtualJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmVirtualJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmVirtualJoiningStrategy;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmVirtualOverrideJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmVirtualOverrideJoinTableJoiningStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmVirtualAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmVirtualAssociationOverrideRelationshipReference
	extends AbstractOrmXmlContextNode
	implements OrmVirtualAssociationOverrideRelationshipReference2_0
{
	protected OrmVirtualJoiningStrategy strategy;

	protected final OrmVirtualJoinColumnJoiningStrategy joinColumnStrategy;

	// JPA 2.0
	protected final OrmVirtualJoinTableJoiningStrategy joinTableStrategy;


	public GenericOrmVirtualAssociationOverrideRelationshipReference(OrmVirtualAssociationOverride parent) {
		super(parent);
		this.joinColumnStrategy = this.buildJoinColumnStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.setStrategy(this.buildStrategy());
		this.joinColumnStrategy.update();
		this.joinTableStrategy.update();
	}


	// ********** strategy **********

	public OrmVirtualJoiningStrategy getPredominantJoiningStrategy() {
		return this.strategy;
	}

	protected void setStrategy(OrmVirtualJoiningStrategy strategy) {
		OrmVirtualJoiningStrategy old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(PREDOMINANT_JOINING_STRATEGY_PROPERTY, old, strategy);
	}

	protected OrmVirtualJoiningStrategy buildStrategy() {
		if (this.isJpa2_0Compatible()) {
			if (this.joinColumnStrategy.hasSpecifiedJoinColumns()) {
				return this.joinColumnStrategy;
			}
			return this.joinTableStrategy;
		}
		return this.joinColumnStrategy;
	}


	// ********** join column strategy **********

	public OrmVirtualJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean usesJoinColumnJoiningStrategy() {
		return this.strategy == this.joinColumnStrategy;
	}

	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}

	protected OrmVirtualJoinColumnJoiningStrategy buildJoinColumnStrategy() {
		return new GenericOrmVirtualOverrideJoinColumnJoiningStrategy(this);
	}


	// ********** join table strategy **********

	public OrmVirtualJoinTableJoiningStrategy getJoinTableJoiningStrategy() {
		return this.joinTableStrategy;
	}

	public boolean usesJoinTableJoiningStrategy() {
		return this.strategy == this.joinTableStrategy;
	}

	public boolean mayHaveDefaultJoinTable() {
		return this.isVirtual();
	}

	protected OrmVirtualJoinTableJoiningStrategy buildJoinTableStrategy() {
		return new GenericOrmVirtualOverrideJoinTableJoiningStrategy2_0(this);
	}


	// ********** conversions **********

	public void initializeOn(RelationshipReference newRelationship) {
		newRelationship.initializeFromJoinTableRelationship(this);
		newRelationship.initializeFromJoinColumnRelationship(this);
	}

	public void initializeOnSpecified(AssociationOverrideRelationshipReference specifiedRelationship) {
		specifiedRelationship.initializeFromVirtualJoinColumnRelationship(this);
		specifiedRelationship.initializeFromVirtualJoinTableRelationship(this);
	}


	// ********** misc **********

	@Override
	public OrmVirtualAssociationOverride getParent() {
		return (OrmVirtualAssociationOverride) super.getParent();
	}

	public OrmVirtualAssociationOverride getAssociationOverride() {
		return this.getParent();
	}

	public TypeMapping getTypeMapping() {
		return this.getAssociationOverride().getContainer().getTypeMapping();
	}

	public Entity getEntity() {
		TypeMapping typeMapping = this.getTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	public boolean isVirtual() {
		return true;
	}

	public RelationshipMapping getMapping() {
		return this.getAssociationOverride().getMapping();
	}

	public RelationshipReference resolveOverriddenRelationship() {
		return this.getAssociationOverride().resolveOverriddenRelationship();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return null;
	}
}

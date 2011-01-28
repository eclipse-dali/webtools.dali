/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.OverrideRelationship;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.Relationship;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmVirtualJoinColumnRelationshipStrategy;
import org.eclipse.jpt.core.context.orm.OrmVirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.core.context.orm.OrmVirtualRelationshipStrategy;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmVirtualOverrideJoinColumnRelationshipStrategy;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmVirtualOverrideJoinTableRelationshipStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmVirtualOverrideRelationship2_0;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmVirtualOverrideRelationship
	extends AbstractOrmXmlContextNode
	implements OrmVirtualOverrideRelationship2_0
{
	protected OrmVirtualRelationshipStrategy strategy;

	protected final OrmVirtualJoinColumnRelationshipStrategy joinColumnStrategy;

	// JPA 2.0
	protected final OrmVirtualJoinTableRelationshipStrategy joinTableStrategy;


	public GenericOrmVirtualOverrideRelationship(OrmVirtualAssociationOverride parent) {
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

	public OrmVirtualRelationshipStrategy getStrategy() {
		return this.strategy;
	}

	protected void setStrategy(OrmVirtualRelationshipStrategy strategy) {
		OrmVirtualRelationshipStrategy old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, strategy);
	}

	protected OrmVirtualRelationshipStrategy buildStrategy() {
		if (this.isJpa2_0Compatible()) {
			if (this.joinColumnStrategy.hasSpecifiedJoinColumns()) {
				return this.joinColumnStrategy;
			}
			return this.joinTableStrategy;
		}
		return this.joinColumnStrategy;
	}


	// ********** join column strategy **********

	public OrmVirtualJoinColumnRelationshipStrategy getJoinColumnStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean strategyIsJoinColumn() {
		return this.strategy == this.joinColumnStrategy;
	}

	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}

	protected OrmVirtualJoinColumnRelationshipStrategy buildJoinColumnStrategy() {
		return new GenericOrmVirtualOverrideJoinColumnRelationshipStrategy(this);
	}


	// ********** join table strategy **********

	public OrmVirtualJoinTableRelationshipStrategy getJoinTableStrategy() {
		return this.joinTableStrategy;
	}

	public boolean strategyIsJoinTable() {
		return this.strategy == this.joinTableStrategy;
	}

	public boolean mayHaveDefaultJoinTable() {
		return this.isVirtual();
	}

	protected OrmVirtualJoinTableRelationshipStrategy buildJoinTableStrategy() {
		return new GenericOrmVirtualOverrideJoinTableRelationshipStrategy2_0(this);
	}


	// ********** conversions **********

	public void initializeOn(Relationship newRelationship) {
		newRelationship.initializeFromJoinTableRelationship(this);
		newRelationship.initializeFromJoinColumnRelationship(this);
	}

	public void initializeOnSpecified(OverrideRelationship specifiedRelationship) {
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

	public Relationship resolveOverriddenRelationship() {
		return this.getAssociationOverride().resolveOverriddenRelationship();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return null;
	}
}

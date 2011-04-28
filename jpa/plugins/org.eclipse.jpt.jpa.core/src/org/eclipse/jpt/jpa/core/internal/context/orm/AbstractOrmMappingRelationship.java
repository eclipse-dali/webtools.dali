/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedByRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappingRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmRelationshipStrategy;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlRelationshipMapping;

/**
 * <strong>NB:</strong> Subclasses will want to set the {@link #strategy} at the
 * end of their constructors; otherwise, it will be <code>null</code> until it
 * is set during {@link #update()}.
 */
public abstract class AbstractOrmMappingRelationship<M extends OrmRelationshipMapping>
	extends AbstractOrmXmlContextNode
	implements OrmMappingRelationship
{
	protected OrmRelationshipStrategy strategy;


	protected AbstractOrmMappingRelationship(M parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateStrategy();
	}


	// ********** strategy **********

	public OrmRelationshipStrategy getStrategy() {
		return this.strategy;
	}

	protected void setStrategy(OrmRelationshipStrategy strategy) {
		RelationshipStrategy old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, strategy);
	}

	protected abstract OrmRelationshipStrategy buildStrategy();

	/**
	 * This is called by subclasses when the various supported strategies are
	 * added or removed; allowing the strategy to be set synchronously. (?)
	 */
	protected void updateStrategy() {
		this.setStrategy(this.buildStrategy());
	}


	// ********** conversions **********

	public void initializeFrom(ReadOnlyRelationship oldRelationship) {
		oldRelationship.initializeOn(this);
	}

	public void initializeFromMappedByRelationship(MappedByRelationship oldRelationship) {
		// NOP
	}

	public void initializeFromJoinTableRelationship(ReadOnlyJoinTableRelationship oldRelationship) {
		// NOP
	}

	public void initializeFromJoinColumnRelationship(ReadOnlyJoinColumnRelationship oldRelationship) {
		// NOP
	}


	// ********** misc **********

	@Override
	@SuppressWarnings("unchecked")
	public M getParent() {
		return (M) super.getParent();
	}

	public M getMapping() {
		return this.getParent();
	}

	protected AbstractXmlRelationshipMapping getXmlMapping() {
		return this.getMapping().getXmlAttributeMapping();
	}

	public TypeMapping getTypeMapping() {
		return this.getMapping().getTypeMapping();
	}

	public Entity getEntity() {
		TypeMapping typeMapping = this.getTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	public boolean isOverridable() {
		return this.strategy.isOverridable();
	}

	public boolean isVirtual() {
		return false;
	}

	public boolean isTargetForeignKey() {
		return false;
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getMapping().getValidationTextRange();
	}
}

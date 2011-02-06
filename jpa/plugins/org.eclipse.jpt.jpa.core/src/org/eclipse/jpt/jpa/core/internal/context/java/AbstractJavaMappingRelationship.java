/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedByRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappingRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipStrategy;

/**
 * <strong>NB:</strong> Subclasses may want to set the {@link #strategy} at the
 * end of their constructors; otherwise, it will be <code>null</code> until it
 * is set during {@link #update()}.
 */
public abstract class AbstractJavaMappingRelationship<M extends JavaRelationshipMapping>
	extends AbstractJavaJpaContextNode
	implements JavaMappingRelationship
{
	protected JavaRelationshipStrategy strategy;


	public AbstractJavaMappingRelationship(M parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateStrategy();
	}


	// ********** strategy **********

	public JavaRelationshipStrategy getStrategy() {
		return this.strategy;
	}

	protected void setStrategy(JavaRelationshipStrategy strategy) {
		RelationshipStrategy old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, strategy);
	}

	protected abstract JavaRelationshipStrategy buildStrategy();

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

	public JavaRelationshipMapping getMapping() {
		return this.getParent();
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
		return this.getMapping().getPersistentAttribute().isVirtual();
	}

	public boolean isTargetForeignKey() {
		return false;
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getMapping().getValidationTextRange(astRoot);
	}
}

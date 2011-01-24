/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.OverrideRelationship;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.Relationship;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaVirtualJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaVirtualJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaVirtualJoiningStrategy;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaVirtualOverrideJoinTableJoiningStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaVirtualOverrideRelationship2_0;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaVirtualOverrideRelationship
	extends AbstractJavaJpaContextNode
	implements JavaVirtualOverrideRelationship2_0
{
	protected JavaVirtualJoiningStrategy strategy;

	protected final JavaVirtualJoinColumnJoiningStrategy joinColumnStrategy;

	// JPA 2.0
	protected final JavaVirtualJoinTableJoiningStrategy joinTableStrategy;


	public GenericJavaVirtualOverrideRelationship(JavaVirtualAssociationOverride parent) {
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

	public JavaVirtualJoiningStrategy getPredominantJoiningStrategy() {
		return this.strategy;
	}

	protected void setStrategy(JavaVirtualJoiningStrategy strategy) {
		JavaVirtualJoiningStrategy old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(PREDOMINANT_JOINING_STRATEGY_PROPERTY, old, strategy);
	}

	protected JavaVirtualJoiningStrategy buildStrategy() {
		if (this.isJpa2_0Compatible()) {
			if (this.joinColumnStrategy.hasSpecifiedJoinColumns()) {
				return this.joinColumnStrategy;
			}
			return this.joinTableStrategy;
		}
		return this.joinColumnStrategy;
	}


	// ********** join column strategy **********

	public JavaVirtualJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean usesJoinColumnJoiningStrategy() {
		return this.strategy == this.joinColumnStrategy;
	}

	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}

	protected JavaVirtualJoinColumnJoiningStrategy buildJoinColumnStrategy() {
		return new GenericJavaVirtualOverrideJoinColumnJoiningStrategy(this);
	}


	// ********** join table strategy **********

	public JavaVirtualJoinTableJoiningStrategy getJoinTableJoiningStrategy() {
		return this.joinTableStrategy;
	}

	public boolean usesJoinTableJoiningStrategy() {
		return this.strategy == this.joinTableStrategy;
	}

	public boolean mayHaveDefaultJoinTable() {
		return this.isVirtual();
	}

	protected JavaVirtualJoinTableJoiningStrategy buildJoinTableStrategy() {
		return new GenericJavaVirtualOverrideJoinTableJoiningStrategy2_0(this);
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
	public JavaVirtualAssociationOverride getParent() {
		return (JavaVirtualAssociationOverride) super.getParent();
	}

	public JavaVirtualAssociationOverride getAssociationOverride() {
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

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}
}

/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.OwnableRelationshipReference;
import org.eclipse.jpt.core.context.ReadOnlyAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.ReadOnlyRelationshipReference;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoiningStrategy;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.GenericJavaOverrideJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaOverrideJoinTableJoiningStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaAssociationOverrideRelationshipReference
	extends AbstractJavaJpaContextNode
	implements JavaAssociationOverrideRelationshipReference2_0
{
	protected JavaJoiningStrategy strategy;

	protected final JavaJoinColumnJoiningStrategy joinColumnStrategy;

	// JPA 2.0
	protected final JavaJoinTableJoiningStrategy joinTableStrategy;


	public GenericJavaAssociationOverrideRelationshipReference(JavaAssociationOverride parent) {
		super(parent);
		this.joinColumnStrategy = this.buildJoinColumnStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.joinColumnStrategy.synchronizeWithResourceModel();
		this.joinTableStrategy.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.setStrategy(this.buildStrategy());
		this.joinColumnStrategy.update();
		this.joinTableStrategy.update();
	}


	// ********** strategy **********

	public JavaJoiningStrategy getPredominantJoiningStrategy() {
		return this.strategy;
	}

	protected void setStrategy(JavaJoiningStrategy strategy) {
		JavaJoiningStrategy old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(PREDOMINANT_JOINING_STRATEGY_PROPERTY, old, strategy);
	}

	protected JavaJoiningStrategy buildStrategy() {
		if (this.isJpa2_0Compatible()) {
			if (this.joinColumnStrategy.hasSpecifiedJoinColumns()) {
				return this.joinColumnStrategy;
			}
			return this.joinTableStrategy;
		}
		return this.joinColumnStrategy;
	}


	// ********** join column strategy **********

	public JavaJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean usesJoinColumnJoiningStrategy() {
		return this.strategy == this.joinColumnStrategy;
	}

	public void setJoinColumnJoiningStrategy() {
		this.joinColumnStrategy.addStrategy();
		this.joinTableStrategy.removeStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}

	protected JavaJoinColumnJoiningStrategy buildJoinColumnStrategy() {
		return new GenericJavaOverrideJoinColumnJoiningStrategy(this);
	}


	// ********** join table strategy **********

	public JavaJoinTableJoiningStrategy getJoinTableJoiningStrategy() {
		return this.joinTableStrategy;
	}

	public boolean usesJoinTableJoiningStrategy() {
		return this.strategy == this.joinTableStrategy;
	}

	public void setJoinTableJoiningStrategy() {
		this.joinTableStrategy.addStrategy();
		this.joinColumnStrategy.removeStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return this.isVirtual() && this.usesJoinTableJoiningStrategy();
	}

	protected JavaJoinTableJoiningStrategy buildJoinTableStrategy() {
		return this.isJpa2_0Compatible() ?
				new GenericJavaOverrideJoinTableJoiningStrategy2_0(this) :
				new NullJavaJoinTableJoiningStrategy(this);
	}


	// ********** conversions **********

	public void initializeFrom(ReadOnlyRelationshipReference oldRelationship) {
		oldRelationship.initializeOn(this);
	}

	public void initializeOn(RelationshipReference newRelationship) {
		newRelationship.initializeFromJoinTableRelationship(this);
		newRelationship.initializeFromJoinColumnRelationship(this);
	}

	public void initializeFromMappedByRelationship(OwnableRelationshipReference oldRelationship) {
		// NOP
	}

	public void initializeFromJoinTableRelationship(ReadOnlyJoinTableEnabledRelationshipReference oldRelationship) {
		this.joinTableStrategy.initializeFrom(oldRelationship.getJoinTableJoiningStrategy());
	}

	public void initializeFromJoinColumnRelationship(ReadOnlyJoinColumnEnabledRelationshipReference oldRelationship) {
		this.joinColumnStrategy.initializeFrom(oldRelationship.getJoinColumnJoiningStrategy());
	}

	public void initializeFromVirtual(ReadOnlyAssociationOverrideRelationshipReference virtualRelationship) {
		virtualRelationship.initializeOnSpecified(this);
	}

	public void initializeOnSpecified(AssociationOverrideRelationshipReference specifiedRelationship) {
		throw new UnsupportedOperationException();
	}

	public void initializeFromVirtualJoinTableRelationship(ReadOnlyJoinTableEnabledRelationshipReference virtualRelationship) {
		this.joinTableStrategy.initializeFromVirtual(virtualRelationship.getJoinTableJoiningStrategy());
	}

	public void initializeFromVirtualJoinColumnRelationship(ReadOnlyJoinColumnEnabledRelationshipReference virtualRelationship) {
		this.joinColumnStrategy.initializeFromVirtual(virtualRelationship.getJoinColumnJoiningStrategy());
	}


	// ********** misc **********

	@Override
	public JavaAssociationOverride getParent() {
		return (JavaAssociationOverride) super.getParent();
	}

	public JavaAssociationOverride getAssociationOverride() {
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
		return false;
	}

	public RelationshipMapping getMapping() {
		return this.getAssociationOverride().getMapping();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.joinColumnStrategy.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		return this.joinTableStrategy.javaCompletionProposals(pos, filter, astRoot);
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getAssociationOverride().getValidationTextRange(astRoot);
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.joinColumnStrategy.validate(messages, reporter, astRoot);
		this.joinTableStrategy.validate(messages, reporter, astRoot);
	}
}

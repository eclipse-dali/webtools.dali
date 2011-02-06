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

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaMappingJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.NullJavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaManyToOneRelationship2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaManyToOneRelationship
	extends AbstractJavaMappingRelationship<JavaManyToOneMapping>
	implements JavaManyToOneRelationship2_0
{
	protected final JavaJoinColumnRelationshipStrategy joinColumnStrategy;

	// JPA 2.0
	protected final JavaJoinTableRelationshipStrategy joinTableStrategy;


	public GenericJavaManyToOneRelationship(JavaManyToOneMapping parent) {
		super(parent);
		this.joinColumnStrategy = this.buildJoinColumnStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();

		this.strategy = this.buildStrategy();
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
		this.joinColumnStrategy.update();
		this.joinTableStrategy.update();
	}


	// ********** strategy **********

	@Override
	protected JavaRelationshipStrategy buildStrategy() {
		if (this.isJpa2_0Compatible()) {
			if (this.joinTableStrategy.getJoinTable() != null) {
				return this.joinTableStrategy;
			}
		}
		return this.joinColumnStrategy;
	}


	// ********** join table strategy **********

	public JavaJoinTableRelationshipStrategy getJoinTableStrategy() {
		return this.joinTableStrategy;
	}

	public boolean strategyIsJoinTable() {
		return this.strategy == this.joinTableStrategy;
	}

	public final void setStrategyToJoinTable() {
		this.joinTableStrategy.addStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return false;
	}

	protected JavaJoinTableRelationshipStrategy buildJoinTableStrategy() {
		return this.isJpa2_0Compatible() ?
				new GenericJavaMappingJoinTableRelationshipStrategy(this) :
				new NullJavaJoinTableRelationshipStrategy(this);
	}


	// ********** join column strategy **********

	public JavaJoinColumnRelationshipStrategy getJoinColumnStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean strategyIsJoinColumn() {
		return this.strategy == this.joinColumnStrategy;
	}

	public void setStrategyToJoinColumn() {
		// join column strategy is the default; so no need to add stuff,
		// just remove all the others
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return this.joinTableStrategy.getJoinTable() == null;
	}

	protected JavaJoinColumnRelationshipStrategy buildJoinColumnStrategy() {
		return new GenericJavaMappingJoinColumnRelationshipStrategy(this);
	}


	// ********** conversions **********

	public void initializeOn(Relationship newRelationship) {
		newRelationship.initializeFromJoinColumnRelationship(this);
		newRelationship.initializeFromJoinTableRelationship(this);
	}

	@Override
	public void initializeFromJoinTableRelationship(ReadOnlyJoinTableRelationship oldRelationship) {
		super.initializeFromJoinTableRelationship(oldRelationship);
		this.joinTableStrategy.initializeFrom(oldRelationship.getJoinTableStrategy());
	}

	@Override
	public void initializeFromJoinColumnRelationship(ReadOnlyJoinColumnRelationship oldRelationship) {
		super.initializeFromJoinColumnRelationship(oldRelationship);
		this.joinColumnStrategy.initializeFrom(oldRelationship.getJoinColumnStrategy());
	}


	// ********** misc **********

	@Override
	public JavaManyToOneMapping getMapping() {
		return this.getParent();
	}

	public boolean isOwner() {
		return true;
	}

	public boolean isOwnedBy(RelationshipMapping mapping) {
		return false;
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

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.joinColumnStrategy.validate(messages, reporter, astRoot);
		this.joinTableStrategy.validate(messages, reporter, astRoot);
	}
}

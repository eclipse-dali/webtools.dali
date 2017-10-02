/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaMappingJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.NullJavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaManyToOneRelationship2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaManyToOneRelationship
	extends AbstractJavaMappingRelationship<JavaManyToOneMapping>
	implements JavaManyToOneRelationship2_0
{
	protected boolean strategyIsJoinColumn;
	protected boolean strategyIsJoinTable;

	protected final JavaSpecifiedJoinColumnRelationshipStrategy joinColumnStrategy;

	// JPA 2.0
	protected final JavaSpecifiedJoinTableRelationshipStrategy joinTableStrategy;


	public GenericJavaManyToOneRelationship(JavaManyToOneMapping parent) {
		super(parent);
		this.joinColumnStrategy = this.buildJoinColumnStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();

		this.strategy = this.buildStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.joinColumnStrategy.synchronizeWithResourceModel(monitor);
		this.joinTableStrategy.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setStrategyIsJoinColumn(this.buildStrategyIsJoinColumn());
		this.setStrategyIsJoinTable(this.buildStrategyIsJoinTable());
		this.joinColumnStrategy.update(monitor);
		this.joinTableStrategy.update(monitor);
	}


	// ********** strategy **********

	@Override
	protected SpecifiedRelationshipStrategy buildStrategy() {
		if (this.isJpa2_0Compatible()) {
			if (this.joinTableStrategy.getJoinTable() != null) {
				return this.joinTableStrategy;
			}
		}
		return this.joinColumnStrategy;
	}


	// ********** join column strategy **********

	public JavaSpecifiedJoinColumnRelationshipStrategy getJoinColumnStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean strategyIsJoinColumn() {
		return this.strategyIsJoinColumn;
	}

	protected void setStrategyIsJoinColumn(boolean strategyIsJoinColumn) {
		boolean old = this.strategyIsJoinColumn;
		this.firePropertyChanged(STRATEGY_IS_JOIN_COLUMN_PROPERTY, old, this.strategyIsJoinColumn = strategyIsJoinColumn);
	}

	protected boolean buildStrategyIsJoinColumn() {
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

	protected JavaSpecifiedJoinColumnRelationshipStrategy buildJoinColumnStrategy() {
		return new GenericJavaMappingJoinColumnRelationshipStrategy(this);
	}


	// ********** join table strategy **********

	public JavaSpecifiedJoinTableRelationshipStrategy getJoinTableStrategy() {
		return this.joinTableStrategy;
	}

	public boolean strategyIsJoinTable() {
		return this.strategyIsJoinTable;
	}

	protected void setStrategyIsJoinTable(boolean strategyIsJoinTable) {
		boolean old = this.strategyIsJoinTable;
		this.firePropertyChanged(STRATEGY_IS_JOIN_TABLE_PROPERTY, old, this.strategyIsJoinTable = strategyIsJoinTable);
	}

	protected boolean buildStrategyIsJoinTable() {
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

	protected JavaSpecifiedJoinTableRelationshipStrategy buildJoinTableStrategy() {
		return this.isJpa2_0Compatible() ?
				new GenericJavaMappingJoinTableRelationshipStrategy(this) :
				new NullJavaJoinTableRelationshipStrategy(this);
	}


	// ********** misc **********

	public boolean isOwner() {
		return true;
	}

	public boolean isOwnedBy(RelationshipMapping mapping) {
		return false;
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		result = this.joinColumnStrategy.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		return this.joinTableStrategy.getCompletionProposals(pos);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.joinColumnStrategy.validate(messages, reporter);
		this.joinTableStrategy.validate(messages, reporter);
	}
}

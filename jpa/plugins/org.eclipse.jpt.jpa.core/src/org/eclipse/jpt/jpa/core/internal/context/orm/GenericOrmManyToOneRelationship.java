/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappingRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmManyToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManyToOne;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmManyToOneRelationship
	extends AbstractOrmMappingRelationship<OrmManyToOneMapping>
	implements OrmManyToOneRelationship2_0
{
	protected boolean strategyIsJoinColumn;
	protected boolean strategyIsJoinTable;

	protected final OrmSpecifiedJoinColumnRelationshipStrategy joinColumnStrategy;

	// JPA 2.0
	protected final OrmSpecifiedJoinTableRelationshipStrategy joinTableStrategy;

	
	public GenericOrmManyToOneRelationship(OrmManyToOneMapping parent) {
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
			if (this.joinTableStrategy.getJoinTable() != null){
				return this.joinTableStrategy;
			}
		}
		return this.joinColumnStrategy;
	}


	// ********** join column strategy **********

	public OrmSpecifiedJoinColumnRelationshipStrategy getJoinColumnStrategy() {
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

	protected OrmSpecifiedJoinColumnRelationshipStrategy buildJoinColumnStrategy() {
		return new GenericOrmMappingJoinColumnRelationshipStrategy(this);
	}


	// ********** join table strategy **********

	public OrmSpecifiedJoinTableRelationshipStrategy getJoinTableStrategy() {
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

	public void setStrategyToJoinTable() {
		this.joinTableStrategy.addStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return false;
	}

	protected OrmSpecifiedJoinTableRelationshipStrategy buildJoinTableStrategy() {
		return this.isJpa2_0Compatible() ?
				new GenericOrmMappingJoinTableRelationshipStrategy(this) :
				new NullOrmJoinTableRelationshipStrategy(this);
	}


	// ********** conversions **********

	public void initializeOn(OrmMappingRelationship newRelationship) {
		newRelationship.initializeFromJoinColumnRelationship(this);
		newRelationship.initializeFromJoinTableRelationship(this);
	}

	@Override
	public void initializeFromJoinTableRelationship(OrmJoinTableRelationship oldRelationship) {
		super.initializeFromJoinTableRelationship(oldRelationship);
		this.joinTableStrategy.initializeFrom(oldRelationship.getJoinTableStrategy());
	}

	@Override
	public void initializeFromJoinColumnRelationship(OrmJoinColumnRelationship oldRelationship) {
		super.initializeFromJoinColumnRelationship(oldRelationship);
		this.joinColumnStrategy.initializeFrom(oldRelationship.getJoinColumnStrategy());
	}


	// ********** misc **********

	@Override
	protected XmlManyToOne getXmlMapping() {
		return (XmlManyToOne) super.getXmlMapping();
	}

	public XmlManyToOne getXmlContainer() {
		return this.getXmlMapping();
	}

	public boolean isOwner() {
		return true;
	}

	public boolean isOwnedBy(RelationshipMapping mapping) {
		return false;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.joinColumnStrategy.validate(messages, reporter);
		this.joinTableStrategy.validate(messages, reporter);
	}

	// ********** completion proposals **********

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
		result = this.joinTableStrategy.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}
}

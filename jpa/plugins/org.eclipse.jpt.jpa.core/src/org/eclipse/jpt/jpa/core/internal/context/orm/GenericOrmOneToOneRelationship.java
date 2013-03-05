/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedMappedByRelationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedMappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOneToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmOneToOneRelationship
	extends AbstractOrmMappingRelationship<OrmOneToOneMapping>
	implements OrmOneToOneRelationship2_0
{
	protected final SpecifiedMappedByRelationshipStrategy mappedByStrategy;

	protected final OrmSpecifiedPrimaryKeyJoinColumnRelationshipStrategy primaryKeyJoinColumnStrategy;

	// JPA 2.0
	protected final OrmSpecifiedJoinTableRelationshipStrategy joinTableStrategy;

	protected final OrmSpecifiedJoinColumnRelationshipStrategy joinColumnStrategy;


	public GenericOrmOneToOneRelationship(OrmOneToOneMapping parent) {
		super(parent);
		this.mappedByStrategy = this.buildMappedByStrategy();
		this.primaryKeyJoinColumnStrategy = this.buildPrimaryKeyJoinColumnStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();
		this.joinColumnStrategy = this.buildJoinColumnStrategy();

		this.strategy = this.buildStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.mappedByStrategy.synchronizeWithResourceModel();
		this.primaryKeyJoinColumnStrategy.synchronizeWithResourceModel();
		this.joinTableStrategy.synchronizeWithResourceModel();
		this.joinColumnStrategy.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.mappedByStrategy.update();
		this.primaryKeyJoinColumnStrategy.update();
		this.joinTableStrategy.update();
		this.joinColumnStrategy.update();
	}


	// ********** strategy **********

	@Override
	protected SpecifiedRelationshipStrategy buildStrategy() {
		if (this.mappedByStrategy.getMappedByAttribute() != null) {
			return this.mappedByStrategy;
		}
		if (this.primaryKeyJoinColumnStrategy.hasPrimaryKeyJoinColumns()) {
			return this.primaryKeyJoinColumnStrategy;
		}
		if (this.isJpa2_0Compatible()) {
			if (this.joinTableStrategy.getJoinTable() != null){
				return this.joinTableStrategy;
			}
		}
		return this.joinColumnStrategy;
	}


	// ********** mapped by strategy **********

	public SpecifiedMappedByRelationshipStrategy getMappedByStrategy() {
		return this.mappedByStrategy;
	}

	public boolean strategyIsMappedBy() {
		return this.strategy == this.mappedByStrategy;
	}

	public void setStrategyToMappedBy() {
		this.mappedByStrategy.addStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayBeMappedBy(AttributeMapping mapping) {
		return mapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	protected SpecifiedMappedByRelationshipStrategy buildMappedByStrategy() {
		return new GenericOrmMappedByRelationshipStrategy(this);
	}


	// ********** primary key join column strategy **********

	public OrmSpecifiedPrimaryKeyJoinColumnRelationshipStrategy getPrimaryKeyJoinColumnStrategy() {
		return this.primaryKeyJoinColumnStrategy;
	}

	public boolean strategyIsPrimaryKeyJoinColumn() {
		return this.strategy == this.primaryKeyJoinColumnStrategy;
	}

	public void setStrategyToPrimaryKeyJoinColumn() {
		this.primaryKeyJoinColumnStrategy.addStrategy();
		this.mappedByStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	protected OrmSpecifiedPrimaryKeyJoinColumnRelationshipStrategy buildPrimaryKeyJoinColumnStrategy() {
		return new GenericOrmPrimaryKeyJoinColumnRelationshipStrategy(this);
	}


	// ********** join table strategy **********

	public OrmSpecifiedJoinTableRelationshipStrategy getJoinTableStrategy() {
		return this.joinTableStrategy;
	}

	public boolean strategyIsJoinTable() {
		return this.strategy == this.joinTableStrategy;
	}

	public void setStrategyToJoinTable() {
		this.joinTableStrategy.addStrategy();
		this.mappedByStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
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


	// ********** join column strategy **********

	public OrmSpecifiedJoinColumnRelationshipStrategy getJoinColumnStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean strategyIsJoinColumn() {
		return this.strategy == this.joinColumnStrategy;
	}

	public void setStrategyToJoinColumn() {
		// join column strategy is the default; so no need to add stuff,
		// just remove all the others
		this.mappedByStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return (this.mappedByStrategy.getMappedByAttribute() == null) &&
				(this.primaryKeyJoinColumnStrategy.getPrimaryKeyJoinColumnsSize() == 0) &&
				(this.joinTableStrategy.getJoinTable() == null);
	}

	protected OrmSpecifiedJoinColumnRelationshipStrategy buildJoinColumnStrategy() {
		return new GenericOrmMappingJoinColumnRelationshipStrategy(this);
	}


	// ********** conversions **********

	public void initializeOn(SpecifiedRelationship newRelationship) {
		newRelationship.initializeFromMappedByRelationship(this);
		newRelationship.initializeFromJoinTableRelationship(this);
		newRelationship.initializeFromJoinColumnRelationship(this);
		// no other pk join column relationships yet
	}

	@Override
	public void initializeFromMappedByRelationship(SpecifiedMappedByRelationship oldRelationship) {
		super.initializeFromMappedByRelationship(oldRelationship);
		this.mappedByStrategy.initializeFrom(oldRelationship.getMappedByStrategy());
	}

	@Override
	public void initializeFromJoinTableRelationship(ReadOnlyJoinTableRelationship oldRelationship) {
		super.initializeFromJoinTableRelationship(oldRelationship);
		this.joinTableStrategy.initializeFrom(oldRelationship.getJoinTableStrategy());
	}

	@Override
	public void initializeFromJoinColumnRelationship(JoinColumnRelationship oldRelationship) {
		super.initializeFromJoinColumnRelationship(oldRelationship);
		this.joinColumnStrategy.initializeFrom(oldRelationship.getJoinColumnStrategy());
	}


	// ********** misc **********

	@Override
	protected XmlOneToOne getXmlMapping() {
		return (XmlOneToOne) super.getXmlMapping();
	}

	public XmlOneToOne getXmlContainer() {
		return this.getXmlMapping();
	}

	public boolean isOwner() {
		return this.mappedByStrategy.getMappedByAttribute() == null;
	}

	public boolean isOwnedBy(RelationshipMapping mapping) {
		return this.mappedByStrategy.relationshipIsOwnedBy(mapping);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.mappedByStrategy.validate(messages, reporter);
		this.primaryKeyJoinColumnStrategy.validate(messages, reporter);
		this.joinTableStrategy.validate(messages, reporter);
		this.joinColumnStrategy.validate(messages, reporter);
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.mappedByStrategy.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.primaryKeyJoinColumnStrategy.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.joinTableStrategy.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.joinColumnStrategy.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}
}

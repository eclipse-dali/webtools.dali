/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.MappedByRelationship;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.Relationship;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmMappedByJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneRelationship2_0;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmOneToOneRelationship
	extends AbstractOrmMappingRelationship<OrmOneToOneMapping>
	implements OrmOneToOneRelationship2_0
{
	protected final OrmMappedByJoiningStrategy mappedByStrategy;

	protected final OrmPrimaryKeyJoinColumnJoiningStrategy primaryKeyJoinColumnStrategy;

	// JPA 2.0
	protected final OrmJoinTableJoiningStrategy joinTableStrategy;

	protected final OrmJoinColumnJoiningStrategy joinColumnStrategy;


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
	protected OrmJoiningStrategy buildStrategy() {
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

	public OrmMappedByJoiningStrategy getMappedByJoiningStrategy() {
		return this.mappedByStrategy;
	}

	public boolean usesMappedByJoiningStrategy() {
		return this.strategy == this.mappedByStrategy;
	}

	public void setMappedByJoiningStrategy() {
		this.mappedByStrategy.addStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return mappedByMapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	protected OrmMappedByJoiningStrategy buildMappedByStrategy() {
		return new GenericOrmMappedByJoiningStrategy(this);
	}


	// ********** primary key join column strategy **********

	public OrmPrimaryKeyJoinColumnJoiningStrategy getPrimaryKeyJoinColumnJoiningStrategy() {
		return this.primaryKeyJoinColumnStrategy;
	}

	public boolean usesPrimaryKeyJoinColumnJoiningStrategy() {
		return this.strategy == this.primaryKeyJoinColumnStrategy;
	}

	public void setPrimaryKeyJoinColumnJoiningStrategy() {
		this.primaryKeyJoinColumnStrategy.addStrategy();
		this.mappedByStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	protected OrmPrimaryKeyJoinColumnJoiningStrategy buildPrimaryKeyJoinColumnStrategy() {
		return new GenericOrmPrimaryKeyJoinColumnJoiningStrategy(this);
	}


	// ********** join table strategy **********

	public OrmJoinTableJoiningStrategy getJoinTableJoiningStrategy() {
		return this.joinTableStrategy;
	}

	public boolean usesJoinTableJoiningStrategy() {
		return this.strategy == this.joinTableStrategy;
	}

	public void setJoinTableJoiningStrategy() {
		this.joinTableStrategy.addStrategy();
		this.mappedByStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return false;
	}

	protected OrmJoinTableJoiningStrategy buildJoinTableStrategy() {
		return this.isJpa2_0Compatible() ?
				new GenericOrmMappingJoinTableJoiningStrategy(this) :
				new NullOrmJoinTableJoiningStrategy(this);
	}


	// ********** join column strategy **********

	public OrmJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean usesJoinColumnJoiningStrategy() {
		return this.strategy == this.joinColumnStrategy;
	}

	public void setJoinColumnJoiningStrategy() {
		// join column strategy is the default; so no need to add stuff,
		// just remove all the others
		this.mappedByStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return (this.mappedByStrategy.getMappedByAttribute() == null) &&
				(this.primaryKeyJoinColumnStrategy.primaryKeyJoinColumnsSize() == 0) &&
				(this.joinTableStrategy.getJoinTable() == null);
	}

	protected OrmJoinColumnJoiningStrategy buildJoinColumnStrategy() {
		return new GenericOrmMappingJoinColumnJoiningStrategy(this);
	}


	// ********** conversions **********

	public void initializeOn(Relationship newRelationship) {
		newRelationship.initializeFromMappedByRelationship(this);
		newRelationship.initializeFromJoinTableRelationship(this);
		newRelationship.initializeFromJoinColumnRelationship(this);
		// no other pk join column relationships yet
	}

	@Override
	public void initializeFromMappedByRelationship(MappedByRelationship oldRelationship) {
		super.initializeFromMappedByRelationship(oldRelationship);
		this.mappedByStrategy.initializeFrom(oldRelationship.getMappedByJoiningStrategy());
	}

	@Override
	public void initializeFromJoinTableRelationship(ReadOnlyJoinTableRelationship oldRelationship) {
		super.initializeFromJoinTableRelationship(oldRelationship);
		this.joinTableStrategy.initializeFrom(oldRelationship.getJoinTableJoiningStrategy());
	}

	@Override
	public void initializeFromJoinColumnRelationship(ReadOnlyJoinColumnRelationship oldRelationship) {
		super.initializeFromJoinColumnRelationship(oldRelationship);
		this.joinColumnStrategy.initializeFrom(oldRelationship.getJoinColumnJoiningStrategy());
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
}

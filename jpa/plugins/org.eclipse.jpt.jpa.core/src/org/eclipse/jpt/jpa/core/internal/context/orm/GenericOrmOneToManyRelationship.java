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

import java.util.List;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.MappedByRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.NullOrmJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmMappingJoinColumnRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmMappingJoinTableRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmMappingMappedByRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmMappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOneToManyRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmOneToManyRelationship
	extends AbstractOrmMappingRelationship<OrmOneToManyMapping>
	implements OrmOneToManyRelationship2_0
{
	protected final OrmMappingMappedByRelationshipStrategy2_0 mappedByStrategy;

	protected final OrmMappingJoinTableRelationshipStrategy2_0 joinTableStrategy;

	// JPA 2.0 or EclipseLink
	protected final boolean supportsJoinColumnStrategy;
	protected final OrmMappingJoinColumnRelationshipStrategy2_0 joinColumnStrategy;


	public GenericOrmOneToManyRelationship(OrmOneToManyMapping parent, boolean supportsJoinColumnStrategy) {
		super(parent);
		this.mappedByStrategy = this.buildMappedByStrategy();
		this.supportsJoinColumnStrategy = supportsJoinColumnStrategy;
		this.joinColumnStrategy = this.buildJoinColumnStrategy();

		// build join table strategy last since it's dependent on the other strategies
		this.joinTableStrategy = this.buildJoinTableStrategy();

		this.strategy = this.buildStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.mappedByStrategy.synchronizeWithResourceModel();
		this.joinColumnStrategy.synchronizeWithResourceModel();
		this.joinTableStrategy.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.mappedByStrategy.update();
		this.joinColumnStrategy.update();
		this.joinTableStrategy.update();
	}


	// ********** strategy **********

	@Override
	protected OrmMappingRelationshipStrategy2_0 buildStrategy() {
		if (this.mappedByStrategy.getMappedByAttribute() != null) {
			return this.mappedByStrategy;
		}
		if (this.supportsJoinColumnStrategy) {
			if (this.joinColumnStrategy.hasSpecifiedJoinColumns()) {
				return this.joinColumnStrategy;
			}
		}
		return this.joinTableStrategy;
	}


	// ********** mapped by strategy **********

	public OrmMappedByRelationshipStrategy getMappedByStrategy() {
		return this.mappedByStrategy;
	}

	public boolean strategyIsMappedBy() {
		return this.strategy == this.mappedByStrategy;
	}

	public final void setStrategyToMappedBy() {
		this.mappedByStrategy.addStrategy();
		this.joinTableStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayBeMappedBy(AttributeMapping mapping) {
		String key = mapping.getKey();
		if (key == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return true;
		}
		if (this.supportsJoinColumnStrategy) {
			return key == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
		}
		return false;
	}

	protected OrmMappingMappedByRelationshipStrategy2_0 buildMappedByStrategy() {
		return new GenericOrmMappedByRelationshipStrategy(this);
	}


	// ********** join table strategy **********

	public OrmJoinTableRelationshipStrategy getJoinTableStrategy() {
		return this.joinTableStrategy;
	}

	public boolean strategyIsJoinTable() {
		return this.strategy == this.joinTableStrategy;
	}

	public final void setStrategyToJoinTable() {
		// join table is default, so no need to add to resource
		this.mappedByStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return (this.mappedByStrategy.getMappedByAttribute() == null) &&
				! this.joinColumnStrategy.hasSpecifiedJoinColumns();
	}

	protected OrmMappingJoinTableRelationshipStrategy2_0 buildJoinTableStrategy() {
		return new GenericOrmMappingJoinTableRelationshipStrategy(this);
	}


	// ********** join column strategy **********

	public OrmJoinColumnRelationshipStrategy getJoinColumnStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean strategyIsJoinColumn() {
		return this.strategy == this.joinColumnStrategy;
	}

	public void setStrategyToJoinColumn() {
		this.joinColumnStrategy.addStrategy();
		this.mappedByStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}

	protected OrmMappingJoinColumnRelationshipStrategy2_0 buildJoinColumnStrategy() {
		return this.supportsJoinColumnStrategy ?
				new GenericOrmMappingJoinColumnRelationshipStrategy(this, true) :  // true = target foreign key
				new NullOrmJoinColumnRelationshipStrategy(this);
	}


	// ********** conversions **********

	public void initializeOn(Relationship newRelationship) {
		newRelationship.initializeFromMappedByRelationship(this);
		newRelationship.initializeFromJoinTableRelationship(this);
		newRelationship.initializeFromJoinColumnRelationship(this);
	}

	@Override
	public void initializeFromMappedByRelationship(MappedByRelationship oldRelationship) {
		super.initializeFromMappedByRelationship(oldRelationship);
		this.mappedByStrategy.initializeFrom(oldRelationship.getMappedByStrategy());
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
	protected XmlOneToMany getXmlMapping() {
		return (XmlOneToMany) super.getXmlMapping();
	}

	public XmlOneToMany getXmlContainer() {
		return this.getXmlMapping();
	}

	public boolean isOwner() {
		return this.mappedByStrategy.getMappedByAttribute() == null;
	}

	public boolean isOwnedBy(RelationshipMapping mapping) {
		return this.mappedByStrategy.relationshipIsOwnedBy(mapping);
	}

	@Override
	public boolean isTargetForeignKey() {
		return this.joinColumnStrategy.isTargetForeignKey();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.mappedByStrategy.validate(messages, reporter);
		this.joinTableStrategy.validate(messages, reporter);
		this.joinColumnStrategy.validate(messages, reporter);
	}
}

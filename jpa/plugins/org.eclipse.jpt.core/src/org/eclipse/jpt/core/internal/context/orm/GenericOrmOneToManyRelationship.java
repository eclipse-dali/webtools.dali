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
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.NullOrmJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToManyRelationship2_0;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmOneToManyRelationship
	extends AbstractOrmMappingRelationship<OrmOneToManyMapping>
	implements OrmOneToManyRelationship2_0
{
	protected final OrmMappedByJoiningStrategy mappedByStrategy;

	protected final OrmJoinTableJoiningStrategy joinTableStrategy;

	// JPA 2.0 or EclipseLink
	protected final boolean supportsJoinColumnStrategy;
	protected final OrmJoinColumnJoiningStrategy joinColumnStrategy;


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
	protected OrmJoiningStrategy buildStrategy() {
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

	public OrmMappedByJoiningStrategy getMappedByJoiningStrategy() {
		return this.mappedByStrategy;
	}

	public boolean usesMappedByJoiningStrategy() {
		return this.strategy == this.mappedByStrategy;
	}

	public final void setMappedByJoiningStrategy() {
		this.mappedByStrategy.addStrategy();
		this.joinTableStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		String key = mappedByMapping.getKey();
		if (key == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return true;
		}
		if (this.supportsJoinColumnStrategy) {
			return key == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
		}
		return false;
	}

	protected OrmMappedByJoiningStrategy buildMappedByStrategy() {
		return new GenericOrmMappedByJoiningStrategy(this);
	}


	// ********** join table strategy **********

	public OrmJoinTableJoiningStrategy getJoinTableJoiningStrategy() {
		return this.joinTableStrategy;
	}

	public boolean usesJoinTableJoiningStrategy() {
		return this.strategy == this.joinTableStrategy;
	}

	public final void setJoinTableJoiningStrategy() {
		// join table is default, so no need to add to resource
		this.mappedByStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return (this.mappedByStrategy.getMappedByAttribute() == null) &&
				! this.joinColumnStrategy.hasSpecifiedJoinColumns();
	}

	protected OrmJoinTableJoiningStrategy buildJoinTableStrategy() {
		return new GenericOrmMappingJoinTableJoiningStrategy(this);
	}


	// ********** join column strategy **********

	public OrmJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean usesJoinColumnJoiningStrategy() {
		return this.strategy == this.joinColumnStrategy;
	}

	public void setJoinColumnJoiningStrategy() {
		this.joinColumnStrategy.addStrategy();
		this.mappedByStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}

	protected OrmJoinColumnJoiningStrategy buildJoinColumnStrategy() {
		return this.supportsJoinColumnStrategy ?
				new GenericOrmMappingJoinColumnJoiningStrategy(this, true) :  // true = target foreign key
				new NullOrmJoinColumnJoiningStrategy(this);
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

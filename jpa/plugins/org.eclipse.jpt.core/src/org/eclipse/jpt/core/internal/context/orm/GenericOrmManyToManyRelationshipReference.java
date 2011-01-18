/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.OwnableRelationshipReference;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmMappedByJoiningStrategy;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmManyToManyRelationshipReference
	extends AbstractOrmMappingRelationship<OrmManyToManyMapping>
	implements OrmManyToManyRelationshipReference
{
	protected final OrmMappedByJoiningStrategy mappedByStrategy;

	protected final OrmJoinTableJoiningStrategy joinTableStrategy;


	public GenericOrmManyToManyRelationshipReference(OrmManyToManyMapping parent) {
		super(parent);
		this.mappedByStrategy = this.buildMappedByStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();

		this.strategy = this.buildStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.mappedByStrategy.synchronizeWithResourceModel();
		this.joinTableStrategy.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.mappedByStrategy.update();
		this.joinTableStrategy.update();
	}


	// ********** strategy **********

	@Override
	protected OrmJoiningStrategy buildStrategy() {
		if (this.mappedByStrategy.getMappedByAttribute() != null) {
			return this.mappedByStrategy;
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

	public void setMappedByJoiningStrategy() {
		this.mappedByStrategy.addStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return mappedByMapping.getKey() == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
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

	public void setJoinTableJoiningStrategy() {
		// join table is the default strategy, so no need to add to resource
		this.mappedByStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return this.mappedByStrategy.getMappedByAttribute() == null;
	}

	protected OrmJoinTableJoiningStrategy buildJoinTableStrategy() {
		return new GenericOrmMappingJoinTableJoiningStrategy(this);
	}


	// ********** conversions **********

	public void initializeOn(RelationshipReference newRelationship) {
		newRelationship.initializeFromMappedByRelationship(this);
		newRelationship.initializeFromJoinTableRelationship(this);
	}

	@Override
	public void initializeFromMappedByRelationship(OwnableRelationshipReference oldRelationship) {
		super.initializeFromMappedByRelationship(oldRelationship);
		this.mappedByStrategy.initializeFrom(oldRelationship.getMappedByJoiningStrategy());
	}

	@Override
	public void initializeFromJoinTableRelationship(ReadOnlyJoinTableEnabledRelationshipReference oldRelationship) {
		super.initializeFromJoinTableRelationship(oldRelationship);
		this.joinTableStrategy.initializeFrom(oldRelationship.getJoinTableJoiningStrategy());
	}


	// ********** misc **********

	@Override
	protected XmlManyToMany getXmlMapping() {
		return (XmlManyToMany) super.getXmlMapping();
	}

	public XmlManyToMany getXmlContainer() {
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
		this.joinTableStrategy.validate(messages, reporter);
	}
}

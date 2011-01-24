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
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.Relationship;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.jpa2.context.orm.OrmManyToOneRelationship2_0;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmManyToOneRelationship
	extends AbstractOrmMappingRelationship<OrmManyToOneMapping>
	implements OrmManyToOneRelationship2_0
{
	protected final OrmJoinColumnJoiningStrategy joinColumnStrategy;

	// JPA 2.0
	protected final OrmJoinTableJoiningStrategy joinTableStrategy;

	
	public GenericOrmManyToOneRelationship(OrmManyToOneMapping parent) {
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
	protected OrmJoiningStrategy buildStrategy() {
		if (this.isJpa2_0Compatible()) {
			if (this.joinTableStrategy.getJoinTable() != null){
				return this.joinTableStrategy;
			}
		}
		return this.joinColumnStrategy;
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
		this.joinColumnStrategy.removeStrategy();
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
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return this.joinTableStrategy.getJoinTable() == null;
	}

	protected OrmJoinColumnJoiningStrategy buildJoinColumnStrategy() {
		return new GenericOrmMappingJoinColumnJoiningStrategy(this);
	}


	// ********** conversions **********

	public void initializeOn(Relationship newRelationship) {
		newRelationship.initializeFromJoinColumnRelationship(this);
		newRelationship.initializeFromJoinTableRelationship(this);
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
}

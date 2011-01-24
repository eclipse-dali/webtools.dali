/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.OverrideRelationship;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.MappedByRelationship;
import org.eclipse.jpt.core.context.ReadOnlyOverrideRelationship;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.Relationship;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoiningStrategy;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmOverrideJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.internal.context.orm.NullOrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmOverrideJoinTableJoiningStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOverrideRelationship2_0;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmOverrideRelationship
	extends AbstractOrmXmlContextNode
	implements OrmOverrideRelationship2_0
{
	protected OrmJoiningStrategy strategy;

	protected final OrmJoinColumnJoiningStrategy joinColumnStrategy;

	// JPA 2.0
	protected final OrmJoinTableJoiningStrategy joinTableStrategy;


	public GenericOrmOverrideRelationship(OrmAssociationOverride parent) {
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

	public OrmJoiningStrategy getPredominantJoiningStrategy() {
		return this.strategy;
	}

	protected void setStrategy(OrmJoiningStrategy strategy) {
		OrmJoiningStrategy old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(PREDOMINANT_JOINING_STRATEGY_PROPERTY, old, strategy);
	}

	protected OrmJoiningStrategy buildStrategy() {
		if (this.isJpa2_0Compatible()) {
			if (this.joinColumnStrategy.hasSpecifiedJoinColumns()) {
				return this.joinColumnStrategy;
			}
			return this.joinTableStrategy;
		}
		return this.joinColumnStrategy;
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
		this.joinTableStrategy.removeStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}

	protected OrmJoinColumnJoiningStrategy buildJoinColumnStrategy() {
		return new GenericOrmOverrideJoinColumnJoiningStrategy(this);
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
	}

	public boolean mayHaveDefaultJoinTable() {
		return false;
	}

	protected OrmJoinTableJoiningStrategy buildJoinTableStrategy() {
		return this.isJpa2_0Compatible() ?
				new GenericOrmOverrideJoinTableJoiningStrategy2_0(this) :
				new NullOrmJoinTableJoiningStrategy(this);
	}


	// ********** conversions **********

	public void initializeFrom(ReadOnlyRelationship oldRelationship) {
		oldRelationship.initializeOn(this);
	}

	public void initializeOn(Relationship newRelationship) {
		newRelationship.initializeFromJoinTableRelationship(this);
		newRelationship.initializeFromJoinColumnRelationship(this);
	}

	public void initializeFromMappedByRelationship(MappedByRelationship oldRelationship) {
		// NOP
	}

	public void initializeFromJoinTableRelationship(ReadOnlyJoinTableRelationship oldRelationship) {
		this.joinTableStrategy.initializeFrom(oldRelationship.getJoinTableJoiningStrategy());
	}

	public void initializeFromJoinColumnRelationship(ReadOnlyJoinColumnRelationship oldRelationship) {
		this.joinColumnStrategy.initializeFrom(oldRelationship.getJoinColumnJoiningStrategy());
	}

	public void initializeFromVirtual(ReadOnlyOverrideRelationship virtualRelationship) {
		virtualRelationship.initializeOnSpecified(this);
	}

	public void initializeOnSpecified(OverrideRelationship specifiedRelationship) {
		throw new UnsupportedOperationException();
	}

	public void initializeFromVirtualJoinTableRelationship(ReadOnlyJoinTableRelationship virtualRelationship) {
		this.joinTableStrategy.initializeFromVirtual(virtualRelationship.getJoinTableJoiningStrategy());
	}

	public void initializeFromVirtualJoinColumnRelationship(ReadOnlyJoinColumnRelationship virtualRelationship) {
		this.joinColumnStrategy.initializeFromVirtual(virtualRelationship.getJoinColumnJoiningStrategy());
	}


	// ********** misc **********

	@Override
	public OrmAssociationOverride getParent() {
		return (OrmAssociationOverride) super.getParent();
	}

	public OrmAssociationOverride getAssociationOverride() {
		return this.getParent();
	}

	public XmlAssociationOverride getXmlContainer() {
		return this.getAssociationOverride().getXmlOverride();
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


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getAssociationOverride().getValidationTextRange();
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.joinColumnStrategy.validate(messages, reporter);
		this.joinTableStrategy.validate(messages, reporter);
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getAssociationOverride().getContainer().buildJoinTableJoinColumnValidator(this.getAssociationOverride(), column, owner, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getAssociationOverride().getContainer().buildJoinTableInverseJoinColumnValidator(this.getAssociationOverride(), column, owner, textRangeResolver);
	}

	public JptValidator buildTableValidator(Table table, TableTextRangeResolver textRangeResolver) {
		return this.getAssociationOverride().getContainer().buildTableValidator(this.getAssociationOverride(), table, textRangeResolver);
	}
}

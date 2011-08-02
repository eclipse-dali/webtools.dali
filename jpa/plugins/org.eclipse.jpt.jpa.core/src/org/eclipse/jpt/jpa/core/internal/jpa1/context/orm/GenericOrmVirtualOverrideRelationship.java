/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.OverrideRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn.Owner;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.internal.context.orm.GenericOrmVirtualOverrideJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmVirtualOverrideJoinTableRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmAssociationOverride2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmVirtualOverrideRelationship2_0;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmVirtualOverrideRelationship
	extends AbstractOrmXmlContextNode
	implements OrmVirtualOverrideRelationship2_0
{
	protected OrmVirtualRelationshipStrategy strategy;

	protected final OrmVirtualJoinColumnRelationshipStrategy joinColumnStrategy;

	// JPA 2.0
	protected final OrmVirtualJoinTableRelationshipStrategy joinTableStrategy;


	public GenericOrmVirtualOverrideRelationship(OrmVirtualAssociationOverride parent) {
		super(parent);
		this.joinColumnStrategy = this.buildJoinColumnStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.setStrategy(this.buildStrategy());
		this.joinColumnStrategy.update();
		this.joinTableStrategy.update();
	}


	// ********** strategy **********

	public OrmVirtualRelationshipStrategy getStrategy() {
		return this.strategy;
	}

	protected void setStrategy(OrmVirtualRelationshipStrategy strategy) {
		OrmVirtualRelationshipStrategy old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, strategy);
	}

	protected OrmVirtualRelationshipStrategy buildStrategy() {
		return this.isJpa2_0Compatible() ?
				this.buildStrategy2_0() :
				this.joinColumnStrategy;
	}

	/**
	 * The overridden mapping determines the override's strategy.
	 */
	protected OrmVirtualRelationshipStrategy buildStrategy2_0() {
		MappingRelationshipStrategy2_0 mappingStrategy = this.getMappingStrategy();
		return (mappingStrategy != null) ?
				(OrmVirtualRelationshipStrategy) mappingStrategy.selectOverrideStrategy(this) :
				this.buildMissingMappingStrategy();
	}

	/**
	 * Get the strategy from the overridden mapping.
	 */
	protected MappingRelationshipStrategy2_0 getMappingStrategy() {
		RelationshipMapping mapping = this.getMapping();
		return (mapping == null) ? null : (MappingRelationshipStrategy2_0) mapping.getRelationship().getStrategy();
	}

	/**
	 * Return the strategy to use when the override's name does not match the
	 * name of an appropriate relationship mapping.
	 */
	protected OrmVirtualRelationshipStrategy buildMissingMappingStrategy() {
		return this.joinColumnStrategy.hasSpecifiedJoinColumns() ?
				this.joinColumnStrategy :
				this.joinTableStrategy;
	}


	// ********** join column strategy **********

	public OrmVirtualJoinColumnRelationshipStrategy getJoinColumnStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean strategyIsJoinColumn() {
		return this.strategy == this.joinColumnStrategy;
	}

	public boolean mayHaveDefaultJoinColumn() {
		// association overrides do not have defaults
		return false;
	}

	protected OrmVirtualJoinColumnRelationshipStrategy buildJoinColumnStrategy() {
		return new GenericOrmVirtualOverrideJoinColumnRelationshipStrategy(this);
	}


	// ********** join table strategy **********

	public OrmVirtualJoinTableRelationshipStrategy getJoinTableStrategy() {
		return this.joinTableStrategy;
	}

	public boolean strategyIsJoinTable() {
		return this.strategy == this.joinTableStrategy;
	}

	public boolean mayHaveDefaultJoinTable() {
		// association overrides do not have defaults
		return false;
	}

	protected OrmVirtualJoinTableRelationshipStrategy buildJoinTableStrategy() {
		return new GenericOrmVirtualOverrideJoinTableRelationshipStrategy2_0(this);
	}


	// ********** conversions **********

	public void initializeOn(Relationship newRelationship) {
		newRelationship.initializeFromJoinTableRelationship(this);
		newRelationship.initializeFromJoinColumnRelationship(this);
	}

	public void initializeOnSpecified(OverrideRelationship specifiedRelationship) {
		specifiedRelationship.initializeFromVirtualJoinColumnRelationship(this);
		specifiedRelationship.initializeFromVirtualJoinTableRelationship(this);
	}


	// ********** misc **********

	@Override
	public OrmVirtualAssociationOverride getParent() {
		return (OrmVirtualAssociationOverride) super.getParent();
	}

	protected OrmVirtualAssociationOverride getAssociationOverride() {
		return this.getParent();
	}

	protected OrmAssociationOverride2_0 getAssociationOverride2_0() {
		return (OrmAssociationOverride2_0) this.getAssociationOverride();
	}

	public TypeMapping getTypeMapping() {
		return this.getAssociationOverride().getTypeMapping();
	}

	public String getAttributeName() {
		return this.getAssociationOverride().getName();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getAssociationOverride().tableNameIsInvalid(tableName);
	}

	public Iterable<String> getCandidateTableNames() {
		return this.getAssociationOverride().getCandidateTableNames();
	}

	public Table resolveDbTable(String tableName) {
		return this.getAssociationOverride().resolveDbTable(tableName);
	}

	public String getDefaultTableName() {
		return this.getAssociationOverride().getDefaultTableName();
	}

	public JptValidator buildColumnValidator(ReadOnlyBaseColumn column, Owner owner, BaseColumnTextRangeResolver textRangeResolver) {
		return this.getAssociationOverride().buildColumnValidator(column, owner, textRangeResolver);
	}

	public Entity getEntity() {
		TypeMapping typeMapping = this.getTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	public boolean isVirtual() {
		return true;
	}

	public RelationshipMapping getMapping() {
		return this.getAssociationOverride().getMapping();
	}

	public ReadOnlyRelationship resolveOverriddenRelationship() {
		return this.getAssociationOverride().resolveOverriddenRelationship();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getAssociationOverride().getValidationTextRange();
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.strategy.validate(messages, reporter);
	}

	public JptValidator buildJoinTableValidator(ReadOnlyJoinTable table, TableTextRangeResolver textRangeResolver) {
		return this.getAssociationOverride2_0().buildJoinTableValidator(table, textRangeResolver);
	}

	public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getAssociationOverride2_0().buildJoinTableJoinColumnValidator(column, owner, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getAssociationOverride2_0().buildJoinTableInverseJoinColumnValidator(column, owner, textRangeResolver);
	}
}

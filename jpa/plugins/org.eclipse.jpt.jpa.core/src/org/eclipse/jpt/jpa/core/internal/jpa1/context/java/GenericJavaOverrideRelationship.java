/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedByRelationship;
import org.eclipse.jpt.jpa.core.context.OverrideRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTableColumn.Owner;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.internal.context.java.GenericJavaOverrideJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaOverrideJoinTableRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSpecifiedAssociationOverride2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaOverrideRelationship
	extends AbstractJavaContextModel<JavaSpecifiedAssociationOverride>
	implements JavaOverrideRelationship2_0
{
	protected RelationshipStrategy strategy;

	protected final JavaJoinColumnRelationshipStrategy joinColumnStrategy;

	// JPA 2.0
	protected final JavaJoinTableRelationshipStrategy joinTableStrategy;


	public GenericJavaOverrideRelationship(JavaSpecifiedAssociationOverride parent) {
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

	public RelationshipStrategy getStrategy() {
		return this.strategy;
	}

	protected void setStrategy(RelationshipStrategy strategy) {
		RelationshipStrategy old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, strategy);
	}

	protected RelationshipStrategy buildStrategy() {
		return this.isJpa2_0Compatible() ?
				this.buildStrategy2_0() :
				this.joinColumnStrategy;
	}

	/**
	 * The overridden mapping determines the override's strategy.
	 */
	protected RelationshipStrategy buildStrategy2_0() {
		MappingRelationshipStrategy2_0 mappingStrategy = this.getMappingStrategy();
		return (mappingStrategy != null) ?
				(RelationshipStrategy) mappingStrategy.selectOverrideStrategy(this) :
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
	protected RelationshipStrategy buildMissingMappingStrategy() {
		return this.joinColumnStrategy.hasSpecifiedJoinColumns() ?
				this.joinColumnStrategy :
				this.joinTableStrategy;
	}


	// ********** join column strategy **********

	public JavaJoinColumnRelationshipStrategy getJoinColumnStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean strategyIsJoinColumn() {
		return this.strategy == this.joinColumnStrategy;
	}

	public void setStrategyToJoinColumn() {
		throw new UnsupportedOperationException();
	}

	public boolean mayHaveDefaultJoinColumn() {
		// association overrides do not have defaults
		return false;
	}

	protected JavaJoinColumnRelationshipStrategy buildJoinColumnStrategy() {
		return new GenericJavaOverrideJoinColumnRelationshipStrategy(this);
	}


	// ********** join table strategy **********

	public JavaJoinTableRelationshipStrategy getJoinTableStrategy() {
		return this.joinTableStrategy;
	}

	public boolean strategyIsJoinTable() {
		return this.strategy == this.joinTableStrategy;
	}

	public void setStrategyToJoinTable() {
		throw new UnsupportedOperationException();
	}

	public boolean mayHaveDefaultJoinTable() {
		// association overrides do not have defaults
		return false;
	}

	protected JavaJoinTableRelationshipStrategy buildJoinTableStrategy() {
		return this.isJpa2_0Compatible() ?
				new GenericJavaOverrideJoinTableRelationshipStrategy2_0(this) :
				new NullJavaJoinTableRelationshipStrategy(this);
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
		this.joinTableStrategy.initializeFrom(oldRelationship.getJoinTableStrategy());
	}

	public void initializeFromJoinColumnRelationship(ReadOnlyJoinColumnRelationship oldRelationship) {
		this.joinColumnStrategy.initializeFrom(oldRelationship.getJoinColumnStrategy());
	}

	public void initializeFromVirtual(ReadOnlyOverrideRelationship virtualRelationship) {
		virtualRelationship.initializeOnSpecified(this);
	}

	public void initializeOnSpecified(OverrideRelationship specifiedRelationship) {
		throw new UnsupportedOperationException();
	}

	public void initializeFromVirtualJoinTableRelationship(ReadOnlyJoinTableRelationship virtualRelationship) {
		this.joinTableStrategy.initializeFromVirtual(virtualRelationship.getJoinTableStrategy());
	}

	public void initializeFromVirtualJoinColumnRelationship(ReadOnlyJoinColumnRelationship virtualRelationship) {
		this.joinColumnStrategy.initializeFromVirtual(virtualRelationship.getJoinColumnStrategy());
	}


	// ********** misc **********

	protected JavaSpecifiedAssociationOverride getAssociationOverride() {
		return this.parent;
	}

	protected JavaSpecifiedAssociationOverride2_0 getAssociationOverride2_0() {
		return (JavaSpecifiedAssociationOverride2_0) this.getAssociationOverride();
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

	public JptValidator buildColumnValidator(ReadOnlyBaseColumn column, Owner owner) {
		return this.getAssociationOverride().buildColumnValidator(column, owner);
	}

	public AssociationOverrideAnnotation getOverrideAnnotation() {
		return this.getAssociationOverride().getOverrideAnnotation();
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


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		return (this.strategy == null) ? null : this.strategy.getCompletionProposals(pos);
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getAssociationOverride().getValidationTextRange();
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// prevent NPE on JPA 2_0 platforms
		// this.strategy == null when the mapping relationship strategy, e.g. mappedBy, cannot be overridden
		if (this.strategy != null) {
			this.strategy.validate(messages, reporter);
		}
	}

	public JptValidator buildJoinTableValidator(ReadOnlyJoinTable table) {
		return this.getAssociationOverride2_0().buildJoinTableValidator(table);
	}

	public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner) {
		return this.getAssociationOverride2_0().buildJoinTableJoinColumnValidator(column, owner);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner) {
		return this.getAssociationOverride2_0().buildJoinTableInverseJoinColumnValidator(column, owner);
	}
}

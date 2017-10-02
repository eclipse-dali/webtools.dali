/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.internal.context.java.GenericJavaOverrideJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaOverrideJoinTableRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedMappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.VirtualOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSpecifiedAssociationOverride2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSpecifiedOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaOverrideRelationship
	extends AbstractJavaContextModel<JavaSpecifiedAssociationOverride>
	implements JavaSpecifiedOverrideRelationship2_0
{
	protected SpecifiedRelationshipStrategy strategy;
	protected boolean strategyIsJoinColumn;
	protected boolean strategyIsJoinTable;

	protected final JavaSpecifiedJoinColumnRelationshipStrategy joinColumnStrategy;

	// JPA 2.0
	protected final JavaSpecifiedJoinTableRelationshipStrategy joinTableStrategy;


	public GenericJavaOverrideRelationship(JavaSpecifiedAssociationOverride parent) {
		super(parent);
		this.joinColumnStrategy = this.buildJoinColumnStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();
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
		this.setStrategy(this.buildStrategy());
		this.setStrategyIsJoinColumn(this.buildStrategyIsJoinColumn());
		this.setStrategyIsJoinTable(this.buildStrategyIsJoinTable());
		this.joinColumnStrategy.update(monitor);
		this.joinTableStrategy.update(monitor);
	}


	// ********** strategy **********

	public SpecifiedRelationshipStrategy getStrategy() {
		return this.strategy;
	}

	protected void setStrategy(SpecifiedRelationshipStrategy strategy) {
		SpecifiedRelationshipStrategy old = this.strategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, this.strategy = strategy);
	}

	protected SpecifiedRelationshipStrategy buildStrategy() {
		return this.isJpa2_0Compatible() ?
				this.buildStrategy2_0() :
				this.joinColumnStrategy;
	}

	/**
	 * The overridden mapping determines the override's strategy.
	 */
	protected SpecifiedRelationshipStrategy buildStrategy2_0() {
		SpecifiedMappingRelationshipStrategy2_0 mappingStrategy = this.getMappingStrategy();
		return (mappingStrategy != null) ?
				(SpecifiedRelationshipStrategy) mappingStrategy.selectOverrideStrategy(this) :
				this.buildMissingMappingStrategy();
	}

	/**
	 * Get the strategy from the overridden mapping.
	 */
	protected SpecifiedMappingRelationshipStrategy2_0 getMappingStrategy() {
		RelationshipMapping mapping = this.getMapping();
		return (mapping == null) ? null : (SpecifiedMappingRelationshipStrategy2_0) mapping.getRelationship().getStrategy();
	}

	/**
	 * Return the strategy to use when the override's name does not match the
	 * name of an appropriate relationship mapping.
	 */
	protected SpecifiedRelationshipStrategy buildMissingMappingStrategy() {
		return this.joinColumnStrategy.hasSpecifiedJoinColumns() ?
				this.joinColumnStrategy :
				this.joinTableStrategy;
	}


	// ********** join column strategy **********

	public JavaSpecifiedJoinColumnRelationshipStrategy getJoinColumnStrategy() {
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
		throw new UnsupportedOperationException();
	}

	public boolean mayHaveDefaultJoinColumn() {
		// association overrides do not have defaults
		return false;
	}

	protected JavaSpecifiedJoinColumnRelationshipStrategy buildJoinColumnStrategy() {
		return new GenericJavaOverrideJoinColumnRelationshipStrategy(this);
	}


	// ********** join table strategy **********

	public JavaSpecifiedJoinTableRelationshipStrategy getJoinTableStrategy() {
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
		throw new UnsupportedOperationException();
	}

	public boolean mayHaveDefaultJoinTable() {
		// association overrides do not have defaults
		return false;
	}

	protected JavaSpecifiedJoinTableRelationshipStrategy buildJoinTableStrategy() {
		return this.isJpa2_0Compatible() ?
				new GenericJavaOverrideJoinTableRelationshipStrategy2_0(this) :
				new NullJavaJoinTableRelationshipStrategy(this);
	}


	// ********** conversions **********

	public void initializeFrom(VirtualOverrideRelationship virtualRelationship) {
		this.joinColumnStrategy.initializeFrom(virtualRelationship.getJoinColumnStrategy());
		if (this.isJpa2_0Compatible()) {
			this.joinTableStrategy.initializeFrom(((VirtualOverrideRelationship2_0) virtualRelationship).getJoinTableStrategy());
		}
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

	public JpaValidator buildColumnValidator(BaseColumn column, TableColumn.ParentAdapter parentAdapter) {
		return this.getAssociationOverride().buildColumnValidator(column, parentAdapter);
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

	public JpaValidator buildJoinTableValidator(JoinTable table) {
		return this.getAssociationOverride2_0().buildJoinTableValidator(table);
	}

	public JpaValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getAssociationOverride2_0().buildJoinTableJoinColumnValidator(column, parentAdapter);
	}

	public JpaValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getAssociationOverride2_0().buildJoinTableInverseJoinColumnValidator(column, parentAdapter);
	}
}

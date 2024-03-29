/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.VirtualRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaVirtualOverrideJoinTableRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.AssociationOverride2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedMappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.VirtualOverrideRelationship2_0;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaVirtualOverrideRelationship
	extends AbstractJavaContextModel<JavaVirtualAssociationOverride>
	implements VirtualOverrideRelationship2_0
{
	protected VirtualRelationshipStrategy strategy;

	protected final VirtualJoinColumnRelationshipStrategy joinColumnStrategy;

	// JPA 2.0
	protected final VirtualJoinTableRelationshipStrategy joinTableStrategy;


	public GenericJavaVirtualOverrideRelationship(JavaVirtualAssociationOverride parent) {
		super(parent);
		this.joinColumnStrategy = this.buildJoinColumnStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setStrategy(this.buildStrategy());
		this.joinColumnStrategy.update(monitor);
		this.joinTableStrategy.update(monitor);
	}


	// ********** strategy **********

	public VirtualRelationshipStrategy getStrategy() {
		return this.strategy;
	}

	protected void setStrategy(VirtualRelationshipStrategy strategy) {
		VirtualRelationshipStrategy old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, strategy);
	}

	protected VirtualRelationshipStrategy buildStrategy() {
		return this.isJpa2_0Compatible() ?
				this.buildStrategy2_0() :
				this.joinColumnStrategy;
	}

	/**
	 * The overridden mapping determines the override's strategy.
	 */
	protected VirtualRelationshipStrategy buildStrategy2_0() {
		SpecifiedMappingRelationshipStrategy2_0 mappingStrategy = this.getMappingStrategy();
		return (mappingStrategy != null) ?
				(VirtualRelationshipStrategy) mappingStrategy.selectOverrideStrategy(this) :
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
	protected VirtualRelationshipStrategy buildMissingMappingStrategy() {
		return this.joinColumnStrategy.hasSpecifiedJoinColumns() ?
				this.joinColumnStrategy :
				this.joinTableStrategy;
	}


	// ********** join column strategy **********

	public VirtualJoinColumnRelationshipStrategy getJoinColumnStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean strategyIsJoinColumn() {
		return this.strategy == this.joinColumnStrategy;
	}

	public boolean mayHaveDefaultJoinColumn() {
		// association overrides do not have defaults
		return false;
	}

	protected VirtualJoinColumnRelationshipStrategy buildJoinColumnStrategy() {
		return new GenericJavaVirtualOverrideJoinColumnRelationshipStrategy(this);
	}


	// ********** join table strategy **********

	public VirtualJoinTableRelationshipStrategy getJoinTableStrategy() {
		return this.joinTableStrategy;
	}

	public boolean strategyIsJoinTable() {
		return this.strategy == this.joinTableStrategy;
	}

	public boolean mayHaveDefaultJoinTable() {
		// association overrides do not have defaults
		return false;
	}

	protected VirtualJoinTableRelationshipStrategy buildJoinTableStrategy() {
		return new GenericJavaVirtualOverrideJoinTableRelationshipStrategy2_0(this);
	}


	// ********** misc **********

	protected JavaVirtualAssociationOverride getAssociationOverride() {
		return this.parent;
	}

	protected AssociationOverride2_0 getAssociationOverride2_0() {
		return (AssociationOverride2_0) this.getAssociationOverride();
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

	public Relationship resolveOverriddenRelationship() {
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

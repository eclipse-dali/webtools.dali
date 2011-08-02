/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaVirtualOverrideJoinColumnRelationshipStrategy
	extends AbstractJavaJpaContextNode
	implements JavaVirtualJoinColumnRelationshipStrategy
{
	protected final SpecifiedJoinColumnContainer specifiedJoinColumnContainer;
	protected final JavaReadOnlyJoinColumn.Owner joinColumnOwner;

	protected JavaVirtualJoinColumn defaultJoinColumn;


	public GenericJavaVirtualOverrideJoinColumnRelationshipStrategy(JavaVirtualOverrideRelationship parent) {
		super(parent);
		this.specifiedJoinColumnContainer = new SpecifiedJoinColumnContainer();
		this.joinColumnOwner = this.buildJoinColumnOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateSpecifiedJoinColumns();
		this.updateDefaultJoinColumn();
	}


	// ********** join columns **********

	public ListIterable<JavaVirtualJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int getJoinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}


	// ********** specified join columns **********

	public ListIterable<JavaVirtualJoinColumn> getSpecifiedJoinColumns() {
		return this.specifiedJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedJoinColumnsSize() {
		return this.specifiedJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.getSpecifiedJoinColumnsSize() != 0;
	}

	public JavaVirtualJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumnContainer.getContextElement(index);
	}

	protected void updateSpecifiedJoinColumns() {
		this.specifiedJoinColumnContainer.update();
	}

	protected ListIterable<ReadOnlyJoinColumn> getOverriddenSpecifiedJoinColumns() {
		ReadOnlyJoinColumnRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ?
				EmptyListIterable.<ReadOnlyJoinColumn>instance() :
				new SuperListIterableWrapper<ReadOnlyJoinColumn>(overriddenStrategy.getSpecifiedJoinColumns());
	}


	protected void moveSpecifiedJoinColumn(int index, JavaVirtualJoinColumn joinColumn) {
		this.specifiedJoinColumnContainer.moveContextElement(index, joinColumn);
	}

	protected JavaVirtualJoinColumn addSpecifiedJoinColumn(int index, ReadOnlyJoinColumn joinColumn) {
		return this.specifiedJoinColumnContainer.addContextElement(index, joinColumn);
	}

	protected void removeSpecifiedJoinColumn(JavaVirtualJoinColumn joinColumn) {
		this.specifiedJoinColumnContainer.removeContextElement(joinColumn);
	}

	/**
	 * specified join column container
	 */
	protected class SpecifiedJoinColumnContainer
		extends ContextListContainer<JavaVirtualJoinColumn, ReadOnlyJoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_JOIN_COLUMNS_LIST;
		}
		@Override
		protected JavaVirtualJoinColumn buildContextElement(ReadOnlyJoinColumn resourceElement) {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.buildJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<ReadOnlyJoinColumn> getResourceElements() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getOverriddenSpecifiedJoinColumns();
		}
		@Override
		protected ReadOnlyJoinColumn getResourceElement(JavaVirtualJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
	}

	protected JavaReadOnlyJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}


	// ********** default join column **********

	public JavaVirtualJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(JavaVirtualJoinColumn joinColumn) {
		JavaVirtualJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<JavaVirtualJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<JavaVirtualJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<JavaVirtualJoinColumn>instance();
	}

	protected int getDefaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultJoinColumn() {
		ReadOnlyJoinColumn overriddenDefaultJoinColumn = this.getOverriddenDefaultJoinColumn();
		if (overriddenDefaultJoinColumn == null) {
			if (this.defaultJoinColumn != null) {
				this.setDefaultJoinColumn(null);
			}
		} else {
			if ((this.defaultJoinColumn != null) && (this.defaultJoinColumn.getOverriddenColumn() == overriddenDefaultJoinColumn)) {
				this.defaultJoinColumn.update();
			} else {
				this.setDefaultJoinColumn(this.buildJoinColumn(overriddenDefaultJoinColumn));
			}
		}
	}

	protected ReadOnlyJoinColumn getOverriddenDefaultJoinColumn() {
		ReadOnlyJoinColumnRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ? null : overriddenStrategy.getDefaultJoinColumn();
	}


	// ********** misc **********

	@Override
	public JavaVirtualOverrideRelationship getParent() {
		return (JavaVirtualOverrideRelationship) super.getParent();
	}

	public JavaVirtualOverrideRelationship getRelationship() {
		return this.getParent();
	}

	protected ReadOnlyJoinColumnRelationshipStrategy getOverriddenStrategy() {
		ReadOnlyJoinColumnRelationship relationship = this.getOverriddenJoinColumnRelationship();
		return (relationship == null) ? null : relationship.getJoinColumnStrategy();
	}

	protected ReadOnlyJoinColumnRelationship getOverriddenJoinColumnRelationship() {
		ReadOnlyRelationship relationship = this.resolveOverriddenRelationship();
		return (relationship instanceof ReadOnlyJoinColumnRelationship) ? (ReadOnlyJoinColumnRelationship) relationship : null;
	}

	protected ReadOnlyRelationship resolveOverriddenRelationship() {
		return this.getRelationship().resolveOverriddenRelationship();
	}

	public boolean isTargetForeignKey() {
		RelationshipMapping relationshipMapping = this.getRelationshipMapping();
		return (relationshipMapping != null) &&
				relationshipMapping.getRelationship().isTargetForeignKey();
	}

	public TypeMapping getRelationshipSource() {
		return this.isTargetForeignKey() ?
				// relationship mapping is not null
				this.getRelationshipMapping().getResolvedTargetEntity() :
				this.getRelationship().getTypeMapping();
	}

	public TypeMapping getRelationshipTarget() {
		return this.isTargetForeignKey() ?
				this.getRelationship().getTypeMapping() :
				// relationship mapping may still be null
				this.getRelationshipMappingTargetEntity();
	}

	protected TypeMapping getRelationshipMappingTargetEntity() {
		RelationshipMapping mapping = this.getRelationshipMapping();
		return (mapping == null) ? null : mapping.getResolvedTargetEntity();
	}

	protected Entity getRelationshipTargetEntity() {
		TypeMapping target = this.getRelationshipTarget();
		return (target instanceof Entity) ? (Entity) target : null;
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	public String getTableName() {
		return this.isTargetForeignKey() ?
				this.getTargetDefaultTableName() :
				this.getRelationship().getDefaultTableName();
	}

	protected String getTargetDefaultTableName() {
		TypeMapping typeMapping = this.getRelationshipMapping().getResolvedTargetEntity();
		return (typeMapping == null) ? null : typeMapping.getPrimaryTableName();
	}

	protected Table resolveDbTable(String tableName) {
		return this.isTargetForeignKey() ?
				this.resolveTargetDbTable(tableName) :
				this.getRelationship().resolveDbTable(tableName);
	}

	protected Table resolveTargetDbTable(String tableName) {
		TypeMapping typeMapping = this.getRelationshipMapping().getResolvedTargetEntity();
		return (typeMapping == null) ? null : typeMapping.resolveDbTable(tableName);
	}

	protected boolean tableNameIsInvalid(String tableName) {
		return this.isTargetForeignKey() ?
				this.targetTableNameIsInvalid(tableName) :
				this.getRelationship().tableNameIsInvalid(tableName);
	}

	protected boolean targetTableNameIsInvalid(String tableName) {
		TypeMapping typeMapping = this.getRelationshipMapping().getResolvedTargetEntity();
		return (typeMapping != null) && typeMapping.tableNameIsInvalid(tableName);
	}

	protected Table getReferencedColumnDbTable() {
		TypeMapping relationshipTarget = this.getRelationshipTarget();
		return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
	}

	protected Iterable<String> getCandidateTableNames() {
		return this.isTargetForeignKey() ?
				this.getTargetCandidateTableNames() :
				this.getRelationship().getCandidateTableNames();
	}

	protected Iterable<String> getTargetCandidateTableNames() {
		TypeMapping typeMapping = this.getRelationshipMapping().getResolvedTargetEntity();
		return (typeMapping != null) ? typeMapping.getAllAssociatedTableNames() : EmptyIterable.<String>instance();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationship().getValidationTextRange(astRoot);
	}

	protected String getAttributeName() {
		return this.getRelationship().getAttributeName();
	}

	protected JavaVirtualJoinColumn buildJoinColumn(ReadOnlyJoinColumn overriddenJoinColumn) {
		return this.getJpaFactory().buildJavaVirtualJoinColumn(this, this.joinColumnOwner, overriddenJoinColumn);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		for (JavaVirtualJoinColumn joinColumn : this.getJoinColumns()) {
			joinColumn.validate(messages, reporter, astRoot);
		}
	}


	// ********** join column owner **********

	protected class JoinColumnOwner
		implements JavaReadOnlyJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		public String getDefaultTableName() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getTableName();
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public String getAttributeName() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getAttributeName();
		}

		public TypeMapping getTypeMapping() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getRelationshipSource();
		}

		public Entity getRelationshipTarget() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.tableNameIsInvalid(tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getCandidateTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getReferencedColumnDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return false;
		}

		public int getJoinColumnsSize() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getJoinColumnsSize();
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getValidationTextRange(astRoot);
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getRelationship().buildColumnValidator((ReadOnlyBaseColumn) column, this, (BaseColumnTextRangeResolver) textRangeResolver);
		}
	}
}

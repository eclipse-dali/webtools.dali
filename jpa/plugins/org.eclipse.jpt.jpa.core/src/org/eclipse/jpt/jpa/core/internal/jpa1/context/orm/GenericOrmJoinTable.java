/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationship;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinTable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> join table
 */
public class GenericOrmJoinTable
	extends GenericOrmReferenceTable<OrmSpecifiedJoinTableRelationshipStrategy, OrmSpecifiedJoinTable.ParentAdapter, XmlJoinTable>
	implements OrmSpecifiedJoinTable
{
	protected final ContextListContainer<OrmSpecifiedJoinColumn, XmlJoinColumn> specifiedInverseJoinColumnContainer;
	protected final JoinColumn.ParentAdapter inverseJoinColumnParentAdapter;

	protected OrmSpecifiedJoinColumn defaultInverseJoinColumn;


	public GenericOrmJoinTable(OrmSpecifiedJoinTable.ParentAdapter parentAdapter) {
		super(parentAdapter);
		this.inverseJoinColumnParentAdapter = this.buildInverseJoinColumnParentAdapter();
		this.specifiedInverseJoinColumnContainer = this.buildSpecifiedInverseJoinColumnContainer();
	}

	@Override
	protected JoinColumn.ParentAdapter buildJoinColumnParentAdapter() {
		return new JoinColumnParentAdapter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncSpecifiedInverseJoinColumns(monitor);
		if (this.defaultInverseJoinColumn != null) {
			this.defaultInverseJoinColumn.synchronizeWithResourceModel(monitor);
		}
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getSpecifiedInverseJoinColumns(), monitor);
		this.updateDefaultInverseJoinColumn(monitor);
	}


	// ********** XML table **********

	@Override
	protected XmlJoinTable getXmlTable() {
		return this.getRelationshipStrategy().getXmlJoinTable();
	}

	@Override
	protected XmlJoinTable buildXmlTable() {
		return this.getRelationshipStrategy().buildXmlJoinTable();
	}

	@Override
	protected void removeXmlTable() {
		this.getRelationshipStrategy().removeXmlJoinTable();
	}


	// ********** inverse join columns **********

	public ListIterable<OrmSpecifiedJoinColumn> getInverseJoinColumns() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumns() : this.getDefaultInverseJoinColumns();
	}

	public int getInverseJoinColumnsSize() {
		return this.hasSpecifiedInverseJoinColumns() ? this.getSpecifiedInverseJoinColumnsSize() : this.getDefaultInverseJoinColumnsSize();
	}

	public void convertDefaultInverseJoinColumnToSpecified() {
		MappingTools.convertJoinTableDefaultToSpecifiedInverseJoinColumn(this);
	}


	// ********** specified inverse join columns **********

	public ListIterable<OrmSpecifiedJoinColumn> getSpecifiedInverseJoinColumns() {
		return this.specifiedInverseJoinColumnContainer;
	}

	public int getSpecifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumnContainer.size();
	}

	public boolean hasSpecifiedInverseJoinColumns() {
		return this.getSpecifiedInverseJoinColumnsSize() != 0;
	}

	public OrmSpecifiedJoinColumn getSpecifiedInverseJoinColumn(int index) {
		return this.specifiedInverseJoinColumnContainer.get(index);
	}

	public OrmSpecifiedJoinColumn addSpecifiedInverseJoinColumn() {
		return this.addSpecifiedInverseJoinColumn(this.getSpecifiedInverseJoinColumnsSize());
	}

	public OrmSpecifiedJoinColumn addSpecifiedInverseJoinColumn(int index) {
		XmlJoinTable xmlTable = this.getXmlTableForUpdate();
		XmlJoinColumn xmlJoinColumn = this.buildXmlJoinColumn();
		OrmSpecifiedJoinColumn joinColumn = this.specifiedInverseJoinColumnContainer.addContextElement(index, xmlJoinColumn);
		xmlTable.getInverseJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	public void removeSpecifiedInverseJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.removeSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumnContainer.indexOf((OrmSpecifiedJoinColumn) joinColumn));
	}

	public void removeSpecifiedInverseJoinColumn(int index) {
		this.specifiedInverseJoinColumnContainer.remove(index);
		this.getXmlTable().getInverseJoinColumns().remove(index);
		this.removeXmlTableIfUnset();
	}

	public void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex) {
		this.specifiedInverseJoinColumnContainer.move(targetIndex, sourceIndex);
		this.getXmlTable().getInverseJoinColumns().move(targetIndex, sourceIndex);
	}

	public void clearSpecifiedInverseJoinColumns() {
		this.specifiedInverseJoinColumnContainer.clear();
		this.getXmlTable().getInverseJoinColumns().clear();
	}

	protected void syncSpecifiedInverseJoinColumns(IProgressMonitor monitor) {
		this.specifiedInverseJoinColumnContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<XmlJoinColumn> getXmlInverseJoinColumns() {
		XmlJoinTable xmlTable = this.getXmlTable();
		return (xmlTable == null) ?
				EmptyListIterable.<XmlJoinColumn>instance() :
				// clone to reduce chance of concurrency problems
				IterableTools.cloneLive(xmlTable.getInverseJoinColumns());
	}

	protected ContextListContainer<OrmSpecifiedJoinColumn, XmlJoinColumn> buildSpecifiedInverseJoinColumnContainer() {
		return this.buildSpecifiedContextListContainer(SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, new SpecifiedInverseJoinColumnContainerAdapter());
	}

	/**
	 * specified inverse join column container adapter
	 */
	public class SpecifiedInverseJoinColumnContainerAdapter
		extends AbstractContainerAdapter<OrmSpecifiedJoinColumn, XmlJoinColumn>
	{
		public OrmSpecifiedJoinColumn buildContextElement(XmlJoinColumn resourceElement) {
			return GenericOrmJoinTable.this.buildInverseJoinColumn(resourceElement);
		}
		public ListIterable<XmlJoinColumn> getResourceElements() {
			return GenericOrmJoinTable.this.getXmlInverseJoinColumns();
		}
		public XmlJoinColumn extractResourceElement(OrmSpecifiedJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected JoinColumn.ParentAdapter buildInverseJoinColumnParentAdapter() {
		return new InverseJoinColumnParentAdapter();
	}


	// ********** default inverse join column **********

	public OrmSpecifiedJoinColumn getDefaultInverseJoinColumn() {
		return this.defaultInverseJoinColumn;
	}

	protected void setDefaultInverseJoinColumn(OrmSpecifiedJoinColumn joinColumn) {
		OrmSpecifiedJoinColumn old = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterable<OrmSpecifiedJoinColumn> getDefaultInverseJoinColumns() {
		return (this.defaultInverseJoinColumn != null) ?
				new SingleElementListIterable<OrmSpecifiedJoinColumn>(this.defaultInverseJoinColumn) :
				EmptyListIterable.<OrmSpecifiedJoinColumn>instance();
	}

	protected int getDefaultInverseJoinColumnsSize() {
		return (this.defaultInverseJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultInverseJoinColumn(IProgressMonitor monitor) {
		if (this.buildsDefaultInverseJoinColumn()) {
			if (this.defaultInverseJoinColumn == null) {
				this.setDefaultInverseJoinColumn(this.buildInverseJoinColumn(null));
			} else {
				this.defaultInverseJoinColumn.update(monitor);
			}
		} else {
			this.setDefaultInverseJoinColumn(null);
		}
	}

	protected boolean buildsDefaultInverseJoinColumn() {
		return ! this.hasSpecifiedInverseJoinColumns();
	}


	// ********** misc **********

	protected OrmSpecifiedJoinTableRelationshipStrategy getRelationshipStrategy() {
		return this.parent;
	}

	@Override
	protected String buildDefaultName() {
		return this.getRelationshipStrategy().getJoinTableDefaultName();
	}

	public void initializeFrom(OrmSpecifiedJoinTable oldTable) {
		super.initializeFrom(oldTable);
		for (OrmSpecifiedJoinColumn joinColumn : oldTable.getSpecifiedInverseJoinColumns()) {
			this.addSpecifiedInverseJoinColumn().initializeFrom(joinColumn);
		}
	}

	public void initializeFrom(VirtualJoinTable virtualTable) {
		super.initializeFrom(virtualTable);
		for (VirtualJoinColumn joinColumn : virtualTable.getInverseJoinColumns()) {
			this.addSpecifiedInverseJoinColumn().initializeFrom(joinColumn);
		}
	}

	protected OrmSpecifiedJoinColumn buildInverseJoinColumn(XmlJoinColumn xmlJoinColumn) {
		return this.getContextModelFactory().buildOrmJoinColumn(this.inverseJoinColumnParentAdapter, xmlJoinColumn);
	}

	public RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipStrategy().getRelationship().getMapping();
	}

	public SpecifiedPersistentAttribute getPersistentAttribute() {
		return this.getRelationshipMapping().getPersistentAttribute();
	}


	// ********** validation **********

	@Override
	protected void validateJoinColumns(List<IMessage> messages, IReporter reporter) {
		super.validateJoinColumns(messages, reporter);
		this.validateModels(this.getInverseJoinColumns(), messages, reporter);
	}

	public boolean validatesAgainstDatabase() {
		return this.getRelationshipStrategy().validatesAgainstDatabase();
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (OrmSpecifiedJoinColumn column : this.getInverseJoinColumns()) {
			result = column.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	// ********** join column parent adapters **********

	/**
	 * just a little common behavior
	 */
	public abstract class AbstractJoinColumnParentAdapter
		implements JoinColumn.ParentAdapter
	{
		public JpaContextModel getColumnParent() {
			return GenericOrmJoinTable.this;
		}

		public String getDefaultColumnName(NamedColumn column) {
			return MappingTools.buildJoinColumnDefaultName((JoinColumn) column, this);
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name. The table is always the join table.
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return ObjectTools.notEquals(this.getDefaultTableName(), tableName);
		}

		/**
		 * the join column can only be on the join table itself
		 */
		public Iterable<String> getCandidateTableNames() {
			return EmptyIterable.instance();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return ObjectTools.equals(GenericOrmJoinTable.this.getName(), tableName) ?
					GenericOrmJoinTable.this.getDbTable() :
					null;
		}

		/**
		 * by default, the join column is, obviously, in the join table;
		 * not sure whether it can be anywhere else...
		 */
		public String getDefaultTableName() {
			return GenericOrmJoinTable.this.getName();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmJoinTable.this.getValidationTextRange();
		}

		protected SpecifiedRelationship getRelationship() {
			return this.getRelationshipStrategy().getRelationship();
		}

		protected OrmSpecifiedJoinTableRelationshipStrategy getRelationshipStrategy() {
			return GenericOrmJoinTable.this.getRelationshipStrategy();
		}
	}


	/**
	 * parent adapter for "back-pointer" join columns;
	 * these point at the source/owning entity
	 */
	public class JoinColumnParentAdapter
		extends AbstractJoinColumnParentAdapter
	{
		public Entity getRelationshipTarget() {
			return this.getRelationship().getEntity();
		}

		public String getAttributeName() {
			return MappingTools.getTargetAttributeName(GenericOrmJoinTable.this.getRelationshipMapping());
		}

		@Override
		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			org.eclipse.jpt.jpa.db.Table dbTable = super.resolveDbTable(tableName);
			return (dbTable != null) ? dbTable : this.getTypeMapping().resolveDbTable(tableName);
		}

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			return this.getTypeMapping().getPrimaryDbTable();
		}

		protected TypeMapping getTypeMapping() {
			return this.getRelationship().getTypeMapping();
		}

		public int getJoinColumnsSize() {
			return GenericOrmJoinTable.this.getJoinColumnsSize();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return this.getRelationshipStrategy().buildJoinTableJoinColumnValidator((JoinColumn) column, this);
		}
	}


	/**
	 * parent adapter for "forward-pointer" join columns;
	 * these point at the target/inverse entity
	 */
	public class InverseJoinColumnParentAdapter
		extends AbstractJoinColumnParentAdapter
	{
		public Entity getRelationshipTarget() {
			RelationshipMapping relationshipMapping = GenericOrmJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getResolvedTargetEntity();
		}

		public String getAttributeName() {
			RelationshipMapping relationshipMapping = GenericOrmJoinTable.this.getRelationshipMapping();
			return (relationshipMapping == null) ? null : relationshipMapping.getName();
		}

		@Override
		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			org.eclipse.jpt.jpa.db.Table dbTable = super.resolveDbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.resolveDbTable(tableName);
		}

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			Entity relationshipTarget = this.getRelationshipTarget();
			return (relationshipTarget == null) ? null : relationshipTarget.getPrimaryDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericOrmJoinTable.this.getInverseJoinColumnsSize();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return this.getRelationshipStrategy().buildJoinTableInverseJoinColumnValidator((JoinColumn) column, this);
		}
	}
}

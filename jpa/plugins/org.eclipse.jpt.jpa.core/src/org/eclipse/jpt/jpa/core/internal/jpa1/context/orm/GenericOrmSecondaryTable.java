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
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualSecondaryTable;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmTable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.SecondaryTablePrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> secondary table
 */
public class GenericOrmSecondaryTable
	extends AbstractOrmTable<OrmEntity, OrmSpecifiedSecondaryTable.ParentAdapter, XmlSecondaryTable>
	implements OrmSpecifiedSecondaryTable
{
	/** @see AbstractOrmTable#AbstractOrmTable(org.eclipse.jpt.jpa.core.context.Table.ParentAdapter, org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlTable) */
	protected /* final */ XmlSecondaryTable xmlSecondaryTable;

	protected final ContextListContainer<OrmSpecifiedPrimaryKeyJoinColumn, XmlPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumnContainer;
	protected final BaseJoinColumn.ParentAdapter primaryKeyJoinColumnParentAdapter;

	protected OrmSpecifiedPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;


	public GenericOrmSecondaryTable(OrmSpecifiedSecondaryTable.ParentAdapter parentAdapter, XmlSecondaryTable xmlSecondaryTable) {
		super(parentAdapter, xmlSecondaryTable);
		this.primaryKeyJoinColumnParentAdapter = this.buildPrimaryKeyJoinColumnParentAdapter();
		this.specifiedPrimaryKeyJoinColumnContainer = this.buildSpecifiedPrimaryKeyJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncSpecifiedPrimaryKeyJoinColumns(monitor);
		if (this.defaultPrimaryKeyJoinColumn != null) {
			this.defaultPrimaryKeyJoinColumn.synchronizeWithResourceModel(monitor);
		}
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getSpecifiedPrimaryKeyJoinColumns(), monitor);
		this.updateDefaultPrimaryKeyJoinColumn(monitor);
	}


	// ********** XML table **********

	@Override
	public XmlSecondaryTable getXmlTable() {
		return this.xmlSecondaryTable;
	}

	/**
	 * @see AbstractOrmTable
	 */
	@Override
	protected void setXmlTable(XmlSecondaryTable xmlTable) {
		this.xmlSecondaryTable = xmlTable;
	}

	/**
	 * secondary tables are part of a collection;
	 * the 'secondary-table' element will be removed from/added
	 * when the XML secondary table is removed/added to
	 * the XML entity's collection
	 */
	@Override
	protected XmlSecondaryTable buildXmlTable() {
		throw new IllegalStateException("XML secondary table is missing"); //$NON-NLS-1$
	}

	/**
	 * @see #buildXmlTable()
	 */
	@Override
	protected void removeXmlTable() {
		// do nothing
	}


	// ********** primary key join columns **********

	public ListIterable<OrmSpecifiedPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumns() : this.getDefaultPrimaryKeyJoinColumns();
	}

	public int getPrimaryKeyJoinColumnsSize() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumnsSize() : this.getDefaultPrimaryKeyJoinColumnsSize();
	}


	// ********** specified primary key join columns **********

	public ListIterable<OrmSpecifiedPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumnContainer;
	}

	public OrmSpecifiedPrimaryKeyJoinColumn getSpecifiedPrimaryKeyJoinColumn(int index) {
		return this.specifiedPrimaryKeyJoinColumnContainer.get(index);
	}

	public int getSpecifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumnContainer.size();
	}

	protected boolean hasSpecifiedPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumnsSize() != 0;
	}

	public OrmSpecifiedPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn() {
		return this.addSpecifiedPrimaryKeyJoinColumn(this.getSpecifiedPrimaryKeyJoinColumnsSize());
	}

	public OrmSpecifiedPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		XmlPrimaryKeyJoinColumn xmlJoinColumn = this.buildXmlPrimaryKeyJoinColumn();
		OrmSpecifiedPrimaryKeyJoinColumn joinColumn = this.specifiedPrimaryKeyJoinColumnContainer.addContextElement(index, xmlJoinColumn);
		this.xmlSecondaryTable.getPrimaryKeyJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlPrimaryKeyJoinColumn buildXmlPrimaryKeyJoinColumn() {
		return OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn();
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(SpecifiedPrimaryKeyJoinColumn joinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumnContainer.indexOf((OrmSpecifiedPrimaryKeyJoinColumn) joinColumn));
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		this.specifiedPrimaryKeyJoinColumnContainer.remove(index);
		this.xmlSecondaryTable.getPrimaryKeyJoinColumns().remove(index);
	}

	//default PK join column will get set in the update
	public void convertDefaultPrimaryKeyJoinColumnsToSpecified() {
		if (this.defaultPrimaryKeyJoinColumn == null) {
			throw new IllegalStateException("default PK join column is null"); //$NON-NLS-1$
		}
		// Add a PK join column by creating a specified one using the default one
		String columnName = this.defaultPrimaryKeyJoinColumn.getDefaultName();
		String referencedColumnName = this.defaultPrimaryKeyJoinColumn.getDefaultReferencedColumnName();

		SpecifiedPrimaryKeyJoinColumn pkJoinColumn = this.addSpecifiedPrimaryKeyJoinColumn(0);
		pkJoinColumn.setSpecifiedName(columnName);
		pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);		
	}

	public void clearSpecifiedPrimaryKeyJoinColumns() {
		this.specifiedPrimaryKeyJoinColumnContainer.clear();
		this.xmlSecondaryTable.getPrimaryKeyJoinColumns().clear();
	}

	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.specifiedPrimaryKeyJoinColumnContainer.move(targetIndex, sourceIndex);
		this.xmlSecondaryTable.getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
	}

	protected void syncSpecifiedPrimaryKeyJoinColumns(IProgressMonitor monitor) {
		this.specifiedPrimaryKeyJoinColumnContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<XmlPrimaryKeyJoinColumn> getXmlPrimaryKeyJoinColumns() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlSecondaryTable.getPrimaryKeyJoinColumns());
	}

	protected ContextListContainer<OrmSpecifiedPrimaryKeyJoinColumn, XmlPrimaryKeyJoinColumn> buildSpecifiedPrimaryKeyJoinColumnContainer() {
		return this.buildSpecifiedContextListContainer(SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, new SpecifiedPrimaryKeyJoinColumnContainerAdapter());
	}

	/**
	 * specified primary key join column container adapter
	 */
	public class SpecifiedPrimaryKeyJoinColumnContainerAdapter
		extends AbstractContainerAdapter<OrmSpecifiedPrimaryKeyJoinColumn, XmlPrimaryKeyJoinColumn>
	{
		public OrmSpecifiedPrimaryKeyJoinColumn buildContextElement(XmlPrimaryKeyJoinColumn resourceElement) {
			return GenericOrmSecondaryTable.this.buildPrimaryKeyJoinColumn(resourceElement);
		}
		public ListIterable<XmlPrimaryKeyJoinColumn> getResourceElements() {
			return GenericOrmSecondaryTable.this.getXmlPrimaryKeyJoinColumns();
		}
		public XmlPrimaryKeyJoinColumn extractResourceElement(OrmSpecifiedPrimaryKeyJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected BaseJoinColumn.ParentAdapter buildPrimaryKeyJoinColumnParentAdapter() {
		return new PrimaryKeyJoinColumnParentAdapter();
	}


	// ********** default primary key join column **********

	public OrmSpecifiedPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		return this.defaultPrimaryKeyJoinColumn;
	}

	protected void setDefaultPrimaryKeyJoinColumn(OrmSpecifiedPrimaryKeyJoinColumn joinColumn) {
		OrmSpecifiedPrimaryKeyJoinColumn old = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_PRIMARY_KEY_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterable<OrmSpecifiedPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
		return (this.defaultPrimaryKeyJoinColumn != null) ?
				new SingleElementListIterable<OrmSpecifiedPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn) :
				EmptyListIterable.<OrmSpecifiedPrimaryKeyJoinColumn>instance();
	}

	protected int getDefaultPrimaryKeyJoinColumnsSize() {
		return (this.defaultPrimaryKeyJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultPrimaryKeyJoinColumn(IProgressMonitor monitor) {
		if (this.buildsDefaultPrimaryKeyJoinColumn()) {
			if (this.defaultPrimaryKeyJoinColumn == null) {
				this.setDefaultPrimaryKeyJoinColumn(this.buildPrimaryKeyJoinColumn(null));
			} else {
				this.defaultPrimaryKeyJoinColumn.update(monitor);
			}
		} else {
			this.setDefaultPrimaryKeyJoinColumn(null);
		}
	}

	protected boolean buildsDefaultPrimaryKeyJoinColumn() {
		return ! this.hasSpecifiedPrimaryKeyJoinColumns();
	}


	// ********** misc **********

	protected OrmEntity getEntity() {
		return this.parent;
	}

	public boolean isVirtual() {
		return false;
	}

	public void initializeFrom(OrmVirtualSecondaryTable oldTable) {
		super.initializeFrom(oldTable);
		for (OrmVirtualPrimaryKeyJoinColumn pkJoinColumn : oldTable.getSpecifiedPrimaryKeyJoinColumns()) {
			this.addSpecifiedPrimaryKeyJoinColumn().initializeFrom(pkJoinColumn);
		}
	}

	protected OrmSpecifiedPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn xmlJoinColumn) {
		return this.getContextModelFactory().buildOrmPrimaryKeyJoinColumn(this.primaryKeyJoinColumnParentAdapter, xmlJoinColumn);
	}


	// ********** defaults **********

	/**
	 * a secondary table doesn't have a default name
	 */
	@Override
	protected String buildDefaultName() {
		return null;
	}

	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}


	// ********** validation **********

	public boolean validatesAgainstDatabase() {
		return this.connectionProfileIsActive();
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		boolean continueValidating = this.buildTableValidator().validate(messages, reporter);

		//join column validation will handle the check for whether to validate against the database
		//some validation messages are not database specific. If the database validation for the
		//table fails we will stop there and not validate the join columns at all
		if (continueValidating) {
			this.validateModels(this.getPrimaryKeyJoinColumns(), messages, reporter);
		}
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (OrmSpecifiedPrimaryKeyJoinColumn column : this.getPrimaryKeyJoinColumns()) {
			result = column.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	// ********** primary key join column parent adapter **********

	public class PrimaryKeyJoinColumnParentAdapter
		implements BaseJoinColumn.ParentAdapter
	{
		public JpaContextModel getColumnParent() {
			return GenericOrmSecondaryTable.this;
		}

		protected OrmEntity getEntity() {
			return GenericOrmSecondaryTable.this.getEntity();
		}

		public String getDefaultTableName() {
			return GenericOrmSecondaryTable.this.getName();
		}

		public String getDefaultColumnName(NamedColumn column) {
			if (this.getJoinColumnsSize() != 1) {
				return null;
			}
			Entity parentEntity = this.getEntity().getParentEntity();
			return (parentEntity != null) ?
					parentEntity.getPrimaryKeyColumnName() :
					this.getEntity().getPrimaryKeyColumnName();
		}

		public Table resolveDbTable(String tableName) {
			return GenericOrmSecondaryTable.this.getDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericOrmSecondaryTable.this.getPrimaryKeyJoinColumnsSize();
		}

		public Table getReferencedColumnDbTable() {
			return this.getEntity().getPrimaryDbTable();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmSecondaryTable.this.getValidationTextRange();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return new SecondaryTablePrimaryKeyJoinColumnValidator(GenericOrmSecondaryTable.this, (BaseJoinColumn) column, this);
		}
	}
}

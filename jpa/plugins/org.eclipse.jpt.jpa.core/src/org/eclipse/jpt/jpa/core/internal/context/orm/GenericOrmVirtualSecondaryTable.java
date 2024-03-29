/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualSecondaryTable;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.SecondaryTablePrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> virtual secondary table
 */
public class GenericOrmVirtualSecondaryTable
	extends AbstractOrmVirtualTable<OrmEntity, OrmVirtualSecondaryTable.ParentAdapter, JavaSpecifiedSecondaryTable>
	implements OrmVirtualSecondaryTable
{

	protected final ContextListContainer<OrmVirtualPrimaryKeyJoinColumn, JavaSpecifiedPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumnContainer;
	protected final BaseJoinColumn.ParentAdapter primaryKeyJoinColumnParentAdapter;

	protected OrmVirtualPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;


	public GenericOrmVirtualSecondaryTable(OrmVirtualSecondaryTable.ParentAdapter parentAdapter, JavaSpecifiedSecondaryTable overriddenTable) {
		super(parentAdapter, overriddenTable);
		this.primaryKeyJoinColumnParentAdapter = this.buildPrimaryKeyJoinColumnParentAdapter();
		this.specifiedPrimaryKeyJoinColumnContainer = this.buildSpecifiedPrimaryKeyJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateSpecifiedPrimaryKeyJoinColumns(monitor);
		this.updateDefaultPrimaryKeyJoinColumn(monitor);
	}


	// ********** primary key join columns **********

	public ListIterable<OrmVirtualPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumns() : this.getDefaultPrimaryKeyJoinColumns();
	}

	public int getPrimaryKeyJoinColumnsSize() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumnsSize() : this.getDefaultPrimaryKeyJoinColumnsSize();
	}


	// ********** specified primary key join columns **********

	public ListIterable<OrmVirtualPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumnContainer;
	}

	public int getSpecifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumnContainer.size();
	}

	public boolean hasSpecifiedPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumnsSize() != 0;
	}

	public OrmVirtualPrimaryKeyJoinColumn getSpecifiedPrimaryKeyJoinColumn(int index) {
		return this.specifiedPrimaryKeyJoinColumnContainer.get(index);
	}

	protected void updateSpecifiedPrimaryKeyJoinColumns(IProgressMonitor monitor) {
		this.specifiedPrimaryKeyJoinColumnContainer.update(monitor);
	}

	protected ListIterable<JavaSpecifiedPrimaryKeyJoinColumn> getOverriddenPrimaryKeyJoinColumns() {
		return this.getOverriddenTable().getSpecifiedPrimaryKeyJoinColumns();
	}

	protected void moveSpecifiedPrimaryKeyJoinColumn(int index, OrmVirtualPrimaryKeyJoinColumn pkJoinColumn) {
		this.specifiedPrimaryKeyJoinColumnContainer.move(index, pkJoinColumn);
	}

	protected OrmVirtualPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index, JavaSpecifiedPrimaryKeyJoinColumn javaColumn) {
		return this.specifiedPrimaryKeyJoinColumnContainer.addContextElement(index, javaColumn);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn(OrmVirtualPrimaryKeyJoinColumn pkJoinColumn) {
		this.specifiedPrimaryKeyJoinColumnContainer.remove(pkJoinColumn);
	}

	protected ContextListContainer<OrmVirtualPrimaryKeyJoinColumn, JavaSpecifiedPrimaryKeyJoinColumn> buildSpecifiedPrimaryKeyJoinColumnContainer() {
		return this.buildVirtualContextListContainer(SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, new SpecifiedPrimaryKeyJoinColumnContainerAdapter());
	}

	/**
	 * specified primary key join column container adapter
	 */
	public class SpecifiedPrimaryKeyJoinColumnContainerAdapter
		extends AbstractContainerAdapter<OrmVirtualPrimaryKeyJoinColumn, JavaSpecifiedPrimaryKeyJoinColumn>
	{
		public OrmVirtualPrimaryKeyJoinColumn buildContextElement(JavaSpecifiedPrimaryKeyJoinColumn resourceElement) {
			return GenericOrmVirtualSecondaryTable.this.buildPrimaryKeyJoinColumn(resourceElement);
		}
		public ListIterable<JavaSpecifiedPrimaryKeyJoinColumn> getResourceElements() {
			return GenericOrmVirtualSecondaryTable.this.getOverriddenPrimaryKeyJoinColumns();
		}
		public JavaSpecifiedPrimaryKeyJoinColumn extractResourceElement(OrmVirtualPrimaryKeyJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
	}


	// ********** default primary key join column **********

	public OrmVirtualPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		return this.defaultPrimaryKeyJoinColumn;
	}

	protected void setDefaultPrimaryKeyJoinColumn(OrmVirtualPrimaryKeyJoinColumn pkJoinColumn) {
		OrmVirtualPrimaryKeyJoinColumn old = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = pkJoinColumn;
		this.firePropertyChanged(DEFAULT_PRIMARY_KEY_JOIN_COLUMN, old, pkJoinColumn);
	}

	protected ListIterable<OrmVirtualPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
		return (this.defaultPrimaryKeyJoinColumn != null) ?
				new SingleElementListIterable<OrmVirtualPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn) :
				EmptyListIterable.<OrmVirtualPrimaryKeyJoinColumn>instance();
	}

	protected int getDefaultPrimaryKeyJoinColumnsSize() {
		return (this.defaultPrimaryKeyJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultPrimaryKeyJoinColumn(IProgressMonitor monitor) {
		JavaSpecifiedPrimaryKeyJoinColumn overriddenColumn = this.getOverriddenTable().getDefaultPrimaryKeyJoinColumn();
		if (overriddenColumn == null) {
			this.setDefaultPrimaryKeyJoinColumn(null);
		} else {
			if ((this.defaultPrimaryKeyJoinColumn != null) && (this.defaultPrimaryKeyJoinColumn.getOverriddenColumn() == overriddenColumn)) {
				this.defaultPrimaryKeyJoinColumn.update(monitor);
			} else {
				this.setDefaultPrimaryKeyJoinColumn(this.buildPrimaryKeyJoinColumn(overriddenColumn));
			}
		}
	}


	// ********** misc **********

	protected OrmEntity getEntity() {
		return this.parent;
	}

	public boolean isVirtual() {
		return true;
	}

	protected BaseJoinColumn.ParentAdapter buildPrimaryKeyJoinColumnParentAdapter() {
		return new PrimaryKeyJoinColumnParentAdapter();
	}

	protected OrmVirtualPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(JavaSpecifiedPrimaryKeyJoinColumn javaColumn) {
		return this.getContextModelFactory().buildOrmVirtualPrimaryKeyJoinColumn(this.primaryKeyJoinColumnParentAdapter, javaColumn);
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


	// ********** primary key join column parent adapter **********

	public class PrimaryKeyJoinColumnParentAdapter
		implements BaseJoinColumn.ParentAdapter
	{
		public JpaContextModel getColumnParent() {
			return GenericOrmVirtualSecondaryTable.this;
		}

		protected OrmEntity getEntity() {
			return GenericOrmVirtualSecondaryTable.this.getEntity();
		}

		public String getDefaultTableName() {
			return GenericOrmVirtualSecondaryTable.this.getName();
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
			return GenericOrmVirtualSecondaryTable.this.getDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericOrmVirtualSecondaryTable.this.getPrimaryKeyJoinColumnsSize();
		}

		public Table getReferencedColumnDbTable() {
			return this.getEntity().getPrimaryDbTable();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmVirtualSecondaryTable.this.getValidationTextRange();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return new SecondaryTablePrimaryKeyJoinColumnValidator(GenericOrmVirtualSecondaryTable.this, (BaseJoinColumn) column, this);
		}
	}
}

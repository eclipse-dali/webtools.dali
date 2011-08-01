/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualSecondaryTable;
import org.eclipse.jpt.jpa.core.internal.context.BaseJoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.SecondaryTablePrimaryKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> virtual secondary table
 */
public class GenericOrmVirtualSecondaryTable
	extends AbstractOrmVirtualTable<JavaSecondaryTable>
	implements OrmVirtualSecondaryTable
{

	protected final ContextListContainer<OrmVirtualPrimaryKeyJoinColumn, JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumnContainer;
	protected final OrmReadOnlyBaseJoinColumn.Owner primaryKeyJoinColumnOwner;

	protected OrmVirtualPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;


	public GenericOrmVirtualSecondaryTable(OrmEntity parent, Owner owner, JavaSecondaryTable overriddenTable) {
		super(parent, owner, overriddenTable);
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
		this.specifiedPrimaryKeyJoinColumnContainer = this.buildSpecifiedPrimaryKeyJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateSpecifiedPrimaryKeyJoinColumns();
		this.updateDefaultPrimaryKeyJoinColumn();
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
		return this.specifiedPrimaryKeyJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumnsSize() != 0;
	}

	public OrmVirtualPrimaryKeyJoinColumn getSpecifiedPrimaryKeyJoinColumn(int index) {
		return this.specifiedPrimaryKeyJoinColumnContainer.getContextElement(index);
	}

	protected void updateSpecifiedPrimaryKeyJoinColumns() {
		this.specifiedPrimaryKeyJoinColumnContainer.update();
	}

	protected ListIterable<JavaPrimaryKeyJoinColumn> getOverriddenPrimaryKeyJoinColumns() {
		return this.getOverriddenTable().getSpecifiedPrimaryKeyJoinColumns();
	}

	protected void moveSpecifiedPrimaryKeyJoinColumn(int index, OrmVirtualPrimaryKeyJoinColumn pkJoinColumn) {
		this.specifiedPrimaryKeyJoinColumnContainer.moveContextElement(index, pkJoinColumn);
	}

	protected OrmVirtualPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index, JavaPrimaryKeyJoinColumn javaColumn) {
		return this.specifiedPrimaryKeyJoinColumnContainer.addContextElement(index, javaColumn);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn(OrmVirtualPrimaryKeyJoinColumn pkJoinColumn) {
		this.specifiedPrimaryKeyJoinColumnContainer.removeContextElement(pkJoinColumn);
	}

	protected ContextListContainer<OrmVirtualPrimaryKeyJoinColumn, JavaPrimaryKeyJoinColumn> buildSpecifiedPrimaryKeyJoinColumnContainer() {
		return new SpecifiedPrimaryKeyJoinColumnContainer();
	}

	/**
	 * specified primary key join column container
	 */
	protected class SpecifiedPrimaryKeyJoinColumnContainer
		extends ContextListContainer<OrmVirtualPrimaryKeyJoinColumn, JavaPrimaryKeyJoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST;
		}
		@Override
		protected OrmVirtualPrimaryKeyJoinColumn buildContextElement(JavaPrimaryKeyJoinColumn resourceElement) {
			return GenericOrmVirtualSecondaryTable.this.buildPrimaryKeyJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<JavaPrimaryKeyJoinColumn> getResourceElements() {
			return GenericOrmVirtualSecondaryTable.this.getOverriddenPrimaryKeyJoinColumns();
		}
		@Override
		protected JavaPrimaryKeyJoinColumn getResourceElement(OrmVirtualPrimaryKeyJoinColumn contextElement) {
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

	protected void updateDefaultPrimaryKeyJoinColumn() {
		JavaPrimaryKeyJoinColumn overriddenColumn = this.getOverriddenTable().getDefaultPrimaryKeyJoinColumn();
		if (overriddenColumn == null) {
			this.setDefaultPrimaryKeyJoinColumn(null);
		} else {
			if ((this.defaultPrimaryKeyJoinColumn != null) && (this.defaultPrimaryKeyJoinColumn.getOverriddenColumn() == overriddenColumn)) {
				this.defaultPrimaryKeyJoinColumn.update();
			} else {
				this.setDefaultPrimaryKeyJoinColumn(this.buildPrimaryKeyJoinColumn(overriddenColumn));
			}
		}
	}


	// ********** misc **********

	@Override
	public OrmEntity getParent() {
		return (OrmEntity) super.getParent();
	}

	protected OrmEntity getEntity() {
		return this.getParent();
	}

	public boolean isVirtual() {
		return true;
	}

	protected OrmReadOnlyBaseJoinColumn.Owner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected OrmVirtualPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn javaColumn) {
		return this.getContextNodeFactory().buildOrmVirtualPrimaryKeyJoinColumn(this, this.primaryKeyJoinColumnOwner, javaColumn);
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
			this.validateNodes(this.getPrimaryKeyJoinColumns(), messages, reporter);
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


	// ********** primary key join column owner **********

	protected class PrimaryKeyJoinColumnOwner
		implements OrmReadOnlyBaseJoinColumn.Owner
	{
		protected OrmEntity getEntity() {
			return GenericOrmVirtualSecondaryTable.this.getEntity();
		}

		public TypeMapping getTypeMapping() {
			return this.getEntity();
		}

		public String getDefaultTableName() {
			return GenericOrmVirtualSecondaryTable.this.getName();
		}

		public String getDefaultColumnName() {
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

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericOrmVirtualSecondaryTable.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}

		public Table getReferencedColumnDbTable() {
			return this.getTypeMapping().getPrimaryDbTable();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmVirtualSecondaryTable.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new SecondaryTablePrimaryKeyJoinColumnValidator(GenericOrmVirtualSecondaryTable.this, (ReadOnlyBaseJoinColumn) column, this, (BaseJoinColumnTextRangeResolver) textRangeResolver);
		}
	}
}

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
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
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
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
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
	protected final JavaSecondaryTable overriddenTable;

	protected final Vector<OrmVirtualPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns = new Vector<OrmVirtualPrimaryKeyJoinColumn>();
	protected final SpecifiedPrimaryKeyJoinColumnContainerAdapter specifiedPrimaryKeyJoinColumnContainerAdapter = new SpecifiedPrimaryKeyJoinColumnContainerAdapter();
	protected final OrmReadOnlyBaseJoinColumn.Owner primaryKeyJoinColumnOwner;

	protected OrmVirtualPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;


	public GenericOrmVirtualSecondaryTable(OrmEntity parent, Owner owner, JavaSecondaryTable overriddenTable) {
		super(parent, owner);
		this.overriddenTable = overriddenTable;
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateSpecifiedPrimaryKeyJoinColumns();
		this.updateDefaultPrimaryKeyJoinColumn();
	}


	// ********** table **********

	@Override
	public JavaSecondaryTable getOverriddenTable() {
		return this.overriddenTable;
	}


	// ********** primary key join columns **********

	public ListIterator<OrmVirtualPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.getPrimaryKeyJoinColumns().iterator();
	}

	protected ListIterable<OrmVirtualPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumns() : this.getDefaultPrimaryKeyJoinColumns();
	}

	public int primaryKeyJoinColumnsSize() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumnsSize() : this.getDefaultPrimaryKeyJoinColumnsSize();
	}


	// ********** specified primary key join columns **********

	public ListIterator<OrmVirtualPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumns().iterator();
	}

	protected ListIterable<OrmVirtualPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		return new LiveCloneListIterable<OrmVirtualPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}

	public boolean hasSpecifiedPrimaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.size() != 0;
	}

	public OrmVirtualPrimaryKeyJoinColumn getSpecifiedPrimaryKeyJoinColumn(int index) {
		return this.specifiedPrimaryKeyJoinColumns.get(index);
	}

	protected void updateSpecifiedPrimaryKeyJoinColumns() {
		ContextContainerTools.update(this.specifiedPrimaryKeyJoinColumnContainerAdapter);
	}

	protected Iterable<JavaPrimaryKeyJoinColumn> getOverriddenPrimaryKeyJoinColumns() {
		return CollectionTools.iterable(this.getOverriddenTable().specifiedPrimaryKeyJoinColumns());
	}

	protected void moveSpecifiedPrimaryKeyJoinColumn(int index, OrmVirtualPrimaryKeyJoinColumn pkJoinColumn) {
		this.moveItemInList(index, pkJoinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected OrmVirtualPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index, JavaPrimaryKeyJoinColumn javaColumn) {
		OrmVirtualPrimaryKeyJoinColumn virtualColumn = this.buildPrimaryKeyJoinColumn(javaColumn);
		this.addItemToList(index, virtualColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
		return virtualColumn;
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn(OrmVirtualPrimaryKeyJoinColumn pkJoinColumn) {
		this.removeItemFromList(pkJoinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	/**
	 * specified primary key join column container adapter
	 */
	protected class SpecifiedPrimaryKeyJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<OrmVirtualPrimaryKeyJoinColumn, JavaPrimaryKeyJoinColumn>
	{
		public Iterable<OrmVirtualPrimaryKeyJoinColumn> getContextElements() {
			return GenericOrmVirtualSecondaryTable.this.getSpecifiedPrimaryKeyJoinColumns();
		}
		public Iterable<JavaPrimaryKeyJoinColumn> getResourceElements() {
			return GenericOrmVirtualSecondaryTable.this.getOverriddenPrimaryKeyJoinColumns();
		}
		public JavaPrimaryKeyJoinColumn getResourceElement(OrmVirtualPrimaryKeyJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
		public void moveContextElement(int index, OrmVirtualPrimaryKeyJoinColumn element) {
			GenericOrmVirtualSecondaryTable.this.moveSpecifiedPrimaryKeyJoinColumn(index, element);
		}
		public void addContextElement(int index, JavaPrimaryKeyJoinColumn element) {
			GenericOrmVirtualSecondaryTable.this.addSpecifiedPrimaryKeyJoinColumn(index, element);
		}
		public void removeContextElement(OrmVirtualPrimaryKeyJoinColumn element) {
			GenericOrmVirtualSecondaryTable.this.removeSpecifiedPrimaryKeyJoinColumn(element);
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
			if (this.joinColumnsSize() != 1) {
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

		public int joinColumnsSize() {
			return GenericOrmVirtualSecondaryTable.this.primaryKeyJoinColumnsSize();
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

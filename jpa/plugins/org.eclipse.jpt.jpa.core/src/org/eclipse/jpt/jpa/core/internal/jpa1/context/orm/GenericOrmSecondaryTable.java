/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlySecondaryTable;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.jpa.core.internal.context.BaseJoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
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
	extends AbstractOrmTable<XmlSecondaryTable>
	implements OrmSecondaryTable
{
	/** @see AbstractOrmTable#AbstractOrmTable(org.eclipse.jpt.jpa.core.context.XmlContextNode, org.eclipse.jpt.jpa.core.context.Table.Owner, org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlTable) */
	protected /* final */ XmlSecondaryTable xmlSecondaryTable;

	protected final Vector<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns = new Vector<OrmPrimaryKeyJoinColumn>();
	protected final SpecifiedPrimaryKeyJoinColumnContainerAdapter specifiedPrimaryKeyJoinColumnContainerAdapter = new SpecifiedPrimaryKeyJoinColumnContainerAdapter();
	protected final OrmBaseJoinColumn.Owner primaryKeyJoinColumnOwner;

	protected OrmPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;


	public GenericOrmSecondaryTable(OrmEntity parent, Owner owner, XmlSecondaryTable xmlSecondaryTable) {
		super(parent, owner, xmlSecondaryTable);
		this.primaryKeyJoinColumnOwner = this.buildPrimaryKeyJoinColumnOwner();
		this.initializeSpecifiedPrimaryKeyJoinColumns();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncSpecifiedPrimaryKeyJoinColumns();
		if (this.defaultPrimaryKeyJoinColumn != null) {
			this.defaultPrimaryKeyJoinColumn.synchronizeWithResourceModel();
		}
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getSpecifiedPrimaryKeyJoinColumns());
		this.updateDefaultPrimaryKeyJoinColumn();
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

	public ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.getPrimaryKeyJoinColumns().iterator();
	}

	protected ListIterable<OrmPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.getSpecifiedPrimaryKeyJoinColumns() : this.getDefaultPrimaryKeyJoinColumns();
	}

	public int primaryKeyJoinColumnsSize() {
		return this.hasSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumnsSize() : this.getDefaultPrimaryKeyJoinColumnsSize();
	}


	// ********** specified primary key join columns **********

	public ListIterator<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return this.getSpecifiedPrimaryKeyJoinColumns().iterator();
	}

	public ListIterable<OrmPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
		return new LiveCloneListIterable<OrmPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}

	protected boolean hasSpecifiedPrimaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.size() != 0;
	}

	public OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn() {
		return this.addSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.size());
	}

	public OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		XmlPrimaryKeyJoinColumn xmlJoinColumn = this.buildXmlPrimaryKeyJoinColumn();
		OrmPrimaryKeyJoinColumn joinColumn = this.addSpecifiedPrimaryKeyJoinColumn_(index, xmlJoinColumn);
		this.xmlSecondaryTable.getPrimaryKeyJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlPrimaryKeyJoinColumn buildXmlPrimaryKeyJoinColumn() {
		return OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn();
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn joinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(joinColumn));
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		this.removeSpecifiedPrimaryKeyJoinColumn_(index);
		this.xmlSecondaryTable.getPrimaryKeyJoinColumns().remove(index);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(int index) {
		this.removeItemFromList(index, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
		this.xmlSecondaryTable.getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
	}

	protected void initializeSpecifiedPrimaryKeyJoinColumns() {
		for (XmlPrimaryKeyJoinColumn xmlJoinColumn : this.getXmlPrimaryKeyJoinColumns()) {
			this.specifiedPrimaryKeyJoinColumns.add(this.buildPrimaryKeyJoinColumn(xmlJoinColumn));
		}
	}

	protected void syncSpecifiedPrimaryKeyJoinColumns() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedPrimaryKeyJoinColumnContainerAdapter);
	}

	protected Iterable<XmlPrimaryKeyJoinColumn> getXmlPrimaryKeyJoinColumns() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlPrimaryKeyJoinColumn>(this.xmlSecondaryTable.getPrimaryKeyJoinColumns());
	}

	protected void moveSpecifiedPrimaryKeyJoinColumn_(int index, OrmPrimaryKeyJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}

	protected OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn_(int index, XmlPrimaryKeyJoinColumn xmlJoinColumn) {
		OrmPrimaryKeyJoinColumn joinColumn = this.buildPrimaryKeyJoinColumn(xmlJoinColumn);
		this.addItemToList(index, joinColumn, this.specifiedPrimaryKeyJoinColumns, SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
		return joinColumn;
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(OrmPrimaryKeyJoinColumn joinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn_(this.specifiedPrimaryKeyJoinColumns.indexOf(joinColumn));
	}

	/**
	 * specified primary key join column container adapter
	 */
	protected class SpecifiedPrimaryKeyJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<OrmPrimaryKeyJoinColumn, XmlPrimaryKeyJoinColumn>
	{
		public Iterable<OrmPrimaryKeyJoinColumn> getContextElements() {
			return GenericOrmSecondaryTable.this.getSpecifiedPrimaryKeyJoinColumns();
		}
		public Iterable<XmlPrimaryKeyJoinColumn> getResourceElements() {
			return GenericOrmSecondaryTable.this.getXmlPrimaryKeyJoinColumns();
		}
		public XmlPrimaryKeyJoinColumn getResourceElement(OrmPrimaryKeyJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
		public void moveContextElement(int index, OrmPrimaryKeyJoinColumn element) {
			GenericOrmSecondaryTable.this.moveSpecifiedPrimaryKeyJoinColumn_(index, element);
		}
		public void addContextElement(int index, XmlPrimaryKeyJoinColumn resourceElement) {
			GenericOrmSecondaryTable.this.addSpecifiedPrimaryKeyJoinColumn_(index, resourceElement);
		}
		public void removeContextElement(OrmPrimaryKeyJoinColumn element) {
			GenericOrmSecondaryTable.this.removeSpecifiedPrimaryKeyJoinColumn_(element);
		}
	}

	protected OrmBaseJoinColumn.Owner buildPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}


	// ********** default primary key join column **********

	public OrmPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		return this.defaultPrimaryKeyJoinColumn;
	}

	protected void setDefaultPrimaryKeyJoinColumn(OrmPrimaryKeyJoinColumn joinColumn) {
		OrmPrimaryKeyJoinColumn old = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_PRIMARY_KEY_JOIN_COLUMN, old, joinColumn);
	}

	protected ListIterable<OrmPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
		return (this.defaultPrimaryKeyJoinColumn != null) ?
				new SingleElementListIterable<OrmPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn) :
				EmptyListIterable.<OrmPrimaryKeyJoinColumn>instance();
	}

	protected int getDefaultPrimaryKeyJoinColumnsSize() {
		return (this.defaultPrimaryKeyJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultPrimaryKeyJoinColumn() {
		if (this.buildsDefaultPrimaryKeyJoinColumn()) {
			if (this.defaultPrimaryKeyJoinColumn == null) {
				this.setDefaultPrimaryKeyJoinColumn(this.buildPrimaryKeyJoinColumn(null));
			} else {
				this.defaultPrimaryKeyJoinColumn.update();
			}
		} else {
			this.setDefaultPrimaryKeyJoinColumn(null);
		}
	}

	protected boolean buildsDefaultPrimaryKeyJoinColumn() {
		return ! this.hasSpecifiedPrimaryKeyJoinColumns();
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
		return false;
	}

	public void initializeFrom(ReadOnlySecondaryTable oldSecondaryTable) {
		super.initializeFrom(oldSecondaryTable);
		for (ReadOnlyPrimaryKeyJoinColumn pkJoinColumn : CollectionTools.iterable(oldSecondaryTable.specifiedPrimaryKeyJoinColumns())) {
			this.addSpecifiedPrimaryKeyJoinColumn().initializeFrom(pkJoinColumn);
		}
	}

	protected OrmPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn xmlJoinColumn) {
		return this.getContextNodeFactory().buildOrmPrimaryKeyJoinColumn(this, this.primaryKeyJoinColumnOwner, xmlJoinColumn);
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
			for (OrmPrimaryKeyJoinColumn pkJoinColumn : this.getPrimaryKeyJoinColumns()) {
				pkJoinColumn.validate(messages, reporter);
			}
		}
	}


	// ********** primary key join column owner adapter **********

	protected class PrimaryKeyJoinColumnOwner
		implements OrmBaseJoinColumn.Owner
	{
		protected OrmEntity getEntity() {
			return GenericOrmSecondaryTable.this.getEntity();
		}

		public TypeMapping getTypeMapping() {
			return this.getEntity();
		}

		public String getDefaultTableName() {
			return GenericOrmSecondaryTable.this.getName();
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
			return GenericOrmSecondaryTable.this.getDbTable();
		}

		public int joinColumnsSize() {
			return GenericOrmSecondaryTable.this.primaryKeyJoinColumnsSize();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericOrmSecondaryTable.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}

		public Table getReferencedColumnDbTable() {
			return this.getTypeMapping().getPrimaryDbTable();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmSecondaryTable.this.getValidationTextRange();
		}

		protected boolean isSecondaryTableVirtual() {
			return GenericOrmSecondaryTable.this.isVirtual();
		}

		protected String getSecondaryTableName() {
			return GenericOrmSecondaryTable.this.getName();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new SecondaryTablePrimaryKeyJoinColumnValidator(GenericOrmSecondaryTable.this, (BaseJoinColumn) column, this, (BaseJoinColumnTextRangeResolver) textRangeResolver);
		}
	}
}

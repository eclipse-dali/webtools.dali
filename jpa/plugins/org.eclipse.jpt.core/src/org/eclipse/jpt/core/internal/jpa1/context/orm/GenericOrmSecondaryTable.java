/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmBaseJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmTable;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * orm.xml secondary table
 */
public class GenericOrmSecondaryTable
	extends AbstractOrmTable
	implements OrmSecondaryTable
{
	protected XmlSecondaryTable secondaryTable;
	
	protected final Vector<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns = new Vector<OrmPrimaryKeyJoinColumn>();
	
	protected OrmPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;

	protected final OrmBaseJoinColumn.Owner joinColumnOwner;


	public GenericOrmSecondaryTable(OrmEntity parent, XmlSecondaryTable xmlSecondaryTable) {
		super(parent);
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.initialize(xmlSecondaryTable);
	}
	
	protected OrmBaseJoinColumn.Owner buildJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	public void initializeFrom(SecondaryTable oldSecondaryTable) {
		super.initializeFrom(oldSecondaryTable);
		for (PrimaryKeyJoinColumn oldPkJoinColumn : CollectionTools.iterable(oldSecondaryTable.specifiedPrimaryKeyJoinColumns())) {
			OrmPrimaryKeyJoinColumn newPkJoinColumn = addSpecifiedPrimaryKeyJoinColumn(specifiedPrimaryKeyJoinColumnsSize());
			newPkJoinColumn.initializeFrom(oldPkJoinColumn);
		}
	}
	
	@Override
	public OrmEntity getParent() {
		return (OrmEntity) super.getParent();
	}
	
	public OrmEntity getOrmEntity() {
		return getParent();
	}
	
	public OrmPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		return this.defaultPrimaryKeyJoinColumn;
	}
	
	protected void setDefaultPrimaryKeyJoinColumn(OrmPrimaryKeyJoinColumn newPkJoinColumn) {
		OrmPrimaryKeyJoinColumn oldPkJoinColumn = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = newPkJoinColumn;
		firePropertyChanged(SecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldPkJoinColumn, newPkJoinColumn);
	}
	
	public ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.containsSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumns() : this.defaultPrimaryKeyJoinColumns();
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.containsSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumnsSize() : this.defaultPrimaryKeyJoinColumnsSize();
	}

	public ListIterator<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<OrmPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}
	
	protected ListIterator<OrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		if (this.defaultPrimaryKeyJoinColumn != null) {
			return new SingleElementListIterator<OrmPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn);
		}
		return EmptyListIterator.instance();
	}
	
	protected int defaultPrimaryKeyJoinColumnsSize() {
		return (this.defaultPrimaryKeyJoinColumn == null) ? 0 : 1;
	}
	
	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
		return !this.specifiedPrimaryKeyJoinColumns.isEmpty();
	}	

	public OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		OrmPrimaryKeyJoinColumn oldDefaultPkJoinColumn = this.getDefaultPrimaryKeyJoinColumn();
		if (oldDefaultPkJoinColumn != null) {
			//null the default join column now if one already exists.
			//if one does not exist, there is already a specified join column.
			//Remove it now so that it doesn't get removed during an update and
			//cause change notifications to be sent to the UI in the wrong order
			this.defaultPrimaryKeyJoinColumn = null;
		}
		XmlPrimaryKeyJoinColumn resourcePkJoinColumn = OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn();
		OrmPrimaryKeyJoinColumn contextPkJoinColumn = buildPrimaryKeyJoinColumn(resourcePkJoinColumn);
		this.specifiedPrimaryKeyJoinColumns.add(index, contextPkJoinColumn);
		this.secondaryTable.getPrimaryKeyJoinColumns().add(index, resourcePkJoinColumn);
		
		this.fireItemAdded(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, contextPkJoinColumn);
		if (oldDefaultPkJoinColumn != null) {
			this.firePropertyChanged(SecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldDefaultPkJoinColumn, null);
		}
		return contextPkJoinColumn;
	}
	
	protected void addSpecifiedPrimaryKeyJoinColumn(int index, OrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	protected void addSpecifiedPrimaryKeyJoinColumn(OrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		this.addSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.size(), primaryKeyJoinColumn);
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn pkJoinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(pkJoinColumn));
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		OrmPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		if (!containsSpecifiedPrimaryKeyJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultPrimaryKeyJoinColumn = buildPrimaryKeyJoinColumn(null);
		}
		this.secondaryTable.getPrimaryKeyJoinColumns().remove(index);
		fireItemRemoved(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
		if (this.defaultPrimaryKeyJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(Entity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, null, this.defaultPrimaryKeyJoinColumn);
		}
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(OrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedPrimaryKeyJoinColumns, targetIndex, sourceIndex);
		this.secondaryTable.getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}

	
	public boolean isVirtual() {
		return getOrmEntity().containsVirtualSecondaryTable(this);
	}
	
	@Override
	protected XmlSecondaryTable getResourceTable() {
		return this.secondaryTable;
	}

	@Override
	protected XmlSecondaryTable addResourceTable() {
		//secondaryTables are part of a collection, the secondary-table element will be removed/added
		//when the XmlSecondaryTable is removed/added to the XmlEntity collection
		throw new IllegalStateException("resource table is missing"); //$NON-NLS-1$
	}
	
	@Override
	protected void removeResourceTable() {
		//secondaryTables are part of a collection, the secondary-table element will be removed/added
		//when the XmlSecondaryTable is removed/added to the XmlEntity collection
	}

	protected void initialize(XmlSecondaryTable xst) {
		this.secondaryTable = xst;
		super.initialize(xst);
		this.initializeSpecifiedPrimaryKeyJoinColumns();
		this.initializeDefaultPrimaryKeyJoinColumn();
	}
	
	protected void initializeSpecifiedPrimaryKeyJoinColumns() {
		for (XmlPrimaryKeyJoinColumn resourcePkJoinColumn : this.secondaryTable.getPrimaryKeyJoinColumns()) {
			this.specifiedPrimaryKeyJoinColumns.add(buildPrimaryKeyJoinColumn(resourcePkJoinColumn));
		}
	}
	
	protected boolean shouldBuildDefaultPrimaryKeyJoinColumn() {
		return !containsSpecifiedPrimaryKeyJoinColumns();
	}

	protected void initializeDefaultPrimaryKeyJoinColumn() {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			return;
		}
		this.defaultPrimaryKeyJoinColumn = buildPrimaryKeyJoinColumn(null);
	}	

	public void update(XmlSecondaryTable xst) {
		this.secondaryTable = xst;
		super.update(xst);
		this.updateSpecifiedPrimaryKeyJoinColumns();
		this.updateDefaultPrimaryKeyJoinColumn();
	}
		
	protected void updateSpecifiedPrimaryKeyJoinColumns() {
		// make a copy of the XML PK join columns (to prevent ConcurrentModificationException)
		Iterator<XmlPrimaryKeyJoinColumn> xmlPkJoinColumns = new CloneIterator<XmlPrimaryKeyJoinColumn>(this.secondaryTable.getPrimaryKeyJoinColumns());
		
		for (Iterator<OrmPrimaryKeyJoinColumn> contextPkJoinColumns = this.specifiedPrimaryKeyJoinColumns(); contextPkJoinColumns.hasNext(); ) {
			OrmPrimaryKeyJoinColumn contextPkJoinColumn = contextPkJoinColumns.next();
			if (xmlPkJoinColumns.hasNext()) {
				contextPkJoinColumn.update(xmlPkJoinColumns.next());
			}
			else {
				removeSpecifiedPrimaryKeyJoinColumn_(contextPkJoinColumn);
			}
		}
		
		while (xmlPkJoinColumns.hasNext()) {
			addSpecifiedPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn(xmlPkJoinColumns.next()));
		}
	}
	
	protected void updateDefaultPrimaryKeyJoinColumn() {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			setDefaultPrimaryKeyJoinColumn(null);
			return;
		}
		if (getDefaultPrimaryKeyJoinColumn() == null) {
			this.setDefaultPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn(null));
		}
		else {
			this.defaultPrimaryKeyJoinColumn.update(null);
		}
	}	

	protected OrmPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn resourcePkJoinColumn) {
		return getXmlContextNodeFactory().buildOrmPrimaryKeyJoinColumn(this, this.joinColumnOwner, resourcePkJoinColumn);
	}

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

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.connectionProfileIsActive()) {
			this.validateAgainstDatabase(messages);
		}
		for (Iterator<OrmPrimaryKeyJoinColumn> stream = this.primaryKeyJoinColumns(); stream.hasNext(); ) {
			stream.next().validate(messages, reporter);
		}
	}

	protected void validateAgainstDatabase(List<IMessage> messages) {
		if ( ! this.hasResolvedCatalog()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_CATALOG,
					new String[] {this.getCatalog(), this.getName()}, 
					this,
					this.getCatalogTextRange()
				)
			);
			return;
		}
		
		if ( ! this.hasResolvedSchema()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_SCHEMA,
					new String[] {this.getSchema(), this.getName()}, 
					this,
					this.getSchemaTextRange()
				)
			);
			return;
		}
		
		if ( ! this.isResolved()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_NAME,
					new String[] {this.getName()}, 
					this, 
					this.getNameTextRange()
				)
			);
			return;
		}
	}


	// ********** pk join column owner adapter **********

	class PrimaryKeyJoinColumnOwner
		implements OrmBaseJoinColumn.Owner
	{
		public TypeMapping getTypeMapping() {
			return GenericOrmSecondaryTable.this.getOrmEntity();
		}

		public String getDefaultTableName() {
			return GenericOrmSecondaryTable.this.getName();
		}

		public Table getDbTable(String tableName) {
			return GenericOrmSecondaryTable.this.getDbTable();
		}

		public Table getReferencedColumnDbTable() {
			return getTypeMapping().getPrimaryDbTable();
		}

		public int joinColumnsSize() {
			return GenericOrmSecondaryTable.this.primaryKeyJoinColumnsSize();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericOrmSecondaryTable.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}
		
		public String getDefaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			Entity parentEntity = getOrmEntity().getParentEntity();
			return (parentEntity == null) ? getOrmEntity().getPrimaryKeyColumnName() : parentEntity.getPrimaryKeyColumnName();
	}
		
		public TextRange getValidationTextRange() {
			return null;
		}

	}

}

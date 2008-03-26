/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmBaseJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmSecondaryTable extends AbstractOrmTable
	implements OrmSecondaryTable
{
	protected XmlSecondaryTable secondaryTable;
	
	protected final List<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
	
	protected OrmPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;

	public GenericOrmSecondaryTable(OrmEntity parent, XmlSecondaryTable xmlSecondaryTable) {
		super(parent);
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<OrmPrimaryKeyJoinColumn>();
		initialize(xmlSecondaryTable);
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
	
	public OrmEntity ormEntity() {
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
		XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumnImpl();
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn = createPrimaryKeyJoinColumn(xmlPrimaryKeyJoinColumn);
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		this.secondaryTable.getPrimaryKeyJoinColumns().add(index, xmlPrimaryKeyJoinColumn);
		
		this.fireItemAdded(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		if (oldDefaultPkJoinColumn != null) {
			this.firePropertyChanged(SecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldDefaultPkJoinColumn, null);
		}
		return primaryKeyJoinColumn;
	}
	
	protected OrmBaseJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, OrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
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
			this.defaultPrimaryKeyJoinColumn = createPrimaryKeyJoinColumn(null);
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
		return ormEntity().containsVirtualSecondaryTable(this);
	}
	
	@Override
	protected void addTableResource() {
		//secondaryTables are part of a collection, the secondary-table element will be removed/added
		//when the XmlSecondaryTable is removed/added to the XmlEntity collection
	}
	
	@Override
	protected void removeTableResource() {
		//secondaryTables are part of a collection, the secondary-table element will be removed/added
		//when the XmlSecondaryTable is removed/added to the XmlEntity collection
	}

	@Override
	protected XmlSecondaryTable table() {
		return this.secondaryTable;
	}

	protected void initialize(XmlSecondaryTable secondaryTable) {
		this.secondaryTable = secondaryTable;
		super.initialize(secondaryTable);
		this.initializeSpecifiedPrimaryKeyJoinColumns(secondaryTable);
		this.initializeDefaultPrimaryKeyJoinColumn();
	}
	
	protected void initializeSpecifiedPrimaryKeyJoinColumns(XmlSecondaryTable secondaryTable) {
		for (XmlPrimaryKeyJoinColumn primaryKeyJoinColumn : secondaryTable.getPrimaryKeyJoinColumns()) {
			this.specifiedPrimaryKeyJoinColumns.add(createPrimaryKeyJoinColumn(primaryKeyJoinColumn));
		}
	}
	
	protected boolean shouldBuildDefaultPrimaryKeyJoinColumn() {
		return !containsSpecifiedPrimaryKeyJoinColumns();
	}

	protected void initializeDefaultPrimaryKeyJoinColumn() {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			return;
		}
		this.defaultPrimaryKeyJoinColumn = createPrimaryKeyJoinColumn(null);
	}	

	public void update(XmlSecondaryTable secondaryTable) {
		this.secondaryTable = secondaryTable;
		super.update(secondaryTable);
		this.updateSpecifiedPrimaryKeyJoinColumns(secondaryTable);
		this.updateDefaultPrimaryKeyJoinColumn();
	}
		
	protected void updateSpecifiedPrimaryKeyJoinColumns(XmlSecondaryTable secondaryTable) {
		ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<XmlPrimaryKeyJoinColumn> resourcePrimaryKeyJoinColumns = secondaryTable.getPrimaryKeyJoinColumns().listIterator();
		
		while (primaryKeyJoinColumns.hasNext()) {
			OrmPrimaryKeyJoinColumn primaryKeyJoinColumn = primaryKeyJoinColumns.next();
			if (resourcePrimaryKeyJoinColumns.hasNext()) {
				primaryKeyJoinColumn.update(resourcePrimaryKeyJoinColumns.next());
			}
			else {
				removeSpecifiedPrimaryKeyJoinColumn_(primaryKeyJoinColumn);
			}
		}
		
		while (resourcePrimaryKeyJoinColumns.hasNext()) {
			addSpecifiedPrimaryKeyJoinColumn(specifiedPrimaryKeyJoinColumnsSize(), createPrimaryKeyJoinColumn(resourcePrimaryKeyJoinColumns.next()));
		}
	}
	
	protected void updateDefaultPrimaryKeyJoinColumn() {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			setDefaultPrimaryKeyJoinColumn(null);
			return;
		}
		if (getDefaultPrimaryKeyJoinColumn() == null) {
			this.setDefaultPrimaryKeyJoinColumn(createPrimaryKeyJoinColumn(null));
		}
		else {
			this.defaultPrimaryKeyJoinColumn.update(null);
		}
	}	

	protected OrmPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn) {
		OrmPrimaryKeyJoinColumn ormPrimaryKeyJoinColumn = jpaFactory().buildOrmPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		ormPrimaryKeyJoinColumn.initialize(xmlPrimaryKeyJoinColumn);
		return ormPrimaryKeyJoinColumn;
	}

	@Override
	//no default name for secondaryTables
	protected String defaultName() {
		return null;
	}

	@Override
	protected String defaultCatalog() {
		return getEntityMappings().getCatalog();
	}

	@Override
	protected String defaultSchema() {
		return getEntityMappings().getSchema();
	}
	
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		addTableMessages(messages);
		
		for (OrmPrimaryKeyJoinColumn pkJoinColumn : CollectionTools.iterable(this.primaryKeyJoinColumns())) {
			pkJoinColumn.addToMessages(messages);
		}
	}
	
	protected void addTableMessages(List<IMessage> messages) {
		boolean doContinue = connectionProfileIsActive();
		String schema = getSchema();
		
		if (doContinue && ! hasResolvedSchema()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, getName()}, 
						this,
						schemaTextRange())
				);
			doContinue = false;
		}
		
		if (doContinue && ! isResolved()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_NAME,
						new String[] {getName()}, 
						this, 
						nameTextRange())
				);
		}
	}
	
	class PrimaryKeyJoinColumnOwner implements OrmBaseJoinColumn.Owner
	{

		public TypeMapping getTypeMapping() {
			return GenericOrmSecondaryTable.this.ormEntity();
		}

		public Table getDbTable(String tableName) {
			return GenericOrmSecondaryTable.this.getDbTable();
		}

		public Table dbReferencedColumnTable() {
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
			return ormEntity().parentEntity().primaryKeyColumnName();
		}
		
		public TextRange validationTextRange() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}

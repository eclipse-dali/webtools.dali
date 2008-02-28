/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmSecondaryTable extends AbstractOrmTable
	implements OrmSecondaryTable
{
	protected XmlSecondaryTable secondaryTable;
	
	protected final List<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
	
	protected final List<OrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	public GenericOrmSecondaryTable(OrmEntity parent) {
		super(parent);
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<OrmPrimaryKeyJoinColumn>();
		this.defaultPrimaryKeyJoinColumns = new ArrayList<OrmPrimaryKeyJoinColumn>();
//		this.getDefaultPrimaryKeyJoinColumns().add(this.createPrimaryKeyJoinColumn(0));
	}
	
	@Override
	public OrmEntity parent() {
		return (OrmEntity) super.parent();
	}
	
	public OrmEntity ormEntity() {
		return parent();
	}

	public ListIterator<OrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		return new CloneListIterator<OrmPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumns);
	}

	public OrmPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ? this.defaultPrimaryKeyJoinColumns() : this.specifiedPrimaryKeyJoinColumns();
	}
	
	public ListIterator<OrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<OrmPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}
	
	public int defaultPrimaryKeyJoinColumnsSize() {
		return this.defaultPrimaryKeyJoinColumns.size();
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.containsSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumnsSize() : this.defaultPrimaryKeyJoinColumnsSize();
	}
	
	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
		return !this.specifiedPrimaryKeyJoinColumns.isEmpty();
	}	

	public OrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().buildOrmPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		this.secondaryTable.getPrimaryKeyJoinColumns().add(index, OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn());
		this.fireItemAdded(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		return primaryKeyJoinColumn;
	}
	
	protected AbstractJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
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
		this.secondaryTable.getPrimaryKeyJoinColumns().remove(index);
		fireItemRemoved(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
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

	public void initialize(XmlSecondaryTable secondaryTable) {
		this.secondaryTable = secondaryTable;
		super.initialize(secondaryTable);
		this.initializeSpecifiedPrimaryKeyJoinColumns(secondaryTable);
	}
	
	protected void initializeSpecifiedPrimaryKeyJoinColumns(XmlSecondaryTable secondaryTable) {
		for (XmlPrimaryKeyJoinColumn primaryKeyJoinColumn : secondaryTable.getPrimaryKeyJoinColumns()) {
			this.specifiedPrimaryKeyJoinColumns.add(createPrimaryKeyJoinColumn(primaryKeyJoinColumn));
		}
	}
	
	public void update(XmlSecondaryTable secondaryTable) {
		this.secondaryTable = secondaryTable;
		super.update(secondaryTable);
		this.updateSpecifiedPrimaryKeyJoinColumns(secondaryTable);
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
	
	protected OrmPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		OrmPrimaryKeyJoinColumn ormPrimaryKeyJoinColumn = jpaFactory().buildOrmPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		ormPrimaryKeyJoinColumn.initialize(primaryKeyJoinColumn);
		return ormPrimaryKeyJoinColumn;
	}

	@Override
	//no default name for secondaryTables
	protected String defaultName() {
		return null;
	}

	@Override
	protected String defaultCatalog() {
		return entityMappings().getCatalog();
	}

	@Override
	protected String defaultSchema() {
		return entityMappings().getSchema();
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
		boolean doContinue = isConnected();
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
	
	class PrimaryKeyJoinColumnOwner implements AbstractJoinColumn.Owner
	{

		public TypeMapping typeMapping() {
			return GenericOrmSecondaryTable.this.ormEntity();
		}

		public Table dbTable(String tableName) {
			return GenericOrmSecondaryTable.this.dbTable();
		}

		public Table dbReferencedColumnTable() {
			return typeMapping().primaryDbTable();
		}

		public int joinColumnsSize() {
			return GenericOrmSecondaryTable.this.primaryKeyJoinColumnsSize();
		}
		
		public boolean isVirtual(AbstractJoinColumn joinColumn) {
			return GenericOrmSecondaryTable.this.defaultPrimaryKeyJoinColumns.contains(joinColumn);
		}
		
		public String defaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return ormEntity().parentEntity().primaryKeyColumnName();

		}
	}
}

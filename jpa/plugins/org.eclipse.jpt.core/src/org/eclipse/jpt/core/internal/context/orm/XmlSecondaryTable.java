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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.AbstractTable;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.orm.SecondaryTable;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public class XmlSecondaryTable extends AbstractXmlTable
	implements ISecondaryTable
{
	protected SecondaryTable secondaryTable;
	
	protected final List<XmlPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
	
	protected final List<XmlPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	protected XmlSecondaryTable(XmlEntity parent) {
		super(parent);
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<XmlPrimaryKeyJoinColumn>();
		this.defaultPrimaryKeyJoinColumns = new ArrayList<XmlPrimaryKeyJoinColumn>();
//		this.getDefaultPrimaryKeyJoinColumns().add(this.createPrimaryKeyJoinColumn(0));
	}
	
	public XmlEntity xmlEntity() {
		return (XmlEntity) super.parent();
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		return new CloneListIterator<XmlPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumns);
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ? this.defaultPrimaryKeyJoinColumns() : this.specifiedPrimaryKeyJoinColumns();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<XmlPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<XmlPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}

	public XmlPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		XmlPrimaryKeyJoinColumn primaryKeyJoinColumn = new XmlPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		this.secondaryTable.getPrimaryKeyJoinColumns().add(index, OrmFactory.eINSTANCE.createPrimaryKeyJoinColumn());
		this.fireItemAdded(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		return primaryKeyJoinColumn;
	}
	
	protected IAbstractJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, XmlPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		XmlPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		this.secondaryTable.getPrimaryKeyJoinColumns().remove(index);
		fireItemRemoved(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int oldIndex, int newIndex) {
		this.secondaryTable.getPrimaryKeyJoinColumns().move(newIndex, oldIndex);
		moveItemInList(newIndex, oldIndex, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);		
	}

	
	public boolean isVirtual() {
		return xmlEntity().containsVirtualSecondaryTable(this);
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
	protected AbstractTable table() {
		return this.secondaryTable;
	}

	public void initialize(SecondaryTable secondaryTable) {
		this.secondaryTable = secondaryTable;
		super.initialize(secondaryTable);
		this.initializeSpecifiedPrimaryKeyJoinColumns(secondaryTable);
	}
	
	protected void initializeSpecifiedPrimaryKeyJoinColumns(SecondaryTable secondaryTable) {
		for (PrimaryKeyJoinColumn primaryKeyJoinColumn : secondaryTable.getPrimaryKeyJoinColumns()) {
			this.specifiedPrimaryKeyJoinColumns.add(createPrimaryKeyJoinColumn(primaryKeyJoinColumn));
		}
	}
	
	public void update(SecondaryTable secondaryTable) {
		this.secondaryTable = secondaryTable;
		super.update(secondaryTable);
		this.updateSpecifiedPrimaryKeyJoinColumns(secondaryTable);
	}
		
	protected void updateSpecifiedPrimaryKeyJoinColumns(SecondaryTable secondaryTable) {
		ListIterator<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<PrimaryKeyJoinColumn> resourcePrimaryKeyJoinColumns = secondaryTable.getPrimaryKeyJoinColumns().listIterator();
		
		while (primaryKeyJoinColumns.hasNext()) {
			XmlPrimaryKeyJoinColumn primaryKeyJoinColumn = primaryKeyJoinColumns.next();
			if (resourcePrimaryKeyJoinColumns.hasNext()) {
				primaryKeyJoinColumn.update(resourcePrimaryKeyJoinColumns.next());
			}
			else {
				removeSpecifiedPrimaryKeyJoinColumn(primaryKeyJoinColumn);
			}
		}
		
		while (resourcePrimaryKeyJoinColumns.hasNext()) {
			addSpecifiedPrimaryKeyJoinColumn(specifiedPrimaryKeyJoinColumnsSize(), createPrimaryKeyJoinColumn(resourcePrimaryKeyJoinColumns.next()));
		}
	}
	
	protected XmlPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = new XmlPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		xmlPrimaryKeyJoinColumn.initialize(primaryKeyJoinColumn);
		return xmlPrimaryKeyJoinColumn;
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
	
	class PrimaryKeyJoinColumnOwner implements IAbstractJoinColumn.Owner
	{
		public ITextRange validationTextRange(CompilationUnit astRoot) {
			//TODO textRange
			return null;//return XmlSecondaryTable.this.validationTextRange(astRoot);
		}

		public ITypeMapping typeMapping() {
			return XmlSecondaryTable.this.xmlEntity();
		}

		public Table dbTable(String tableName) {
			return XmlSecondaryTable.this.dbTable();
		}

		public Table dbReferencedColumnTable() {
			return typeMapping().primaryDbTable();
		}

		public int joinColumnsSize() {
			return CollectionTools.size(XmlSecondaryTable.this.primaryKeyJoinColumns());
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return XmlSecondaryTable.this.defaultPrimaryKeyJoinColumns.contains(joinColumn);
		}
		
		public int indexOf(IAbstractJoinColumn joinColumn) {
			return CollectionTools.indexOf(XmlSecondaryTable.this.primaryKeyJoinColumns(), joinColumn);
		}
		
		public String defaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return xmlEntity().parentEntity().primaryKeyColumnName();

		}
	}
}

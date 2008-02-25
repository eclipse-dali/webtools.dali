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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAbstractTable;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;


public class GenericOrmSecondaryTable extends AbstractOrmTable
	implements SecondaryTable
{
	protected XmlSecondaryTable secondaryTable;
	
	protected final List<GenericOrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
	
	protected final List<GenericOrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	protected GenericOrmSecondaryTable(GenericOrmEntity parent) {
		super(parent);
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<GenericOrmPrimaryKeyJoinColumn>();
		this.defaultPrimaryKeyJoinColumns = new ArrayList<GenericOrmPrimaryKeyJoinColumn>();
//		this.getDefaultPrimaryKeyJoinColumns().add(this.createPrimaryKeyJoinColumn(0));
	}
	
	@Override
	public GenericOrmEntity parent() {
		return (GenericOrmEntity) super.parent();
	}
	
	public GenericOrmEntity xmlEntity() {
		return parent();
	}

	public ListIterator<GenericOrmPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		return new CloneListIterator<GenericOrmPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumns);
	}

	public PrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ? this.defaultPrimaryKeyJoinColumns() : this.specifiedPrimaryKeyJoinColumns();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<GenericOrmPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<GenericOrmPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
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

	public GenericOrmPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		GenericOrmPrimaryKeyJoinColumn primaryKeyJoinColumn = new GenericOrmPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		this.secondaryTable.getPrimaryKeyJoinColumns().add(index, OrmFactory.eINSTANCE.createPrimaryKeyJoinColumn());
		this.fireItemAdded(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		return primaryKeyJoinColumn;
	}
	
	protected AbstractJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, GenericOrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn pkJoinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(pkJoinColumn));
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		GenericOrmPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		this.secondaryTable.getPrimaryKeyJoinColumns().remove(index);
		fireItemRemoved(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn_(GenericOrmPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedPrimaryKeyJoinColumns, targetIndex, sourceIndex);
		this.secondaryTable.getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
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
	protected XmlAbstractTable table() {
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
		ListIterator<GenericOrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<XmlPrimaryKeyJoinColumn> resourcePrimaryKeyJoinColumns = secondaryTable.getPrimaryKeyJoinColumns().listIterator();
		
		while (primaryKeyJoinColumns.hasNext()) {
			GenericOrmPrimaryKeyJoinColumn primaryKeyJoinColumn = primaryKeyJoinColumns.next();
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
	
	protected GenericOrmPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(XmlPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		GenericOrmPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = new GenericOrmPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
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
	
	class PrimaryKeyJoinColumnOwner implements AbstractJoinColumn.Owner
	{
		public TextRange validationTextRange(CompilationUnit astRoot) {
			//TODO textRange
			return null;//return XmlSecondaryTable.this.validationTextRange(astRoot);
		}

		public TypeMapping typeMapping() {
			return GenericOrmSecondaryTable.this.xmlEntity();
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
			return xmlEntity().parentEntity().primaryKeyColumnName();

		}
	}
}

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

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.core.internal.resource.orm.AbstractTable;
import org.eclipse.jpt.core.internal.resource.orm.SecondaryTable;


public class XmlSecondaryTable extends AbstractXmlTable
	implements ISecondaryTable
{
	protected SecondaryTable secondaryTable;
	
//	protected EList<IPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;
//
//	protected EList<IPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns;

	protected XmlSecondaryTable(XmlEntity parent) {
		super(parent);
//		this.getDefaultPrimaryKeyJoinColumns().add(this.createPrimaryKeyJoinColumn(0));
	}
	
	public XmlEntity xmlEntity() {
		return (XmlEntity) super.parent();
	}

//
//	public EList<IPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
//		return this.getSpecifiedPrimaryKeyJoinColumns().isEmpty() ? this.getDefaultPrimaryKeyJoinColumns() : this.getSpecifiedPrimaryKeyJoinColumns();
//	}
//
//	public EList<IPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns() {
//		if (specifiedPrimaryKeyJoinColumns == null) {
//			specifiedPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, OrmPackage.XML_SECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS);
//		}
//		return specifiedPrimaryKeyJoinColumns;
//	}
//
//	public EList<IPrimaryKeyJoinColumn> getDefaultPrimaryKeyJoinColumns() {
//		if (defaultPrimaryKeyJoinColumns == null) {
//			defaultPrimaryKeyJoinColumns = new EObjectContainmentEList<IPrimaryKeyJoinColumn>(IPrimaryKeyJoinColumn.class, this, OrmPackage.XML_SECONDARY_TABLE__DEFAULT_PRIMARY_KEY_JOIN_COLUMNS);
//		}
//		return defaultPrimaryKeyJoinColumns;
//	}

//	private XmlEntityInternal entity() {
//		return (XmlEntityInternal) eContainer();
//	}
//	
//	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
//		return !this.getSpecifiedPrimaryKeyJoinColumns().isEmpty();
//	}
//
//	public IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index) {
//		return OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn(new ISecondaryTable.PrimaryKeyJoinColumnOwner(this));
//	}
	
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
	}
	
	public void update(SecondaryTable secondaryTable) {
		this.secondaryTable = secondaryTable;
		super.update(secondaryTable);
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

	public IPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends IPrimaryKeyJoinColumn> ListIterator<T> defaultPrimaryKeyJoinColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public void moveSpecifiedPrimaryKeyJoinColumn(int oldIndex, int newIndex) {
		// TODO Auto-generated method stub
		
	}

	public <T extends IPrimaryKeyJoinColumn> ListIterator<T> primaryKeyJoinColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		// TODO Auto-generated method stub
		
	}

	public <T extends IPrimaryKeyJoinColumn> ListIterator<T> specifiedPrimaryKeyJoinColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	public int specifiedPrimaryKeyJoinColumnsSize() {
		// TODO Auto-generated method stub
		return 0;
	}
}

/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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

import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class GenericOrmUniqueConstraint
	extends AbstractXmlContextNode
	implements OrmUniqueConstraint
{
	
	protected final List<String> columnNames;
	
	protected XmlUniqueConstraint resourceUniqueConstraint;
	
	protected Owner owner;
	
	public GenericOrmUniqueConstraint(XmlContextNode parent, Owner owner, XmlUniqueConstraint resourceUniqueConstraint) {
		super(parent);
		this.owner = owner;
		this.columnNames = new ArrayList<String>();
		this.initialize(resourceUniqueConstraint);
	}
	
	public ListIterator<String> columnNames() {
		return new CloneListIterator<String>(this.columnNames);
	}

	public int columnNamesSize() {
		return this.columnNames.size();
	}

	public void addColumnName(int index, String columnName) {
		this.columnNames.add(index, columnName);
		this.resourceUniqueConstraint.getColumnNames().add(index, columnName);
		fireItemAdded(COLUMN_NAMES_LIST, index, columnName);		
	}	
	
	protected void addColumnName_(int index, String columnName) {
		this.addItemToList(index, columnName, this.columnNames, COLUMN_NAMES_LIST);
	}	

	protected void addColumnName_(String columnName) {
		this.addItemToList(columnName, this.columnNames, COLUMN_NAMES_LIST);
	}	

	protected void setColumnName_(int index, String columnName) {
		this.setItemInList(index, columnName, this.columnNames, COLUMN_NAMES_LIST);
	}	

	public void removeColumnName(String columnName) {
		this.removeColumnName(this.columnNames.indexOf(columnName));
	}

	public void removeColumnName(int index) {
		String removedColumnName = this.columnNames.remove(index);
		this.resourceUniqueConstraint.getColumnNames().remove(index);
		fireItemRemoved(COLUMN_NAMES_LIST, index, removedColumnName);
	}
	
	protected void removeColumnName_(int index) {
		this.removeItemFromList(index, this.columnNames, COLUMN_NAMES_LIST);
	}

	public void moveColumnName(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.columnNames, targetIndex, sourceIndex);
		this.resourceUniqueConstraint.getColumnNames().move(targetIndex, sourceIndex);
		fireItemMoved(COLUMN_NAMES_LIST, targetIndex, sourceIndex);		
	}

	public TextRange getValidationTextRange() {
		return this.resourceUniqueConstraint.getValidationTextRange();
	}
	
	protected void initialize(XmlUniqueConstraint xmlUniqueConstraint) {
		this.resourceUniqueConstraint = xmlUniqueConstraint;
		this.initializeColumnNames();
	}
	
	protected void initializeColumnNames() {
		for (String annotationColumnName : this.resourceUniqueConstraint.getColumnNames()) {
			this.columnNames.add(annotationColumnName);
		}
	}
	
	public void update(XmlUniqueConstraint xmlUniqueConstraint) {
		this.resourceUniqueConstraint = xmlUniqueConstraint;
		this.updateColumnNames();
	}
	
	protected void updateColumnNames() {
		int index = 0;
		for (String xmlColumnName : this.resourceUniqueConstraint.getColumnNames()) {
			if (this.columnNames.size() > index) {
				if ( ! this.columnNames.get(index).equals(xmlColumnName)) {
					this.setColumnName_(index, xmlColumnName);
				}
			}
			else {
				this.addColumnName_(xmlColumnName);			
			}
			index++;
		}
		
		while (index < this.columnNames.size()) {
			this.removeColumnName_(index);
		}
	}
}

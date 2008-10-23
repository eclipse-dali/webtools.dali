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
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.internal.context.persistence.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class GenericOrmUniqueConstraint extends AbstractXmlContextNode
	implements OrmUniqueConstraint
{
	
	protected final List<String> columnNames;
	
	protected XmlUniqueConstraint resourceUniqueConstraint;
	
	protected UniqueConstraint.Owner owner;
	
	public GenericOrmUniqueConstraint(XmlContextNode parent, UniqueConstraint.Owner owner, XmlUniqueConstraint resourceUniqueConstraint) {
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
		fireItemAdded(UniqueConstraint.COLUMN_NAMES_LIST, index, columnName);		
	}	
	
	protected void addColumnName_(int index, String columnName) {
		this.columnNames.add(index, columnName);
		fireItemAdded(UniqueConstraint.COLUMN_NAMES_LIST, index, columnName);		
	}	

	public void removeColumnName(String columnName) {
		this.removeColumnName(this.columnNames.indexOf(columnName));
	}

	public void removeColumnName(int index) {
		String removedColumnName = this.columnNames.remove(index);
		this.resourceUniqueConstraint.getColumnNames().remove(index);
		fireItemRemoved(UniqueConstraint.COLUMN_NAMES_LIST, index, removedColumnName);
	}
	
	protected void removeColumnName_(int index) {
		String removedColumnName = this.columnNames.remove(index);
		fireItemRemoved(UniqueConstraint.COLUMN_NAMES_LIST, index, removedColumnName);
	}

	public void moveColumnName(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.columnNames, targetIndex, sourceIndex);
		this.resourceUniqueConstraint.getColumnNames().move(targetIndex, sourceIndex);
		fireItemMoved(UniqueConstraint.COLUMN_NAMES_LIST, targetIndex, sourceIndex);		
	}

	public TextRange getValidationTextRange() {
		return this.resourceUniqueConstraint.getValidationTextRange();
	}
	
	protected void initialize(XmlUniqueConstraint resourceUniqueConstraint) {
		this.resourceUniqueConstraint = resourceUniqueConstraint;
		this.initializeColumnNames();
	}
	
	protected void initializeColumnNames() {
		ListIterator<String> xmlColumnNames = new CloneListIterator<String>(this.resourceUniqueConstraint.getColumnNames());
		
		for (String annotationColumnName : CollectionTools.iterable(xmlColumnNames)) {
			this.columnNames.add(annotationColumnName);
		}
	}
	
	public void update(XmlUniqueConstraint resourceUniqueConstraint) {
		this.resourceUniqueConstraint = resourceUniqueConstraint;
		this.updateColumnNames();
	}
	
	protected void updateColumnNames() {
		ListIterator<String> xmlColumnNames = new CloneListIterator<String>(this.resourceUniqueConstraint.getColumnNames());
		
		int index = 0;
		for (String xmlColumnName : CollectionTools.iterable(xmlColumnNames)) {
			if (columnNamesSize() > index) {
				if (this.columnNames.get(index) != xmlColumnName) {
					addColumnName_(index, xmlColumnName);
				}
			}
			else {
				addColumnName_(index, xmlColumnName);			
			}
			index++;
		}
		
		for ( ; index < columnNamesSize(); ) {
			removeColumnName_(index);
		}
	}
}

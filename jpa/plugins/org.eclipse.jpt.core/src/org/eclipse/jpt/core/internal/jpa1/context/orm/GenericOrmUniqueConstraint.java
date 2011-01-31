/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmReadOnlyUniqueConstraint;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;

public class GenericOrmUniqueConstraint
	extends AbstractOrmReadOnlyUniqueConstraint
	implements OrmUniqueConstraint
{
	protected Owner owner;
	protected final XmlUniqueConstraint xmlUniqueConstraint;


	public GenericOrmUniqueConstraint(XmlContextNode parent, Owner owner, XmlUniqueConstraint xmlUniqueConstraint) {
		super(parent);
		this.owner = owner;
		this.xmlUniqueConstraint = xmlUniqueConstraint;
		this.initializeColumnNames();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncColumnNames();
	}


	// ********** column names **********

	public void addColumnName(String columnName) {
		this.addColumnName(this.columnNames.size(), columnName);
	}

	public void addColumnName(int index, String columnName) {
		this.addItemToList(index, columnName, this.columnNames, COLUMN_NAMES_LIST);
		this.xmlUniqueConstraint.getColumnNames().add(index, columnName);
	}

	public void removeColumnName(String columnName) {
		this.removeColumnName(this.columnNames.indexOf(columnName));
	}

	public void removeColumnName(int index) {
		this.removeItemFromList(index, this.columnNames, COLUMN_NAMES_LIST);
		this.xmlUniqueConstraint.getColumnNames().remove(index);
	}

	public void moveColumnName(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.columnNames, COLUMN_NAMES_LIST);
		this.xmlUniqueConstraint.getColumnNames().move(targetIndex, sourceIndex);
	}

	protected void initializeColumnNames() {
		for (String xmlColumnName : this.xmlUniqueConstraint.getColumnNames()) {
			this.columnNames.add(xmlColumnName);
		}
	}

	@Override
	protected Iterable<String> getResourceColumnNames() {
		return this.xmlUniqueConstraint.getColumnNames();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.xmlUniqueConstraint.getValidationTextRange();
	}


	// ********** misc **********

	public XmlUniqueConstraint getXmlUniqueConstraint() {
		return this.xmlUniqueConstraint;
	}

	public void initializeFrom(ReadOnlyUniqueConstraint oldUniqueConstraint) {
		for (String columnName : oldUniqueConstraint.getColumnNames()) {
			this.addColumnName(columnName);
		}
	}
}

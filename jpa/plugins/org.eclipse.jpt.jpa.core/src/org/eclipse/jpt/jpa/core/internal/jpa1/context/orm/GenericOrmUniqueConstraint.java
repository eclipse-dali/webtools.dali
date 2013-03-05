/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.resource.orm.XmlUniqueConstraint;

public class GenericOrmUniqueConstraint
	extends AbstractOrmReadOnlyUniqueConstraint
	implements OrmUniqueConstraint
{
	protected Owner owner;
	protected final XmlUniqueConstraint xmlUniqueConstraint;


	public GenericOrmUniqueConstraint(JpaContextModel parent, Owner owner, XmlUniqueConstraint xmlUniqueConstraint) {
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
		TextRange textRange = this.xmlUniqueConstraint.getValidationTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}

	public boolean isEquivalentTo(UniqueConstraint uniqueConstraint) {
		return  columnNamesAreEquivalent(uniqueConstraint);
	}

	protected boolean columnNamesAreEquivalent(UniqueConstraint uniqueConstraint) {
		if (this.getColumnNamesSize() != uniqueConstraint.getColumnNamesSize()) {
			return false;
		} 

		for (int i=0; i<this.getColumnNamesSize(); i++) {
			if (! ObjectTools.equals(this.columnNames.get(i), uniqueConstraint.getColumnName(i))) {
				return false;
			}
		}
		return true;
	}

	// ********** completion proposals **********

	@Override
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.columnNamesTouches(pos)) {
			return this.getCandidateColumnNames();
		}
		return null;
	}

	protected boolean columnNamesTouches(int pos) {
		return this.xmlUniqueConstraint.columnNamesTouches(pos);
	}

	protected Iterable<String> getCandidateColumnNames() {
		return this.owner.getCandidateUniqueConstraintColumnNames();
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

	public void convertFrom(JavaUniqueConstraint javaUniqueConstraint) {
		this.initializeFrom(javaUniqueConstraint);
	}
}

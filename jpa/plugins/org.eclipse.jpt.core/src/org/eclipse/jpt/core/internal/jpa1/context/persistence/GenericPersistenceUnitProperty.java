/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.persistence;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceXmlContextNode;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * Straightforward implementation of the persistence unit property.
 * Notifies the persistence unit of any changes to the property.
 */
public class GenericPersistenceUnitProperty
	extends AbstractPersistenceXmlContextNode
	implements PersistenceUnit.Property
{
	protected final XmlProperty xmlProperty;
	protected String name;
	protected String value;


	public GenericPersistenceUnitProperty(PersistenceUnit parent, XmlProperty xmlProperty) {
		super(parent);
		this.xmlProperty = xmlProperty;
		this.name = xmlProperty.getName();
		this.value = xmlProperty.getValue();
	}

	@Override
	public PersistenceUnit getParent() {
		return (PersistenceUnit) super.getParent();
	}

	public XmlProperty getXmlProperty() {
		return this.xmlProperty;
	}
	
	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		if (attributeValueHasChanged(old, name)) {
			this.xmlProperty.setName(name);
			this.firePropertyChanged(NAME_PROPERTY, old, name);
			getParent().propertyNameChanged(old, this.name, this.value);
		}
	}


	// ********** value **********

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		String old = this.value;
		this.value = value;
		if (attributeValueHasChanged(old, value)) {
			this.xmlProperty.setValue(value);
			this.firePropertyChanged(VALUE_PROPERTY, old, value);
			getParent().propertyValueChanged(this.name, value);
		}
	}

	protected String getValuePackageName() {
		int packageEnd = this.value == null ? -1 : this.value.lastIndexOf('.');
		if (packageEnd == -1 ) {
			return null;
		}
		return this.value.substring(0, packageEnd);
	}


	// ********** updating **********

	public void update() {
		this.setName(this.xmlProperty.getName());
		this.setValue(this.xmlProperty.getValue());
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.xmlProperty.getValidationTextRange();
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		if (this.getValue() != null && this.getValue().equals(originalType.getFullyQualifiedName('.'))) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenameTypeEdit(originalType, newName));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createRenameTypeEdit(IType originalType, String newName) {
		return this.xmlProperty.createRenameTypeEdit(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.getValue() != null && this.getValue().equals(originalType.getFullyQualifiedName('.'))) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newPackage.getElementName()));
		}
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		String packageName = getValuePackageName();
		if (packageName != null && packageName.equals(originalPackage.getElementName())) {
			return new SingleElementIterable<ReplaceEdit>(this.createRenamePackageEdit(newName));
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createRenamePackageEdit(String newName) {
		return this.xmlProperty.createRenamePackageEdit(newName);
	}


	// ********** misc **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
		sb.append(" = "); //$NON-NLS-1$
		sb.append(this.value);
	}

}

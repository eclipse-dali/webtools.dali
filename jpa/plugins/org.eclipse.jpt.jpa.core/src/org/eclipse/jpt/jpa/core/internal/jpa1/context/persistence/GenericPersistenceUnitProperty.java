/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlContextModel;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlProperty;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * Straightforward implementation of the persistence unit property.
 * Notifies the persistence unit of any changes to the property.
 */
public class GenericPersistenceUnitProperty
	extends AbstractPersistenceXmlContextModel<PersistenceUnit>
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

	public XmlProperty getXmlProperty() {
		return this.xmlProperty;
	}


	// ********** synchronize **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setName_(this.xmlProperty.getName());
		this.setValue_(this.xmlProperty.getValue());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.setName_(name);
		this.xmlProperty.setName(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		if (this.firePropertyChanged(NAME_PROPERTY, old, name)) {
			this.getPersistenceUnit().propertyNameChanged(old, name, this.value);
		}
	}


	// ********** value **********

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.setValue_(value);
		this.xmlProperty.setValue(value);
	}

	protected void setValue_(String value) {
		String old = this.value;
		this.value = value;
		if (this.firePropertyChanged(VALUE_PROPERTY, old, value)) {
			this.getPersistenceUnit().propertyValueChanged(this.name, value);
		}
	}

	protected String getValuePackageName() {
		return (this.value == null) ? null : this.getValuePackageName_();
	}

	/**
	 * pre-condition: {@link #value} is not <code>null</code>
	 */
	protected String getValuePackageName_() {
		int lastPeriod = this.value.lastIndexOf('.');
		return (lastPeriod == -1) ? null : this.value.substring(0, lastPeriod);
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlProperty.getValidationTextRange();
		return (textRange != null) ? textRange : this.getPersistenceUnit().getValidationTextRange();
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return ObjectTools.equals(this.value, originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenameTypeEdit(originalType, newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	protected ReplaceEdit createRenameTypeEdit(IType originalType, String newName) {
		return this.xmlProperty.createRenameTypeEdit(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return ObjectTools.equals(this.value, originalType.getFullyQualifiedName('.')) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newPackage.getElementName())) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return ObjectTools.equals(this.getValuePackageName(), originalPackage.getElementName()) ?
				IterableTools.singletonIterable(this.createRenamePackageEdit(newName)) :
				IterableTools.<ReplaceEdit>emptyIterable();
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

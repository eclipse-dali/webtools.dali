/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;


public class GenericProperty extends AbstractPersistenceJpaContextNode
	implements Property
{
	protected String name;
	
	protected String value;
	
	protected XmlProperty property;
	
	public GenericProperty(PersistenceUnit parent) {
		super(parent);
	}
	
	// **************** name ***************************************************
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.property.setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	
	// **************** value **************************************************
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		this.property.setValue(newValue);
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	
	// **************** updating ***********************************************
	
	public void initialize(XmlProperty property) {
		this.property = property;
		this.name = property.getName();
		this.value = property.getValue();
	}
	
	public void update(XmlProperty property) {
		this.property = property;
		setName(property.getName());
		setValue(property.getValue());
	}
	
	// **************** validation ***********************************************
	
	public TextRange validationTextRange() {
		return this.property.validationTextRange();
	}
	
	// **************** toString
	@Override
	public void toString(StringBuilder sb)
	{
		sb.append(" (name: ");
		sb.append(name);
		sb.append(", value: ");
		sb.append(value);
		sb.append(')');
	}

}

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

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;


public class GenericProperty extends AbstractJpaContextNode
	implements Property
{
	protected String name;
	
	protected String value;
	
	
	public GenericProperty(PersistenceUnit parent) {
		super(parent);
	}
	
	// **************** name ***************************************************
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	
	// **************** value **************************************************
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	
	// **************** updating ***********************************************
	
	public void initialize(XmlProperty property) {
		name = property.getName();
		value = property.getValue();
	}
	
	public void update(XmlProperty property) {
		setName(property.getName());
		setValue(property.getValue());
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

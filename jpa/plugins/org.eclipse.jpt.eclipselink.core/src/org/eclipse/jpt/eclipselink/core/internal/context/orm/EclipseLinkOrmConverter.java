/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.utility.internal.StringTools;

public abstract class EclipseLinkOrmConverter<T extends XmlNamedConverter>
	extends AbstractXmlContextNode implements EclipseLinkConverter
{
	protected T resourceConverter;
	
	protected String name;
	
	
	protected EclipseLinkOrmConverter(XmlContextNode parent, T xmlResource) {
		super(parent);
		initialize(xmlResource);
	}
		
	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}
	
	
	protected T getXmlResource() {
		return this.resourceConverter;
	}

	// **************** name ***************************************************
	
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.resourceConverter.setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	
	// **************** resource interaction ***********************************
	
	protected void initialize(T resource) {
		this.resourceConverter = resource;
		this.name = getResourceName();
		getPersistenceUnit().addConverter(this);
	}
	
	public void update() {
		this.setName_(getResourceName());
		getPersistenceUnit().addConverter(this);
	}
	
	protected String getResourceName() {
		return this.resourceConverter.getName();
	}
	
	
	// **************** validation *********************************************
	
	public boolean overrides(EclipseLinkConverter converter) {
		if (getName() == null) {
			return false;
		}
		// this isn't ideal, but it will have to do until we have further adopter input
		return this.getName().equals(converter.getName()) && converter instanceof EclipseLinkJavaConverter;
	}
	
	public boolean duplicates(EclipseLinkConverter converter) {
		return (this != converter)
				&& ! StringTools.stringIsEmpty(this.name)
				&& this.name.equals(converter.getName())
				&& ! this.overrides(converter)
				&& ! converter.overrides(this);
	}
	
	public TextRange getValidationTextRange() {
		return this.resourceConverter.getValidationTextRange();
	}
}
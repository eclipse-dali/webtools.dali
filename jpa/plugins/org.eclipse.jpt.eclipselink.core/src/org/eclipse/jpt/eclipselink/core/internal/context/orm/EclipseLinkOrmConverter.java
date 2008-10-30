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
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.utility.internal.StringTools;

public abstract class EclipseLinkOrmConverter
	extends AbstractXmlContextNode implements EclipseLinkConverter
{
	private XmlNamedConverter xmlResource;
	
	private String name;
	
	
	public EclipseLinkOrmConverter(XmlContextNode parent, XmlNamedConverter xmlResource) {
		super(parent);
		initialize(xmlResource);
	}
	
	
	protected XmlNamedConverter getXmlResource() {
		return this.xmlResource;
	}
	
	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}
	
	
	// **************** name ***************************************************
	
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		getXmlResource().setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	
	// **************** resource interaction ***********************************
	
	protected void initialize(XmlNamedConverter resource) {
		this.xmlResource = resource;
		this.name = calculateName();
		getPersistenceUnit().addConverter(this);
	}
	
	public void update() {
		this.setName_(calculateName());
		getPersistenceUnit().addConverter(this);
	}
	
	protected String calculateName() {
		return getXmlResource().getName();
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
		return getXmlResource().getValidationTextRange();
	}
}
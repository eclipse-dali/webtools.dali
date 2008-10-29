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
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedConverter;

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
	}
	
	public void update() {
		this.setName_(calculateName());
	}
	
	protected String calculateName() {
		return getXmlResource().getName();
	}
	
	
	// **************** validation *********************************************
	
	public TextRange getValidationTextRange() {
		return getXmlResource().getValidationTextRange();
	}
}
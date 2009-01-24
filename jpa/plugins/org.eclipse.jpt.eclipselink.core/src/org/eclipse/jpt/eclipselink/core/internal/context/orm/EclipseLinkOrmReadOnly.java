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

import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.ReadOnly;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly;

public class EclipseLinkOrmReadOnly extends AbstractXmlContextNode
	implements ReadOnly
{
	protected XmlReadOnly resource;
	
	protected boolean defaultReadOnly;
	
	protected Boolean specifiedReadOnly;
	
	
	public EclipseLinkOrmReadOnly(OrmTypeMapping parent) {
		super(parent);
	}
	
	
	public boolean isReadOnly() {
		return (this.specifiedReadOnly == null) ? this.defaultReadOnly : this.specifiedReadOnly.booleanValue();
	}
	
	public boolean isDefaultReadOnly() {
		return this.defaultReadOnly;
	}
	
	public void setDefaultReadOnly(boolean newValue) {
		boolean oldValue = this.defaultReadOnly;
		this.defaultReadOnly = newValue;
		firePropertyChanged(DEFAULT_READ_ONLY_PROPERTY, oldValue, newValue);
	}
	
	public Boolean getSpecifiedReadOnly() {
		return this.specifiedReadOnly;
	}
	
	public void setSpecifiedReadOnly(Boolean newSpecifiedReadOnly) {
		Boolean oldSpecifiedReadOnly = this.specifiedReadOnly;
		this.specifiedReadOnly = newSpecifiedReadOnly;
		this.resource.setReadOnly(newSpecifiedReadOnly);
		firePropertyChanged(SPECIFIED_READ_ONLY_PROPERTY, oldSpecifiedReadOnly, newSpecifiedReadOnly);
	}
	
	
	// **************** initialize/update **************************************
	
	protected void initialize(XmlReadOnly resource, ReadOnly javaReadOnly) {
		this.resource = resource;
		this.defaultReadOnly = this.getJavaReadOnly(javaReadOnly);
		this.specifiedReadOnly = this.getResourceReadOnly();
	}
	
	protected void update(ReadOnly javaReadOnly) {
		setDefaultReadOnly(this.getJavaReadOnly(javaReadOnly));
		setSpecifiedReadOnly(this.getResourceReadOnly());
	}
	
	protected boolean getJavaReadOnly(ReadOnly javaReadOnly) {
		return (javaReadOnly == null) ? false : javaReadOnly.isReadOnly();
	}
	
	protected Boolean getResourceReadOnly() {
		return this.resource.getReadOnly();
	}
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		return this.resource.getReadOnlyTextRange();
	}
}

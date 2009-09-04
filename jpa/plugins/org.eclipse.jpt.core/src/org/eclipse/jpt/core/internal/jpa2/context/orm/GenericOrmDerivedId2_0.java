/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlDerivedId;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmDerivedId2_0 
	extends AbstractXmlContextNode
	implements OrmDerivedId2_0
{
	protected XmlDerivedId resource;
	
	protected boolean value;
	
	
	public GenericOrmDerivedId2_0(OrmSingleRelationshipMapping2_0 parent, XmlDerivedId resource) {
		super(parent);
		this.resource = resource;
		this.value = getResourceToContextValue();
	}
	
	
	@Override
	public OrmSingleRelationshipMapping2_0 getParent() {
		return (OrmSingleRelationshipMapping2_0) super.getParent();
	}
	
	public boolean getValue() {
		return this.value;
	}
	
	public void setValue(boolean newValue) {
		boolean oldValue = this.value;
		this.value = newValue;
		this.resource.setId(getContextToResourceValue());
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	protected void setValue_(boolean newValue) {
		boolean oldValue = this.value;
		this.value = newValue;
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	public void update() {
		setValue_(getResourceToContextValue());
	}
	
	protected boolean getResourceToContextValue() {
		return (resource.getId() == null) ? false : resource.getId().booleanValue();
	}
	
	protected Boolean getContextToResourceValue() {
		return (this.value) ? Boolean.TRUE : null;
	}
	
	public TextRange getValidationTextRange() {
		return this.resource.getDerivedIdTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// no validation rules
	}
}

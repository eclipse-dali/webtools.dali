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
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmIdDerivedIdentityStrategy2_0 
	extends AbstractOrmXmlContextNode
	implements OrmIdDerivedIdentityStrategy2_0
{
	protected XmlDerivedId_2_0 resource;
	
	protected boolean value;
	
	
	public GenericOrmIdDerivedIdentityStrategy2_0(
			OrmDerivedIdentity2_0 parent, XmlDerivedId_2_0 resource) {
		super(parent);
		this.resource = resource;
		this.value = getResourceToContextValue();
	}
	
	
	public OrmDerivedIdentity2_0 getDerivedIdentity() {
		return (OrmDerivedIdentity2_0) getParent();
	}
	
	public OrmSingleRelationshipMapping2_0 getMapping() {
		return getDerivedIdentity().getMapping();
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
	
	public boolean isSpecified() {
		return Boolean.TRUE.equals(this.resource.getId());
	}
	
	public void addStrategy() {
		this.resource.setId(true);
	}
	
	public void removeStrategy() {
		this.resource.setId(null);
	}
	
	public void initializeFrom(OrmIdDerivedIdentityStrategy2_0 oldStrategy) {
		setValue(oldStrategy.getValue());
	}
	
	public TextRange getValidationTextRange() {
		return this.resource.getIdTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// no validation rules
	}
}

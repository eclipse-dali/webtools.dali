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
import org.eclipse.jpt.core.jpa2.context.orm.OrmMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmMapsIdDerivedIdentityStrategy2_0
	extends AbstractOrmXmlContextNode
	implements OrmMapsIdDerivedIdentityStrategy2_0
{
	protected XmlMapsId_2_0 resource;
	
	protected String value;
	
	
	public GenericOrmMapsIdDerivedIdentityStrategy2_0(
			OrmDerivedIdentity2_0 parent, XmlMapsId_2_0 resource) {
		super(parent);
		this.resource = resource;
		this.value = this.resource.getMapsId();
	}
	
	
	public OrmDerivedIdentity2_0 getDerivedIdentity() {
		return (OrmDerivedIdentity2_0) getParent();
	}
	
	public OrmSingleRelationshipMapping2_0 getMapping() {
		return getDerivedIdentity().getMapping();
	}
	
	public String getSpecifiedValue() {
		return this.value;
	}
	
	public void setSpecifiedValue(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		this.resource.setMapsId(this.value);
		firePropertyChanged(SPECIFIED_VALUE_PROPERTY, oldValue, newValue);
	}
	
	protected void setSpecifiedValue_(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		firePropertyChanged(SPECIFIED_VALUE_PROPERTY, oldValue, newValue);
	}
	
	public String getDefaultValue() {
		// there is no way to have default values in xml
		return null;
	}
	
	public String getValue() {
		// there is never a default value
		return this.value;
	}
	
	public Iterable<String> getSortedValueChoices() {
		// TODO
		return EmptyIterable.<String>instance();
	}
	
	public void update() {
		setSpecifiedValue_(this.resource.getMapsId());
	}
	
	public boolean isSpecified() {
		return this.resource.getMapsId() != null;
	}
	
	public void addStrategy() {
		this.resource.setMapsId("");	
	}
	
	public void removeStrategy() {
		this.resource.setMapsId(null);
	}
	
	public void initializeFrom(OrmMapsIdDerivedIdentityStrategy2_0 oldStrategy) {
		setSpecifiedValue(oldStrategy.getSpecifiedValue());
	}
	
	public TextRange getValidationTextRange() {
		return this.resource.getMapsIdTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// no validation rules
	}
}

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
import org.eclipse.jpt.core.jpa2.context.orm.OrmMapsId2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmMapsId2_0
	extends AbstractOrmXmlContextNode
	implements OrmMapsId2_0
{
	protected XmlMapsId_2_0 resource;
	
	protected String value;
	
	
	public GenericOrmMapsId2_0(OrmSingleRelationshipMapping2_0 parent, XmlMapsId_2_0 resource) {
		super(parent);
		this.resource = resource;
		this.value = this.resource.getMapsId();
	}
	
	
	@Override
	public OrmSingleRelationshipMapping2_0 getParent() {
		return (OrmSingleRelationshipMapping2_0) super.getParent();
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		this.resource.setMapsId(this.value);
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	protected void setValue_(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	public void update() {
		setValue_(this.resource.getMapsId());
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

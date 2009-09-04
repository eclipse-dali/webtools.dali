/*******************************************************************************
 *  Copyright (c) 2008, 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch;

public class OrmEclipseLinkJoinFetch extends AbstractOrmXmlContextNode
	implements EclipseLinkJoinFetch
{
	protected XmlJoinFetch resource;
	
	protected EclipseLinkJoinFetchType joinFetchValue;
	
	
	public OrmEclipseLinkJoinFetch(OrmAttributeMapping parent, XmlJoinFetch resource) {
		super(parent);
		this.resource = resource;
		this.joinFetchValue = getResourceJoinFetch();
	}
	
	
	public EclipseLinkJoinFetchType getValue() {
		return this.joinFetchValue;
	}
	
	public void setValue(EclipseLinkJoinFetchType newJoinFetchValue) {
		EclipseLinkJoinFetchType oldJoinFetchValue = this.joinFetchValue;
		this.joinFetchValue = newJoinFetchValue;
		this.resource.setJoinFetch(EclipseLinkJoinFetchType.toOrmResourceModel(newJoinFetchValue));
		firePropertyChanged(VALUE_PROPERTY, oldJoinFetchValue, newJoinFetchValue);
	}
	
	protected void setValue_(EclipseLinkJoinFetchType newJoinFetchValue) {
		EclipseLinkJoinFetchType oldJoinFetchValue = this.joinFetchValue;
		this.joinFetchValue = newJoinFetchValue;
		firePropertyChanged(VALUE_PROPERTY, oldJoinFetchValue, newJoinFetchValue);
	}
	
	
	// **************** initialize/update **************************************
	
	protected void update() {
		setValue_(getResourceJoinFetch());
	}
	
	protected EclipseLinkJoinFetchType getResourceJoinFetch() {
		return EclipseLinkJoinFetchType.fromOrmResourceModel(this.resource.getJoinFetch());
	}
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		return this.resource.getJoinFetchTextRange();
	}
}

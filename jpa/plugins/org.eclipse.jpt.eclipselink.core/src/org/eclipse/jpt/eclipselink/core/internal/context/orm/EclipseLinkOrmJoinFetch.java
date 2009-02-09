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
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch;

public class EclipseLinkOrmJoinFetch extends AbstractXmlContextNode
	implements JoinFetch
{
	protected XmlJoinFetch resource;
	
	protected JoinFetchType joinFetchValue;
	
	
	public EclipseLinkOrmJoinFetch(OrmAttributeMapping parent, XmlJoinFetch resource) {
		super(parent);
		this.resource = resource;
		this.joinFetchValue = getResourceJoinFetch();
	}
	
	
	public JoinFetchType getValue() {
		return this.joinFetchValue;
	}
	
	public void setValue(JoinFetchType newJoinFetchValue) {
		JoinFetchType oldJoinFetchValue = this.joinFetchValue;
		this.joinFetchValue = newJoinFetchValue;
		this.resource.setJoinFetch(JoinFetchType.toOrmResourceModel(newJoinFetchValue));
		firePropertyChanged(VALUE_PROPERTY, oldJoinFetchValue, newJoinFetchValue);
	}
	
	protected void setValue_(JoinFetchType newJoinFetchValue) {
		JoinFetchType oldJoinFetchValue = this.joinFetchValue;
		this.joinFetchValue = newJoinFetchValue;
		firePropertyChanged(VALUE_PROPERTY, oldJoinFetchValue, newJoinFetchValue);
	}
	
	
	// **************** initialize/update **************************************
	
	protected void update() {
		setValue_(getResourceJoinFetch());
	}
	
	protected JoinFetchType getResourceJoinFetch() {
		return JoinFetchType.fromOrmResourceModel(this.resource.getJoinFetch());
	}
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		return this.resource.getJoinFetchTextRange();
	}
}

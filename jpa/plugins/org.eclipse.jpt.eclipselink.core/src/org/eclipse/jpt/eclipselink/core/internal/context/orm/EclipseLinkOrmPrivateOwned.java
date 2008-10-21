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

import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.internal.context.persistence.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned;

public class EclipseLinkOrmPrivateOwned extends AbstractXmlContextNode
	implements PrivateOwned
{
	protected XmlPrivateOwned resource;
	
	protected boolean privateOwned;
	
	
	public EclipseLinkOrmPrivateOwned(OrmAttributeMapping parent) {
		super(parent);
	}
	
	
	public boolean isPrivateOwned() {
		return this.privateOwned;
	}
	
	public void setPrivateOwned(boolean newPrivateOwned) {
		boolean oldPrivateOwned = this.privateOwned;
		this.privateOwned = newPrivateOwned;
		this.resource.setPrivateOwned(newPrivateOwned);
		firePropertyChanged(PRIVATE_OWNED_PROPERTY, oldPrivateOwned, newPrivateOwned);
	}
	
	
	// **************** initialize/update **************************************
	
	protected void initialize(XmlPrivateOwned xmlPrivateOwned) {
		this.resource = xmlPrivateOwned;
		this.privateOwned = resource.isPrivateOwned();
	}
	
	protected void update(XmlPrivateOwned xmlPrivateOwned) {
		this.resource = xmlPrivateOwned;
		setPrivateOwned(resource.isPrivateOwned());
	}
	
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		return this.resource.getPrivateOwnedTextRange();
	}
}

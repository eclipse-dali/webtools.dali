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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned;

public class EclipseLinkOrmPrivateOwned extends AbstractXmlContextNode
	implements EclipseLinkPrivateOwned
{
	protected final XmlPrivateOwned resource;
	
	protected boolean privateOwned;
	
	
	public EclipseLinkOrmPrivateOwned(OrmAttributeMapping parent, XmlPrivateOwned resource) {
		super(parent);
		this.resource = resource;
		this.privateOwned = this.getResourcePrivateOwned();
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
	
	protected void setPrivateOwned_(boolean newPrivateOwned) {
		boolean oldPrivateOwned = this.privateOwned;
		this.privateOwned = newPrivateOwned;
		firePropertyChanged(PRIVATE_OWNED_PROPERTY, oldPrivateOwned, newPrivateOwned);
	}
	
	
	// **************** initialize/update **************************************
	
	protected void update() {
		setPrivateOwned_(this.getResourcePrivateOwned());
	}
	
	protected boolean getResourcePrivateOwned() {
		return this.resource.isPrivateOwned();
	}
	
	// **************** validation **************************************
	
	public TextRange getValidationTextRange() {
		return this.resource.getPrivateOwnedTextRange();
	}
}

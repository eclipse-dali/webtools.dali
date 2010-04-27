/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlOverride;
import org.eclipse.jpt.core.utility.TextRange;


public class AbstractOrmOverride extends AbstractOrmXmlContextNode
{

	protected String name;

	protected final BaseOverride.Owner owner;

	protected XmlOverride resourceOverride;

	public AbstractOrmOverride(XmlContextNode parent, BaseOverride.Owner owner, XmlOverride resourceOverride) {
		super(parent);
		this.owner = owner;
		this.resourceOverride = resourceOverride;
		this.name = getResourceName();
	}
	
	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}

	public BaseOverride.Owner getOwner() {
		return this.owner;
	}
	
	protected XmlOverride getResourceOverride() {
		return this.resourceOverride;
	}

	protected void update(XmlOverride resourceOverride) {
		this.resourceOverride = resourceOverride;
		this.setName_(this.getResourceName());
	}
	
	
	// ********************* name ****************
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.resourceOverride.setName(newName);
		firePropertyChanged(BaseOverride.NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(BaseOverride.NAME_PROPERTY, oldName, newName);
	}

	protected String getResourceName() {
		return this.resourceOverride.getName();
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.resourceOverride.getValidationTextRange();
		return textRange == null ? getParent().getValidationTextRange() : textRange;
	}

	
	//****************** miscellaneous ********************
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

}

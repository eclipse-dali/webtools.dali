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

import java.util.List;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmOverride;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.context.orm.OrmOverrideTextRangeResolver;
import org.eclipse.jpt.core.resource.orm.XmlOverride;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractOrmOverride extends AbstractOrmXmlContextNode
	implements OrmOverride
{

	protected String name;

	protected final Owner owner;

	protected XmlOverride resourceOverride;

	protected AbstractOrmOverride(XmlContextNode parent, Owner owner, XmlOverride resourceOverride) {
		super(parent);
		this.owner = owner;
		this.resourceOverride = resourceOverride;
		this.name = getResourceName();
	}
	
	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}

	public Owner getOwner() {
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
	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.buildValidator().validate(messages, reporter);
	}

	protected JptValidator buildValidator() {
		return this.getOwner().buildValidator(this, buildTextRangeResolver());
	}

	protected OverrideTextRangeResolver buildTextRangeResolver() {
		return new OrmOverrideTextRangeResolver(this);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.resourceOverride.getValidationTextRange();
		return textRange == null ? getParent().getValidationTextRange() : textRange;
	}

	public TextRange getNameTextRange() {
		TextRange textRange = this.resourceOverride.getNameTextRange();
		return (textRange != null) ? textRange : this.getValidationTextRange();
	}

	
	//****************** miscellaneous ********************
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

}

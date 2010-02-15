/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmAssociationOverride extends AbstractOrmXmlContextNode
	implements OrmAssociationOverride
{
	protected final AssociationOverride.Owner owner;

	protected transient XmlAssociationOverride resourceAssociationOverride;

	protected String name;

	protected final OrmAssociationOverrideRelationshipReference relationshipReference;


	public GenericOrmAssociationOverride(OrmAssociationOverrideContainer parent, AssociationOverride.Owner owner, XmlAssociationOverride xmlAssociationOverride) {
		super(parent);
		this.owner = owner;
		this.relationshipReference = buildRelationshipReference(xmlAssociationOverride);
		this.resourceAssociationOverride = xmlAssociationOverride;
		this.name = xmlAssociationOverride.getName();
	}

	@Override
	public OrmAssociationOverrideContainer getParent() {
		return (OrmAssociationOverrideContainer) super.getParent();
	}

	protected OrmAssociationOverrideRelationshipReference buildRelationshipReference(XmlAssociationOverride xmlAssociationOverride) {
		return getXmlContextNodeFactory().buildOrmAssociationOverrideRelationshipReference(this, xmlAssociationOverride);
	}
	
	public void initializeFrom(AssociationOverride oldAssociationOverride) {
		this.setName(oldAssociationOverride.getName());
		this.relationshipReference.initializeFrom(oldAssociationOverride.getRelationshipReference());
	}

	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}
	
	public OrmAssociationOverride setVirtual(boolean virtual) {
		return (OrmAssociationOverride) getOwner().setVirtual(virtual, this);
	}
	
	public Owner getOwner() {
		return this.owner;
	}

	public OrmAssociationOverrideRelationshipReference getRelationshipReference() {
		return this.relationshipReference;
	}
	
	// ********** AssociationOverride implementation **********

	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.resourceAssociationOverride.setName(newName);
		firePropertyChanged(BaseOverride.NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(BaseOverride.NAME_PROPERTY, oldName, newName);
	}

	public void update(XmlAssociationOverride xao) {
		this.resourceAssociationOverride = xao;
		this.setName_(xao.getName());
		this.relationshipReference.update(xao);
	}	


	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.relationshipReference.validate(messages, reporter);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.resourceAssociationOverride.getValidationTextRange();
		return textRange == null ? getParent().getValidationTextRange() : textRange;
	}
}

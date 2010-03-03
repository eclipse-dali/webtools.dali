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
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmAssociationOverride extends AbstractOrmOverride
	implements OrmAssociationOverride
{
	protected final OrmAssociationOverrideRelationshipReference relationshipReference;


	public GenericOrmAssociationOverride(OrmAssociationOverrideContainer parent, AssociationOverride.Owner owner, XmlAssociationOverride xmlAssociationOverride) {
		super(parent, owner, xmlAssociationOverride);
		this.relationshipReference = this.buildRelationshipReference();
	}

	@Override
	public OrmAssociationOverrideContainer getParent() {
		return (OrmAssociationOverrideContainer) super.getParent();
	}

	@Override
	public Owner getOwner() {
		return (Owner) super.getOwner();
	}

	protected OrmAssociationOverrideRelationshipReference buildRelationshipReference() {
		return getXmlContextNodeFactory().buildOrmAssociationOverrideRelationshipReference(this, getResourceOverride());
	}
	
	public void initializeFrom(AssociationOverride oldAssociationOverride) {
		this.setName(oldAssociationOverride.getName());
		this.relationshipReference.initializeFrom(oldAssociationOverride.getRelationshipReference());
	}

	public void update(XmlAssociationOverride xao) {
		super.update(xao);
		this.relationshipReference.update(xao);
	}	

	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}
	
	public OrmAssociationOverride setVirtual(boolean virtual) {
		return (OrmAssociationOverride) getOwner().setVirtual(virtual, this);
	}

	@Override
	protected XmlAssociationOverride getResourceOverride() {
		return (XmlAssociationOverride) super.getResourceOverride();
	}

	public OrmAssociationOverrideRelationshipReference getRelationshipReference() {
		return this.relationshipReference;
	}


	// ********** OrmAssociationOverride implementation **********


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.relationshipReference.validate(messages, reporter);
	}
}

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

import java.util.List;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkOrmOneToManyMapping<T extends XmlOneToMany>
	extends GenericOrmOneToManyMapping<T>
	implements EclipseLinkOneToManyMapping
{
	protected EclipseLinkOrmPrivateOwned privateOwned;
	
	protected EclipseLinkOrmJoinFetch joinFetch;
	
	
	public EclipseLinkOrmOneToManyMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.privateOwned = new EclipseLinkOrmPrivateOwned(this, (XmlPrivateOwned) this.resourceAttributeMapping);
		this.joinFetch = new EclipseLinkOrmJoinFetch(this, (XmlJoinFetch) this.resourceAttributeMapping);
	}
	
	
	@Override
	protected OrmRelationshipReference buildRelationshipReference() {
		return new EclipseLinkOrmOneToManyRelationshipReference(this, this.resourceAttributeMapping);
	}
	
	@Override
	public EclipseLinkOrmOneToManyRelationshipReference getRelationshipReference() {
		return (EclipseLinkOrmOneToManyRelationshipReference) super.getRelationshipReference();
	}
	
	public PrivateOwned getPrivateOwned() {
		return this.privateOwned;
	}
	
	public JoinFetch getJoinFetch() {
		return this.joinFetch;
	}
	
	
	// **************** resource -> context ************************************
	
	@Override
	public void update() {
		super.update();
		this.privateOwned.update();
		this.joinFetch.update();
	}
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// TODO - private owned, join fetch validation
	}
}

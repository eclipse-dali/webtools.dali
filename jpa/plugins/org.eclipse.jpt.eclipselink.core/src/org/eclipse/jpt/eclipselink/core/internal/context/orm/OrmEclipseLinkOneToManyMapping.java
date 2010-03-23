/*******************************************************************************
 *  Copyright (c) 2008, 2010  Oracle. 
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
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.eclipselink.core.v2_0.context.EclipseLinkOneToManyMapping2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkOneToManyMapping extends AbstractOrmOneToManyMapping<XmlOneToMany>
	implements EclipseLinkOneToManyMapping2_0
{
	protected OrmEclipseLinkPrivateOwned privateOwned;
	
	protected OrmEclipseLinkJoinFetch joinFetch;
	
	
	public OrmEclipseLinkOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		super(parent, resourceMapping);
		this.privateOwned = new OrmEclipseLinkPrivateOwned(this, this.resourceAttributeMapping);
		this.joinFetch = new OrmEclipseLinkJoinFetch(this, this.resourceAttributeMapping);
	}
	
	
	@Override
	protected OrmRelationshipReference buildRelationshipReference() {
		return new OrmEclipseLinkOneToManyRelationshipReference(this, this.resourceAttributeMapping);
	}
	
	@Override
	public OrmEclipseLinkOneToManyRelationshipReference getRelationshipReference() {
		return (OrmEclipseLinkOneToManyRelationshipReference) super.getRelationshipReference();
	}
	
	public EclipseLinkPrivateOwned getPrivateOwned() {
		return this.privateOwned;
	}
	
	public EclipseLinkJoinFetch getJoinFetch() {
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

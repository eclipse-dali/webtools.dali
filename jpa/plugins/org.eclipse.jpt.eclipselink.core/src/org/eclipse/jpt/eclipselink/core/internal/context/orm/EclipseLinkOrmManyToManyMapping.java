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
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmManyToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkOrmManyToManyMapping extends AbstractOrmManyToManyMapping<XmlManyToMany>
	implements EclipseLinkRelationshipMapping
{
	protected EclipseLinkOrmJoinFetch joinFetch;
	
	
	public EclipseLinkOrmManyToManyMapping(OrmPersistentAttribute parent, XmlManyToMany resourceMapping) {
		super(parent, resourceMapping);
		this.joinFetch = new EclipseLinkOrmJoinFetch(this, this.resourceAttributeMapping);
	}
	
	
	public JoinFetch getJoinFetch() {
		return this.joinFetch;
	}
	
	
	// **************** resource-context interaction ***************************
	
	@Override
	public void update() {
		super.update();
		this.joinFetch.update();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// TODO - private owned, join fetch validation
	}
}
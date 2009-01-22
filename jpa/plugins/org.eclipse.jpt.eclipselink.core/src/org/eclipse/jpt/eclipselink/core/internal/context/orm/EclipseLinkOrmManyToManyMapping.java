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
import org.eclipse.jpt.core.internal.context.orm.GenericOrmManyToManyMapping;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmManyToManyMapping
	extends GenericOrmManyToManyMapping
	implements EclipseLinkRelationshipMapping
{
	protected EclipseLinkOrmJoinFetch joinFetch;
	
	
	public EclipseLinkOrmManyToManyMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.joinFetch = new EclipseLinkOrmJoinFetch(this);
	}
	
	
	public JoinFetch getJoinFetch() {
		return this.joinFetch;
	}
	
	
	// **************** resource-context interaction ***************************
	
	@Override
	public XmlManyToMany addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlManyToMany manyToMany = EclipseLinkOrmFactory.eINSTANCE.createXmlManyToManyImpl();
		getPersistentAttribute().initialize(manyToMany);
		typeMapping.getAttributes().getManyToManys().add(manyToMany);
		return manyToMany;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.joinFetch.initialize((XmlJoinFetch) this.resourceAttributeMapping);
	}
	
	@Override
	public void update() {
		super.update();
		this.joinFetch.update();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		// TODO - private owned, join fetch validation
	}
}
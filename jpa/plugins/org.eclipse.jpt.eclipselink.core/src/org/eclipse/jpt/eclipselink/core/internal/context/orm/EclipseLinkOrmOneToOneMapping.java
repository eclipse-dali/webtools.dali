/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmOneToOneMapping;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.PrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmOneToOneMapping extends GenericOrmOneToOneMapping
	implements EclipseLinkOneToOneMapping
{
	protected EclipseLinkOrmPrivateOwned privateOwned;
	
	protected EclipseLinkOrmJoinFetch joinFetch;
	
	
	public EclipseLinkOrmOneToOneMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.privateOwned = new EclipseLinkOrmPrivateOwned(this);
		this.joinFetch = new EclipseLinkOrmJoinFetch(this);
	}
	
	
	public PrivateOwned getPrivateOwned() {
		return this.privateOwned;
	}
	
	public JoinFetch getJoinFetch() {
		return this.joinFetch;
	}
	
	
	// **************** resource-context interaction ***************************
	
	@Override
	public XmlOneToOne addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlOneToOne oneToOne = EclipseLinkOrmFactory.eINSTANCE.createXmlOneToOneImpl();
		getPersistentAttribute().initialize(oneToOne);
		typeMapping.getAttributes().getOneToOnes().add(oneToOne);
		return oneToOne;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		this.privateOwned.initialize((XmlPrivateOwned) this.resourceAttributeMapping);
		this.joinFetch.initialize((XmlJoinFetch) this.resourceAttributeMapping);
	}
	
	@Override
	public void update() {
		super.update();
		this.privateOwned.update();
		this.joinFetch.update();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		// TODO - private owned, join fetch validation
	}
}
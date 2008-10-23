/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
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
import org.eclipse.jpt.core.internal.context.orm.GenericOrmVersionMapping;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkVersionMapping;
import org.eclipse.jpt.eclipselink.core.context.Mutable;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmVersionMapping extends GenericOrmVersionMapping
	implements EclipseLinkVersionMapping
{	
	protected EclipseLinkOrmMutable mutable;
	
	
	public EclipseLinkOrmVersionMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.mutable = new EclipseLinkOrmMutable(this);
	}
	
	
	public Mutable getMutable() {
		return this.mutable;
	}
	
	
	// **************** resource-context interaction ***************************
	
	@Override
	public XmlVersion addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlVersion version = EclipseLinkOrmFactory.eINSTANCE.createXmlVersion();
		getPersistentAttribute().initialize(version);
		typeMapping.getAttributes().getVersions().add(version);
		return version;
	}
	
	@Override
	public void initialize(XmlAttributeMapping attributeMapping) {
		super.initialize(attributeMapping);	
		this.mutable.initialize((XmlMutable) this.resourceAttributeMapping);
	}
	
	@Override
	public void update() {
		super.update();
		this.mutable.update();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		// TODO - mutable validation
	}
}

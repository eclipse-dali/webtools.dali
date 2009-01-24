/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEmbeddedIdMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedId;

public class EclipseLinkOrmEmbeddedIdMapping extends GenericOrmEmbeddedIdMapping
{
	
	public EclipseLinkOrmEmbeddedIdMapping(OrmPersistentAttribute parent) {
		super(parent);
	}		
	
	// **************** resource-context interaction ***************************
	
	@Override
	public XmlEmbeddedId addToResourceModel(XmlTypeMapping typeMapping) {
		XmlEmbeddedId embeddedId = EclipseLinkOrmFactory.eINSTANCE.createXmlEmbeddedIdImpl();
		getPersistentAttribute().initialize(embeddedId);
		typeMapping.getAttributes().getEmbeddedIds().add(embeddedId);
		return embeddedId;
	}
}

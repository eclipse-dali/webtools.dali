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
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEmbeddedMapping;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded;

public class EclipseLinkOrmEmbeddedMapping extends GenericOrmEmbeddedMapping
{
	
	public EclipseLinkOrmEmbeddedMapping(OrmPersistentAttribute parent) {
		super(parent);
	}		
	
	// **************** resource-context interaction ***************************
	
	@Override
	public XmlEmbedded addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlEmbedded embedded = EclipseLinkOrmFactory.eINSTANCE.createXmlEmbeddedImpl();
		getPersistentAttribute().initialize(embedded);
		typeMapping.getAttributes().getEmbeddeds().add(embedded);
		return embedded;
	}
}

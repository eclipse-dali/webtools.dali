/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context.orm;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_2.context.orm.EclipseLinkOrmXml2_2Definition;


public class EclipseLinkOrmXml2_3Definition
		extends EclipseLinkOrmXml2_2Definition {
	
	// singleton
	private static final OrmXmlDefinition INSTANCE = new EclipseLinkOrmXml2_3Definition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmXml2_3Definition() {
		super();
	}
	
	
	@Override
	public JptResourceType getResourceType() {
		return JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_3_RESOURCE_TYPE;
	}
}
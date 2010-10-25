/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm.v2_2;

import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;

@SuppressWarnings("nls")
public interface EclipseLink2_2
	extends EclipseLink2_0
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_2.xsd";
	String SCHEMA_VERSION	= "2.2";
	
	
	// EclipseLink 2.2 specific nodes
	String ADDITIONAL_CRITERIA = "additional-criteria";
	String CASCADE_ON_DELETE = "cascade-on-delete";
	String CRITERIA = "criteria";	
	String INDEX = "index";
		String INDEX__COLUMN_NAME = "column-name";
		String INDEX__NAME = "name";
		String INDEX__CATALOG = "catalog";
		String INDEX__SCHEMA = "schema";
		String INDEX__TABLE = "table";
		String INDEX__UNIQUE = "unique";
	String PARENT_CLASS = "parent-class";
}

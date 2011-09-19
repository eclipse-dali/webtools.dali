/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLink2_2;


@SuppressWarnings("nls")
public interface EclipseLink2_4
	extends EclipseLink2_2
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_4.xsd";
	String SCHEMA_VERSION	= "2.4";

	// EclipseLink 2.4 specific nodes
	String CACHE__DATABASE_CHANGE_NOTIFICATION_TYPE = "database-change-notification-type";
	String CACHE_INDEX = "cache-index";
		String CACHE_INDEX__COLUMN_NAME = "column-name";
	String DELETE_ALL = "delete-all";
	String MULTITENANT__INCLUDE_CRITERIA = "include-criteria";


}
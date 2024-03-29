/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License 2.0 which accompanies this distribution, and is
 * available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0;

import org.eclipse.jpt.jpa.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;

@SuppressWarnings("nls")
public interface EclipseLink2_0
	extends EclipseLink1_1, JPA2_0
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_0.xsd";
	String SCHEMA_VERSION	= "2.0";
	
	// EclipseLink 2.0 specific nodes

	String CACHE_INTERCEPTOR = "cache-interceptor";
	
	String CORRECTION_TYPE = "correction-type";
	String MAP_KEY_ASSOCIATION_OVERRIDE = "map-key-association-override";

	String QUERY_REDIRECTORS = "query-redirectors";
	String QUERY_REDIRECTORS__ALL_QUERIES = "all-queries";
	String QUERY_REDIRECTORS__READ_ALL = "read-all";
	String QUERY_REDIRECTORS__READ_OBJECT = "read-object";
	String QUERY_REDIRECTORS__REPORT = "report";
	String QUERY_REDIRECTORS__UPDATE = "update";
	String QUERY_REDIRECTORS__INSERT = "insert";
	String QUERY_REDIRECTORS__DELETE = "delete";

}

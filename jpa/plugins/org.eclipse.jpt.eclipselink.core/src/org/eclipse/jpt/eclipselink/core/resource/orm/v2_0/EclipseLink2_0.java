/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.eclipselink.core.resource.orm.v2_0;

import org.eclipse.jpt.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLink1_1;

@SuppressWarnings("nls")
public interface EclipseLink2_0
	extends EclipseLink1_1, JPA2_0
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_0.xsd";
	String SCHEMA_VERSION	= "2.0";
	
	// EclipseLink 2.0 specific nodes
	
	String CACHEABLE = "cacheable";
	String COLLECTION_TABLE = "collection-table";
	String DELIMITIED_IDENTIFIERS = "delimited-identifiers";
	String ELEMENT_COLLECTION = "element-collection";
	String MAP_KEY_ASSOCIATION_OVERRIDE = "map-key-association-override";
	String MAP_KEY_ATTRIBUTE_OVERRIDE = "map-key-attribute-override";
	String MAP_KEY_CLASS = "map-key-class";
	String MAP_KEY_COLUMN = "map-key-column";
	String MAP_KEY_CONVERT = "map-key-convert";
	String MAP_KEY_ENUMERATED = "map-key-enumerated";
	String MAP_KEY_JOIN_COLUMN = "map-key-join-column";
	String MAPPED_BY_ID = "mapped-by-id";
	String NAMED_QUERY__LOCK_MODE = "lock-mode";
	String ORDER_COLUMN = "order-column";
	String ORPHAN_REMOVAL = "orphan-removal";
	String TARGET_CLASS = "target-class";
	String MAP_KEY_TEMPORAL = "map-key-temporal";
}

/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.core.resource.orm.v2_0;

import org.eclipse.jpt.core.resource.orm.JPA;

/**
 * JPA 2.0 orm.xml-related stuff (elements, attributes etc.)
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
@SuppressWarnings("nls")
public interface JPA2_0
	extends JPA
{
	String SCHEMA_NAMESPACE = JPA.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://java.sun.com/xml/ns/persistence/orm_2_0.xsd";
	String SCHEMA_VERSION = "2.0";
	
	// JPA 2.0 specific nodes
	
	String CACHEABLE = "cacheable";
	String COLLECTION_TABLE = "collection-table";
	String DELIMITIED_IDENTIFIERS = "delimited-identifiers";
	String ELEMENT_COLLECTION = "element-collection";
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
	String VALIDATION_MODE = "validation-mode";
}

/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.java;

/**
 * JPA 2.0 Java-related stuff (annotations etc.)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
@SuppressWarnings("nls")
public interface JPA2_0 
{	
	// JPA package
	String PACKAGE = "javax.persistence";
	String PACKAGE_ = PACKAGE + '.';
	
	
	// ********** API **********
	
	// JPA 2.0 annotations
	String ACCESS = PACKAGE_ + "Access";
		String ACCESS__VALUE = "value";
	
	String ASSOCIATION_OVERRIDE__JOIN_TABLE = "joinTable";
	
	String MAPS_ID = PACKAGE_ + "MapsId";
		String MAPS_ID__VALUE = "value";
	
	String SEQUENCE_GENERATOR__CATALOG = "catalog";
	String SEQUENCE_GENERATOR__SCHEMA = "schema";
	
	// JPA 2.0 enums
	String ACCESS_TYPE = PACKAGE_ + "AccessType";
		String ACCESS_TYPE_ = ACCESS_TYPE + '.';
		String ACCESS_TYPE__FIELD = ACCESS_TYPE_ + "FIELD";
		String ACCESS_TYPE__PROPERTY = ACCESS_TYPE_ + "PROPERTY";
	
	// JPA 2.0 metamodel
	String METAMODEL_PACKAGE = PACKAGE_ + "metamodel";
	String METAMODEL_PACKAGE_ = METAMODEL_PACKAGE + '.';
	
	String STATIC_METAMODEL = METAMODEL_PACKAGE_ + "StaticMetamodel";
		String STATIC_METAMODEL__VALUE = "value";
	
	String SINGULAR_ATTRIBUTE = METAMODEL_PACKAGE_ + "SingularAttribute";
	
	String COLLECTION_ATTRIBUTE = METAMODEL_PACKAGE_ + "CollectionAttribute";
	String LIST_ATTRIBUTE = METAMODEL_PACKAGE_ + "ListAttribute";
	String MAP_ATTRIBUTE = METAMODEL_PACKAGE_ + "MapAttribute";
	String SET_ATTRIBUTE = METAMODEL_PACKAGE_ + "SetAttribute";
}
